package com.hust.mymall.controller;

import com.hust.mymall.consts.MallConst;
import com.hust.mymall.enums.ResponseEnum;
import com.hust.mymall.form.UserLoginForm;
import com.hust.mymall.form.UserRegisterForm;
import com.hust.mymall.pojo.User;
import com.hust.mymall.service.IUserService;
import com.hust.mymall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Objects;

/**
 * @author Liubo Ren
 * @version 1.0
 */
@RestController
//@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private IUserService userService;


    @PostMapping("/user/register")
    public ResponseVo<User> register(@Valid @RequestBody UserRegisterForm userRegisterForm
//            , BindingResult bindingResult
    ) {

//        if(bindingResult.hasErrors()) {
//            log.error("注册提交的表单有误，{}", Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
//            return ResponseVo.error(ResponseEnum.PARAM_ERROR, bindingResult);
//        }

        User user = new User();
        BeanUtils.copyProperties(userRegisterForm, user);

        return userService.register(user);
    }

    @PostMapping("/user/login")
    public ResponseVo<User> login(@Valid @RequestBody UserLoginForm userLoginForm,
//                                  BindingResult bindingResult,
                                  HttpSession httpSession) {
//        if(bindingResult.hasErrors()) {
////            log.error("注册提交的表单有误，{}", Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
//            return ResponseVo.error(ResponseEnum.PARAM_ERROR, bindingResult);
//        }
        ResponseVo<User> userResponseVo = userService.login(userLoginForm.getUsername(), userLoginForm.getPassword());

        // 设置Session
        httpSession.setAttribute(MallConst.CURRENT_USER, userResponseVo.getData());

        return userResponseVo;
    }

    // Session保存在服务器内存里，token + redis解决
    // cookie 跨域
    @GetMapping("/user")
    public ResponseVo<User> userInfo(HttpSession httpSession) {
        User user = (User) httpSession.getAttribute(MallConst.CURRENT_USER);
//        if(user == null) {
//            return ResponseVo.error(ResponseEnum.NEED_LOGIN);
//        }

        return ResponseVo.success(user);
    }

    @PostMapping("/user/logout")
    public ResponseVo<User> logout(HttpSession httpSession) {
//        User user = (User) httpSession.getAttribute(MallConst.CURRENT_USER);
//        if(user == null) {
//            return ResponseVo.error(ResponseEnum.NEED_LOGIN);
//        }
        httpSession.removeAttribute(MallConst.CURRENT_USER);
        return ResponseVo.successByMsg("退出成功");
    }
}
