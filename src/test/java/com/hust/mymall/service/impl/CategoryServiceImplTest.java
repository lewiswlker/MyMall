package com.hust.mymall.service.impl;

import com.hust.mymall.MyMallApplicationTests;
import com.hust.mymall.enums.ResponseEnum;
import com.hust.mymall.service.ICategoryService;
import com.hust.mymall.vo.CategoryVo;
import com.hust.mymall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * @author Liubo Ren
 * @version 1.0
 */
@Slf4j
public class CategoryServiceImplTest extends MyMallApplicationTests {
    @Autowired
    private ICategoryService categoryService;

    @Test
    public void selectAll() {
        ResponseVo<List<CategoryVo>> listResponseVo = categoryService.selectAll();
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),listResponseVo.getStatus());
    }

    @Test
    public void findSubCategoryId() {
        Set<Integer> integers = new HashSet<>();
        categoryService.findSubCategoryId(100001, integers);
        log.info("set = {}", integers);
    }
}