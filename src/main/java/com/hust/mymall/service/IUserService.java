package com.hust.mymall.service;

import com.hust.mymall.pojo.User;
import com.hust.mymall.vo.ResponseVo;

/**
 * @author Liubo Ren
 * @version 1.0
 */
public interface IUserService {
    /**
     * @param user
     * 注册
     */
    ResponseVo<User> register(User user);
    /**
     * 登录
     */
    ResponseVo<User> login(String username,String password);
}
