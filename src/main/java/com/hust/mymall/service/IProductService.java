package com.hust.mymall.service;

import com.github.pagehelper.PageInfo;
import com.hust.mymall.vo.ProductDetailVo;
import com.hust.mymall.vo.ProductVo;
import com.hust.mymall.vo.ResponseVo;

import java.util.List;

/**
 * @author Liubo Ren
 * @version 1.0
 */
public interface IProductService {

    ResponseVo<PageInfo> list(Integer categoryId, Integer pageNum, Integer pageSize);

    ResponseVo<ProductDetailVo> detail(Integer productId);
}
