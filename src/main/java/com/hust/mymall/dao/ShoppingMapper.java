package com.hust.mymall.dao;

import com.hust.mymall.pojo.Shopping;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface ShoppingMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Shopping record);

    int insertSelective(Shopping record);

    Shopping selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Shopping record);

    int updateByPrimaryKey(Shopping record);

    int deleteByIdAndUid(@Param("uid") Integer uid,
                         @Param("shoppingId") Integer shoppingId);

    List<Shopping> selectByUid(Integer uid);

    Shopping selectByUidAndShoppingId(@Param("uid") Integer uid,
                                      @Param("shoppingId") Integer shoppingId);

    List<Shopping> selectByIdSet(@Param("idSet") Set idSet);
}