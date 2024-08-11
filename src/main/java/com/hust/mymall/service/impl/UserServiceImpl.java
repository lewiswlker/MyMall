package com.hust.mymall.service.impl;

import com.hust.mymall.dao.UserMapper;
import com.hust.mymall.enums.ResponseEnum;
import com.hust.mymall.enums.RoleEnum;
import com.hust.mymall.pojo.User;
import com.hust.mymall.service.IUserService;
import com.hust.mymall.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;


/**
 * @author Liubo Ren
 * @version 1.0
 */
@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserMapper userMapper;
    /**
     * 注册功能具体实现
     * @param user
     *
     */
    @Override
    public ResponseVo<User> register(User user) {

        // 用户名不能重复
        int countByUsername = userMapper.countByUsername(user.getUsername());
        if(countByUsername > 0) {
            return ResponseVo.error(ResponseEnum.USERNAME_EXIST);
        }
        // e-mail 不能重复
        int countByEmail = userMapper.countByEmail(user.getEmail());
        if(countByEmail > 0) {
            return ResponseVo.error(ResponseEnum.EMAIL_EXIST);
        }
        user.setRole(RoleEnum.CUSTOMER.getRole());
        // 密码MD5摘要算法，spring自带
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes(StandardCharsets.UTF_8)));

        // 写入数据库
        int resultOfInsert = userMapper.insertSelective(user);
        if(resultOfInsert == 0) {
            return ResponseVo.error(ResponseEnum.ERROR);
        }
        return ResponseVo.successByMsg("注册成功");
    }

    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    @Override
    public ResponseVo<User> login(String username, String password) {

        User user = userMapper.selectByUsername(username);

        // 用户不存在
        if (user == null) {
            return ResponseVo.error(ResponseEnum.USERNAME_OR_PASSWORD_ERROR);
        }

        // 密码错误
        if(!(user.getPassword().equalsIgnoreCase(DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8))))) {
            return ResponseVo.error(ResponseEnum.USERNAME_OR_PASSWORD_ERROR);
        }

        user.setPassword("");
        return ResponseVo.success(user);
    }
}
