package com.hust.mymall.Interceptor;

import com.hust.mymall.consts.MallConst;
import com.hust.mymall.exception.UserLoginException;
import com.hust.mymall.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Liubo Ren
 * @version 1.0
 */
@Slf4j
public class UserLoginInterceptor implements HandlerInterceptor {
    /**
     * 返回值true表示继续流程，false表示中断
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("preHandle...");
        User user = (User) request.getSession().getAttribute(MallConst.CURRENT_USER);
        if(user == null) {
            log.info("user == null");
            throw new UserLoginException();
//            return false;
//            return ResponseVo.error(ResponseEnum.NEED_LOGIN);
        }
//        return HandlerInterceptor.super.preHandle(request, response, handler);
        return true;
    }
}
