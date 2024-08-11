package com.hust.mymall.service.impl;

import com.hust.mymall.MyMallApplication;
import com.hust.mymall.MyMallApplicationTests;
import com.hust.mymall.enums.ResponseEnum;
import com.hust.mymall.enums.RoleEnum;
import com.hust.mymall.pojo.User;
import com.hust.mymall.service.IUserService;
import com.hust.mymall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.jws.soap.SOAPBinding;

import static org.junit.Assert.*;

/**
 * @author Liubo Ren
 * @version 1.0
 */
@Transactional
@Slf4j
public class UserServiceImplTest extends MyMallApplicationTests {

    public static final String USERNAME = "jack";
    public static final String PASSWORD = "123456";

    @Autowired
    private IUserService userService;

    @Test
    public void register() {
        User user = new User(USERNAME, PASSWORD, "000001@qq.com", RoleEnum.CUSTOMER.getRole());
        userService.register(user);
    }

    @Test
    public void login() {
        register();
        ResponseVo<User> responseVo = userService.login(USERNAME, PASSWORD);
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),responseVo.getStatus());
        log.info("responseVo = {}",responseVo.getStatus());
    }
}