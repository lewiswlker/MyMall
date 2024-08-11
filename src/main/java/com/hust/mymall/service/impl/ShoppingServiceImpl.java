package com.hust.mymall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hust.mymall.dao.ShoppingMapper;
import com.hust.mymall.enums.ResponseEnum;
import com.hust.mymall.form.ShoppingForm;
import com.hust.mymall.pojo.Shopping;
import com.hust.mymall.service.IShoppingService;
import com.hust.mymall.vo.ResponseVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Liubo Ren
 * @version 1.0
 */
@Service
public class ShoppingServiceImpl implements IShoppingService {

    @Autowired
    private ShoppingMapper shoppingMapper;
    @Override
    public ResponseVo<Map<String, Integer>> add(Integer uid, ShoppingForm shoppingForm) {
        Shopping shopping = new Shopping();
        shopping.setUserId(uid);
        BeanUtils.copyProperties(shoppingForm, shopping);
        int row = shoppingMapper.insertSelective(shopping);
        if(row == 0) {
            return ResponseVo.error(ResponseEnum.ERROR);
        }
        HashMap<String, Integer> map = new HashMap<>();
        map.put("shippingId", shopping.getId());

        return ResponseVo.success(map);
    }

    @Override
    public ResponseVo delete(Integer uid, Integer shoppingId) {
        int row = shoppingMapper.deleteByIdAndUid(uid, shoppingId);
        if(row == 0) {
            return ResponseVo.error(ResponseEnum.DELETE_SHOPPING_FAILED);
        }

        return ResponseVo.success();
    }

    @Override
    public ResponseVo update(Integer uid, Integer shoppingId, ShoppingForm shoppingForm) {
        Shopping shopping = new Shopping();
        BeanUtils.copyProperties(shoppingForm, shopping);
        shopping.setUserId(uid);
        shopping.setId(shoppingId);
        int row = shoppingMapper.updateByPrimaryKeySelective(shopping);
        if(row == 0) {
            return ResponseVo.error(ResponseEnum.DELETE_SHOPPING_FAILED);
        }
        return ResponseVo.success();
    }

    @Override
    public ResponseVo<PageInfo> list(Integer uid, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Shopping> shoppings = shoppingMapper.selectByUid(uid);
        PageInfo pageInfo = new PageInfo(shoppings);
        return ResponseVo.success(pageInfo);
    }
}
