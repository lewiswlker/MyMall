package com.hust.mymall.service;

import com.hust.mymall.form.CartAddForm;
import com.hust.mymall.form.CartUpdateForm;
import com.hust.mymall.pojo.Cart;
import com.hust.mymall.vo.CartVo;
import com.hust.mymall.vo.ResponseVo;

import java.util.List;

/**
 * @author Liubo Ren
 * @version 1.0
 */
public interface ICartService {

    ResponseVo<CartVo> add(Integer uid, CartAddForm cartAddForm);

    ResponseVo<CartVo> list(Integer uid);

    ResponseVo<CartVo> update(Integer uid, Integer productId, CartUpdateForm cartUpdateForm);

    ResponseVo<CartVo> delete(Integer uid, Integer productId);

    ResponseVo<CartVo> selectAll(Integer uid);
    ResponseVo<CartVo> unSelectAll(Integer uid);
    ResponseVo<Integer> sum(Integer uid);

    List<Cart> listForCart(Integer uid);

}
