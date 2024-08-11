package com.hust.mymall.service;

import com.github.pagehelper.PageInfo;
import com.hust.mymall.form.ShoppingForm;
import com.hust.mymall.pojo.Shopping;
import com.hust.mymall.vo.ResponseVo;

import java.util.Map;

/**
 * @author Liubo Ren
 * @version 1.0
 */
public interface IShoppingService {

    ResponseVo<Map<String, Integer>> add(Integer uid, ShoppingForm shoppingForm);

    ResponseVo delete(Integer uid, Integer shoppingId);

    ResponseVo update(Integer uid, Integer shoppingId, ShoppingForm shoppingForm);

    ResponseVo<PageInfo> list(Integer uid, Integer pageNum, Integer pageSize);
}
