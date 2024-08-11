package com.hust.mymall.controller;

import com.hust.mymall.pojo.Category;
import com.hust.mymall.service.ICategoryService;
import com.hust.mymall.vo.CategoryVo;
import com.hust.mymall.vo.ResponseVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Liubo Ren
 * @version 1.0
 */
@RestController
public class CategoryController {
    @Autowired
    private ICategoryService categoryService;

    @GetMapping("/categories")
    public ResponseVo<List<CategoryVo>> selectAll() {
        return categoryService.selectAll();
    }


}
