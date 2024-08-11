package com.hust.mymall.service.impl;

import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hust.mymall.MyMallApplicationTests;
import com.hust.mymall.enums.ResponseEnum;
import com.hust.mymall.form.CartAddForm;
import com.hust.mymall.service.ICartService;
import com.hust.mymall.vo.CartVo;
import com.hust.mymall.vo.OrderVo;
import com.hust.mymall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

/**
 * @author Liubo Ren
 * @version 1.0
 */
@Slf4j
@Transactional
public class OrderServiceImplTest extends MyMallApplicationTests {

    @Autowired
    private OrderServiceImpl orderService;
    @Autowired
    private ICartService cartService;
    private Integer uid = 1;
    private Integer shopping = 28;
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Before
    public void before() {
        CartAddForm cartAddForm = new CartAddForm();
        cartAddForm.setProductId(28);
        cartAddForm.setSelected(true);
        ResponseVo<CartVo> responseVo1 = cartService.add(1, cartAddForm);

        CartAddForm cartAddForm2 = new CartAddForm();
        cartAddForm2.setProductId(29);
        cartAddForm2.setSelected(true);
        ResponseVo<CartVo> responseVo2 = cartService.add(1, cartAddForm2);

        String json = gson.toJson(responseVo2);

        log.info("cartList = {}", json);
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo2.getStatus());

    }

    @Test
    public void create() {
        ResponseVo<OrderVo> responseVo = orderService.create(uid, shopping);
        log.info("responseVo = {}", gson.toJson(responseVo));
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());
    }

    private ResponseVo<OrderVo> createForTest() {
        ResponseVo<OrderVo> responseVo = orderService.create(uid, shopping);
        log.info("responseVo = {}", gson.toJson(responseVo));
//        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());
        return responseVo;
    }

    @Test
    public void list() {
        ResponseVo<PageInfo> responseVo = orderService.list(uid, 1, 2);
        log.info("responseVo = {}", gson.toJson(responseVo));
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());
    }

    @Test
    public void detail() {
        ResponseVo<OrderVo> vo = createForTest();
        ResponseVo<OrderVo> responseVo = orderService.detail(uid, vo.getData().getOrderNo());
        log.info("responseVo = {}", gson.toJson(responseVo));
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());
    }

    @Test
    public void cancel() {
        ResponseVo<OrderVo> vo = createForTest();
        ResponseVo responseVo = orderService.cancel(uid, vo.getData().getOrderNo());
        log.info("responseVo = {}", gson.toJson(responseVo));
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());
    }
}