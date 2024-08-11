package com.hust.mymall.controller;

import com.github.pagehelper.PageInfo;
import com.hust.mymall.service.IProductService;
import com.hust.mymall.vo.ProductDetailVo;
import com.hust.mymall.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Liubo Ren
 * @version 1.0
 */
@RestController
public class ProductController {
    @Autowired
    private IProductService productService;

    @GetMapping("/products")
    public ResponseVo<PageInfo> list(@RequestParam(required = false) Integer categoryId,
                                     @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                     @RequestParam(required = false, defaultValue = "1") Integer pageSize) {

        return productService.list(categoryId, pageNum, pageSize);
    }

    @GetMapping("/products/{productId}")
    public ResponseVo<ProductDetailVo> detail(@PathVariable Integer productId) {
        return productService.detail(productId);
    }
}
