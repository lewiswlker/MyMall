package com.hust.mymall.service.impl;

import com.github.pagehelper.PageInfo;
import com.hust.mymall.MyMallApplicationTests;
import com.hust.mymall.enums.ResponseEnum;
import com.hust.mymall.vo.ProductDetailVo;
import com.hust.mymall.vo.ProductVo;
import com.hust.mymall.vo.ResponseVo;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Liubo Ren
 * @version 1.0
 */
public class ProductServiceImplTest extends MyMallApplicationTests {

    @Autowired
    private ProductServiceImpl productService;

    @Test
    public void list() {

        ResponseVo<PageInfo> responseVo = productService.list(null, 2, 2);
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),responseVo.getStatus());
    }

    @Test
    public void detail() {
        ResponseVo<ProductDetailVo> responseVo = productService.detail(26);
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),responseVo.getStatus());
    }
}