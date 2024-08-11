package com.hust.mymall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hust.mymall.dao.OrderItemMapper;
import com.hust.mymall.dao.OrderMapper;
import com.hust.mymall.dao.ProductMapper;
import com.hust.mymall.dao.ShoppingMapper;
import com.hust.mymall.enums.OrderStatusEnum;
import com.hust.mymall.enums.PaymentTypeEnum;
import com.hust.mymall.enums.ProductStatusEnum;
import com.hust.mymall.enums.ResponseEnum;
import com.hust.mymall.pojo.*;
import com.hust.mymall.service.ICartService;
import com.hust.mymall.service.IOrderService;
import com.hust.mymall.vo.OrderItemVo;
import com.hust.mymall.vo.OrderVo;
import com.hust.mymall.vo.ResponseVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Liubo Ren
 * @version 1.0
 */
@Service
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private ShoppingMapper shoppingMapper;

    @Autowired
    private ICartService cartService;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Override
    @Transactional
    public ResponseVo<OrderVo> create(Integer uid, Integer shoppingId) {
        // 收货地址校验
        Shopping shopping = shoppingMapper.selectByUidAndShoppingId(uid, shoppingId);
        if (shopping == null) {
            return ResponseVo.error(ResponseEnum.SHOPPING_NOT_EXIST);
        }

        // 获取购物车， 校验（是否有库存
        List<Cart> cartList = cartService.listForCart(uid).stream()
                .filter(Cart::getProductSelected)
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(cartList)) {
            return ResponseVo.error(ResponseEnum.CART_SELECTED_IS_EMPTY);
        }

        // 获取cartList里的productIds
        Set<Integer> productIdSet = cartList.stream().map(Cart::getProductId).collect(Collectors.toSet());
        List<Product> productList = productMapper.selectByProductIdSet(productIdSet);
        Map<Integer, Product> map = productList.stream()
                .collect(Collectors.toMap(Product::getId, product -> product));

        List<OrderItem> orderItemList = new ArrayList<>();
        Long orderNo = generateOrderNo();

        for (Cart cart : cartList) {
            // 根据productId查询数据库
            Product product = map.get(cart.getProductId());
            // 是否有商品
            if (product == null) {
                return ResponseVo.error(ResponseEnum.PRODUCT_NOT_EXIST, "商品不存在. productId = " + cart.getProductId());
            }
            // 商品上下架状态
            if (!ProductStatusEnum.ON_SALE.getCode().equals(product.getStatus())) {
                return ResponseVo.error(ResponseEnum.PRODUCT_OFF_SALE_OR_DELETE, "商品已被下架或者删除. 商品名 = " + product.getName());
            }
            // 库存是否充足
            if (product.getStock() < cart.getQuantity()) {
                return ResponseVo.error(ResponseEnum.PRODUCT_STOCK_INSUFFICIENT, "库存不足. " + product.getName());
            }

            OrderItem orderItem = buildOrderItem(uid, orderNo, cart.getQuantity(), product);

            orderItemList.add(orderItem);
            // 减库存
            product.setStock(product.getStock() - cart.getQuantity());
            int row = productMapper.updateByPrimaryKeySelective(product);
            if (row <= 0) {
                return ResponseVo.error(ResponseEnum.ERROR);
            }

        }
        // 计算总价格，只计算选中的
        // 生成订单，入库：order和order_item  事务
        Order order = buildOrder(uid, orderNo, shoppingId, orderItemList);

        int rowForOrder = orderMapper.insertSelective(order);
        if (rowForOrder <= 0) {
            return ResponseVo.error(ResponseEnum.ERROR);
        }

        int rowForOrderItem = orderItemMapper.batchInsert(orderItemList);
        if (rowForOrderItem <= 0) {
            return ResponseVo.error(ResponseEnum.ERROR);
        }
        // 更新购物车
        //Redis 有事务（打包命令，不能回滚
        for (Cart cart : cartList) {
            cartService.delete(uid, cart.getProductId());
        }


        // 构造orderVo
        OrderVo orderVo = buildOrderVo(order, orderItemList, shopping);
        return ResponseVo.success(orderVo);
    }

    @Override
    public ResponseVo<PageInfo> list(Integer uid, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Order> orderList = orderMapper.selectByUid(uid);

        Set<Long> orderNoSet = orderList.stream()
                .map(Order::getOrderNo)
                .collect(Collectors.toSet());
        List<OrderItem> orderItemList = orderItemMapper.selectByOrderNoSet(orderNoSet);
        Map<Long, List<OrderItem>> orderItemMap = orderItemList.stream()
                .collect(Collectors.groupingBy(OrderItem::getOrderNo));


        Set<Integer> shippingIdSet = orderList.stream()
                .map(Order::getShippingId)
                .collect(Collectors.toSet());
        List<Shopping> shoppingList = shoppingMapper.selectByIdSet(shippingIdSet);
        Map<Integer, Shopping> shoppingMap = shoppingList.stream()
                .collect(Collectors.toMap(Shopping::getId, shipping -> shipping));

        List<OrderVo> orderVoList = new ArrayList<>();
        for (Order order : orderList) {
            OrderVo orderVo = buildOrderVo(order,
                    orderItemMap.get(order.getOrderNo()),
                    shoppingMap.get(order.getShippingId()));
            orderVoList.add(orderVo);
        }
        PageInfo pageInfo = new PageInfo<>(orderList);
        pageInfo.setList(orderVoList);

        return ResponseVo.success(pageInfo);
    }

    @Override
    public ResponseVo<OrderVo> detail(Integer uid, Long orderNo) {
        Order order = orderMapper.selectByOrderNo(orderNo);
        if (order == null || !order.getUserId().equals(uid)) {
            return ResponseVo.error(ResponseEnum.ORDER_NOT_EXIST);
        }
        Set<Long> orderNoSet = new HashSet<>();
        orderNoSet.add(order.getOrderNo());
        List<OrderItem> orderItemList = orderItemMapper.selectByOrderNoSet(orderNoSet);

        Shopping shopping = shoppingMapper.selectByPrimaryKey(order.getShippingId());

        OrderVo orderVo = buildOrderVo(order, orderItemList, shopping);

        return ResponseVo.success(orderVo);
    }

    @Override
    public ResponseVo cancel(Integer uid, Long orderNo) {
        Order order = orderMapper.selectByOrderNo(orderNo);
        if (order == null || !order.getUserId().equals(uid)) {
            return ResponseVo.error(ResponseEnum.ORDER_NOT_EXIST);
        }
        // 只有未付款才能取消，具体看业务逻辑
        if (!order.getStatus().equals(OrderStatusEnum.NO_PAY.getCode())) {
            return ResponseVo.error(ResponseEnum.ORDER_STATUS_ERROR);
        }
        order.setStatus(OrderStatusEnum.CANCELED.getCode());
        order.setCloseTime(new Date());
        int row = orderMapper.updateByPrimaryKeySelective(order);
        if (row <= 0) {
            return ResponseVo.error(ResponseEnum.ERROR);
        }

        return ResponseVo.success();
    }

    @Override
    public void paid(Long orderNo) {
        Order order = orderMapper.selectByOrderNo(orderNo);
        if (order == null) {
            throw new RuntimeException(ResponseEnum.ORDER_NOT_EXIST.getDesc() + "订单ID" + orderNo);
        }
        // 只有未付款才能变成已付款，具体看业务逻辑
        if (!order.getStatus().equals(OrderStatusEnum.NO_PAY.getCode())) {
            throw new RuntimeException(ResponseEnum.ORDER_STATUS_ERROR.getDesc() + "订单ID" + orderNo);
        }
        order.setStatus(OrderStatusEnum.PAID.getCode());
        order.setPaymentTime(new Date());
        int row = orderMapper.updateByPrimaryKeySelective(order);
        if (row <= 0) {
            throw new RuntimeException("订单更新为已支付失败！" + ResponseEnum.ERROR.getDesc() + "订单ID" + orderNo);
        }

        return;
    }

    private OrderVo buildOrderVo(Order order, List<OrderItem> orderItemList, Shopping shopping) {
        OrderVo orderVo = new OrderVo();
        BeanUtils.copyProperties(order, orderVo);

        List<OrderItemVo> orderItemVoList = orderItemList.stream().map(
                e -> {
                    OrderItemVo orderItemVo = new OrderItemVo();
                    BeanUtils.copyProperties(e, orderItemVo);
                    return orderItemVo;
                }).collect(Collectors.toList());
        orderVo.setOrderItemVoList(orderItemVoList);
        if (shopping != null) {
            orderVo.setShoppingId(shopping.getId());
            orderVo.setShopping(shopping);
        }
        return orderVo;
    }

    private Order buildOrder(Integer uid,
                             Long orderNo,
                             Integer shoppingId,
                             List<OrderItem> orderItemList
    ) {
        Order order = new Order();
        order.setOrderNo(orderNo);
        order.setUserId(uid);
        order.setShippingId(shoppingId);
        order.setPayment(
                orderItemList.stream()
                        .map(OrderItem::getTotalPrice)
                        .reduce(BigDecimal.ZERO, BigDecimal::add)
        );
        order.setPaymentType(PaymentTypeEnum.PAY_ONLINE.getCode());
        order.setPostage(0);
        order.setStatus(OrderStatusEnum.NO_PAY.getCode());

        return order;
    }

    /**
     * 企业级：分布式唯一id
     *
     * @return
     */
    private Long generateOrderNo() {
        return System.currentTimeMillis() + new Random().nextInt(999);
    }

    private OrderItem buildOrderItem(Integer uid, Long orderNo, Integer quantity, Product product) {
        OrderItem item = new OrderItem();
        item.setUserId(uid);
        item.setOrderNo(orderNo);
        item.setProductId(product.getId());
        item.setProductName(product.getName());
        item.setProductImage(product.getMainImage());
        item.setCurrentUnitPrice(product.getPrice());
        item.setQuantity(quantity);
        item.setTotalPrice(product.getPrice().multiply(BigDecimal.valueOf(quantity)));
        return item;
    }
}
