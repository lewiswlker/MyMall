package com.hust.mymall.dao;

import com.hust.mymall.pojo.User;

import javax.jws.soap.SOAPBinding;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    int countByUsername(String usrname);

    int countByEmail(String email);

    User selectByUsername(String username);
}