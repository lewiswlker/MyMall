package com.hust.mymall.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hust.mymall.MyMallApplicationTests;
import com.hust.mymall.enums.ResponseEnum;
import com.hust.mymall.form.ShoppingForm;
import com.hust.mymall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

import static org.junit.Assert.*;

/**
 * @author Liubo Ren
 * @version 1.0
 */
@Slf4j
public class IShoppingServiceTest extends MyMallApplicationTests {

    @Autowired
    private IShoppingService shoppingService;
    private static final Integer uid = 1;
    private ShoppingForm form;
    private Integer shoppingId;

    @Before
    public void madeForm(){
        ShoppingForm form = new ShoppingForm();
        form.setReceiverName("jack");
        form.setReceiverAddress("huawei");
        form.setReceiverCity("wuhan");
        form.setReceiverMobile("16671006655");
        form.setReceiverPhone("010123456");
        form.setReceiverProvince("hubei");
        form.setReceiverDistrict("yichang");
        form.setReceiverZip("430074");
        this.form = form;

        add();
    }
    @Test
    public void add() {
        ResponseVo<Map<String, Integer>> responseVo = shoppingService.add(uid, this.form);
        this.shoppingId = responseVo.getData().get("shippingId");
        log.info("responseVo = {}", responseVo);
        log.info("shoppingId = {}", shoppingId);
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());

    }

//    @After
    public void delete() {
        ResponseVo responseVo = shoppingService.delete(uid, this.shoppingId);
        log.info("responseVo = {}", responseVo);
        log.info("shoppingId = {}", shoppingId);
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());
    }

    @Test
    public void update() {
        this.form.setReceiverCity("beijing");
        ResponseVo responseVo = shoppingService.update(uid, this.shoppingId, form);
        log.info("responseVo = {}", responseVo);
        log.info("shoppingId = {}", shoppingId);
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());
    }

    @Test
    public void list() {
        ResponseVo responseVo = shoppingService.list(uid, 1, 10);
        log.info("responseVo = {}", responseVo);
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());
    }
}