package com.hust.mymall.service.impl;

import com.google.gson.Gson;
import com.hust.mymall.dao.ProductMapper;
import com.hust.mymall.enums.ProductStatusEnum;
import com.hust.mymall.enums.ResponseEnum;
import com.hust.mymall.form.CartAddForm;
import com.hust.mymall.form.CartUpdateForm;
import com.hust.mymall.pojo.Cart;
import com.hust.mymall.pojo.Product;
import com.hust.mymall.service.ICartService;
import com.hust.mymall.vo.CartProductVo;
import com.hust.mymall.vo.CartVo;
import com.hust.mymall.vo.ResponseVo;
import com.mysql.cj.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Liubo Ren
 * @version 1.0
 */
@Service
public class CartServiceImpl implements ICartService {

    private static final String CART_REDIS_KEY_TEMPLATE = "cart_%d";

    private static final Integer QUANTITY = 1;

    private static final Gson gson = new Gson();

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public ResponseVo<CartVo> add(Integer uid, CartAddForm cartAddForm) {
        Product product = productMapper.selectByPrimaryKey(cartAddForm.getProductId());
        // 判断商品是否存在
        if (product == null) {
            return ResponseVo.error(ResponseEnum.PRODUCT_NOT_EXIST);
        }

        // 库存是否充足
        if (product.getStock() <= 0) {
            return ResponseVo.error(ResponseEnum.PRODUCT_STOCK_INSUFFICIENT);
        }

        //商品是否正常在售
        if (!product.getStatus().equals(ProductStatusEnum.ON_SALE.getCode())) {
            return ResponseVo.error(ResponseEnum.PRODUCT_OFF_SALE_OR_DELETE);
        }

        // 写入redis
        // key: cart_1
        HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE, uid);
        String value = opsForHash.get(redisKey, String.valueOf(product.getId()));

        Cart cart;
        if (StringUtils.isNullOrEmpty(value)) {
            // 没有该商品,add
            cart = new Cart(product.getId(), QUANTITY, cartAddForm.getSelected());

        } else {
            // 有,+1
            cart = gson.fromJson(value, Cart.class);
            cart.setQuantity(cart.getQuantity() + QUANTITY);
        }

        opsForHash.put(redisKey,
                String.valueOf(product.getId()),
                gson.toJson(cart));

        return list(uid);
    }

    @Override
    public ResponseVo<CartVo> list(Integer uid) {
        HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE, uid);

        Map<String, String> entries = opsForHash.entries(redisKey);

        boolean selectAll = true;
        Integer cartTotalQuantity = 0;
        BigDecimal cartTotalPrice = BigDecimal.ZERO;
        CartVo cartVo = new CartVo();
        ArrayList<CartProductVo> cartProductVoList = new ArrayList<>();

        for (Map.Entry<String, String> entry : entries.entrySet()) {
            Integer productId = Integer.valueOf(entry.getKey());
            Cart cart = gson.fromJson(entry.getValue(), Cart.class);
            Product product = productMapper.selectByPrimaryKey(productId);
            if (product != null) {
                CartProductVo cartProductVo = new CartProductVo(productId,
                        cart.getQuantity(),
                        product.getName(),
                        product.getSubtitle(),
                        product.getMainImage(),
                        product.getPrice(),
                        product.getStatus(),
                        product.getPrice().multiply(BigDecimal.valueOf(cart.getQuantity())),
                        product.getStock(),
                        cart.getProductSelected());
                cartProductVoList.add(cartProductVo);

                if (!cart.getProductSelected()) {
                    selectAll = false;
                } else {
                    // 选中的才做累加
                    cartTotalPrice = cartTotalPrice.add(cartProductVo.getProductTotalPrice());
                }
            }

            cartTotalQuantity += cart.getQuantity();
        }
        cartVo.setCartProductVoList(cartProductVoList);
        cartVo.setSelectAll(selectAll);
        cartVo.setCartTotalPrice(cartTotalPrice);
        cartVo.setCartTotalQuantity(cartTotalQuantity);
        return ResponseVo.success(cartVo);
    }

    @Override
    public ResponseVo<CartVo> update(Integer uid, Integer productId, CartUpdateForm cartUpdateForm) {
        HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE, uid);
        String value = opsForHash.get(redisKey, String.valueOf(productId));

        Cart cart;
        if (StringUtils.isNullOrEmpty(value)) {
            // 没有该商品,error
            return ResponseVo.error(ResponseEnum.PRODUCT_NOT_EXIST_IN_CART);
        } else {
            // 有,update content
            cart = gson.fromJson(value, Cart.class);
            if (cartUpdateForm.getQuantity() != null && cartUpdateForm.getQuantity() >= 0) {
                cart.setQuantity(cartUpdateForm.getQuantity());
            }
            if (cartUpdateForm.getSelected() != null) {
                cart.setProductSelected(cartUpdateForm.getSelected());
            }
            opsForHash.put(redisKey, String.valueOf(productId), gson.toJson(cart));
            return list(uid);
        }
    }

    @Override
    public ResponseVo<CartVo> delete(Integer uid, Integer productId) {
        HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE, uid);
        String value = opsForHash.get(redisKey, String.valueOf(productId));

        Cart cart;
        if (StringUtils.isNullOrEmpty(value)) {
            // 没有该商品,error
            return ResponseVo.error(ResponseEnum.PRODUCT_NOT_EXIST_IN_CART);
        }
        opsForHash.delete(redisKey, String.valueOf(productId));
        return list(uid);
    }

    @Override
    public ResponseVo<CartVo> selectAll(Integer uid) {
        HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE, uid);

        List<Cart> carts = listForCart(uid);
        for (Cart cart : carts) {
            cart.setProductSelected(true);
            opsForHash.put(redisKey, String.valueOf(cart.getProductId()), gson.toJson(cart));
        }
        return list(uid);
    }

    @Override
    public ResponseVo<CartVo> unSelectAll(Integer uid) {
        HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE, uid);

        List<Cart> carts = listForCart(uid);
        for (Cart cart : carts) {
            cart.setProductSelected(false);
            opsForHash.put(redisKey, String.valueOf(cart.getProductId()), gson.toJson(cart));
        }
        return list(uid);
    }

    @Override
    public ResponseVo<Integer> sum(Integer uid) {
        Integer sum = listForCart(uid).stream()
                .map(Cart::getQuantity)
                .reduce(0, Integer::sum);
        return ResponseVo.success(sum);
    }

    public List<Cart> listForCart(Integer uid) {
        HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE, uid);

        Map<String, String> entries = opsForHash.entries(redisKey);

        ArrayList<Cart> carts = new ArrayList<>();
        for (Map.Entry<String, String> entry : entries.entrySet()) {
            carts.add(gson.fromJson(entry.getValue(), Cart.class));
        }
        return carts;
    }
}
