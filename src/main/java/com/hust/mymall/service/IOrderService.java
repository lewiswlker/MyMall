package com.hust.mymall.service;

import com.github.pagehelper.PageInfo;
import com.hust.mymall.pojo.PayInfo;
import com.hust.mymall.vo.OrderVo;
import com.hust.mymall.vo.ResponseVo;

/**
 * @author Liubo Ren
 * @version 1.0
 */
public interface IOrderService {

    ResponseVo<OrderVo> create(Integer uid, Integer shoppingId);

    ResponseVo<PageInfo> list(Integer uid, Integer pageNum, Integer pageSize);

    ResponseVo<OrderVo> detail(Integer uid, Long orderNo);

    ResponseVo cancel(Integer uid, Long orderNo);

    void paid(Long orderNo);



}
