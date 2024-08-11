package com.hust.mymall.service.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hust.mymall.MyMallApplicationTests;
import com.hust.mymall.enums.ResponseEnum;
import com.hust.mymall.form.CartAddForm;
import com.hust.mymall.form.CartUpdateForm;
import com.hust.mymall.service.ICartService;
import com.hust.mymall.vo.CartVo;
import com.hust.mymall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * @author Liubo Ren
 * @version 1.0
 */
@Slf4j
public class CartServiceTest extends MyMallApplicationTests {

    @Autowired
    private ICartService cartService;

    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Before
    public void add() {
        CartAddForm cartAddForm = new CartAddForm();
        cartAddForm.setProductId(26);
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
    public void list() {
        ResponseVo<CartVo> responseVo = cartService.list(1);
        String json = gson.toJson(responseVo);
        log.info("cartList = {}", json);
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());
    }

    @Test
    public void update() {
        CartUpdateForm cartUpdateForm = new CartUpdateForm();
        cartUpdateForm.setSelected(false);
        cartUpdateForm.setQuantity(10);
        ResponseVo<CartVo> responseVo = cartService.update(1, 26, cartUpdateForm);
        String json = gson.toJson(responseVo);
        log.info("cartList = {}", json);
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());
    }

    @After
    public void delete() {
        ResponseVo<CartVo> responseVo = cartService.delete(1, 29);
        String json = gson.toJson(responseVo);
        log.info("cartList = {}", json);
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());
    }

    @Test
    public void selectAll() {
        ResponseVo<CartVo> responseVo = cartService.selectAll(1);
        String json = gson.toJson(responseVo);
        log.info("cartList = {}", json);
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());
    }

    @Test
    public void unSelectAll() {
        ResponseVo<CartVo> responseVo = cartService.unSelectAll(1);
        String json = gson.toJson(responseVo);
        log.info("cartList = {}", json);
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());
    }

    @Test
    public void sum() {
        ResponseVo<Integer> responseVo = cartService.sum(1);
        String json = gson.toJson(responseVo);
        log.info("cartList = {}", json);
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());
    }
}