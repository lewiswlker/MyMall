package com.hust.mymall.exception;

import com.hust.mymall.enums.ResponseEnum;
import com.hust.mymall.vo.ResponseVo;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Objects;

/**
 * @author Liubo Ren
 * @version 1.0
 *
 */
@ControllerAdvice
public class RuntimeExceptionHandler {
    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
//    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseVo handle(RuntimeException e) {
        return ResponseVo.error(ResponseEnum.ERROR, e.getMessage());
    }
    @ExceptionHandler(UserLoginException.class)
    @ResponseBody
    public ResponseVo userLoginHandle() {
        return ResponseVo.error(ResponseEnum.NEED_LOGIN);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public  ResponseVo notValidExceptionHandle(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        return ResponseVo.error(ResponseEnum.PARAM_ERROR,
                Objects.requireNonNull(bindingResult.getFieldError()).getField()
                        + " " + bindingResult.getFieldError().getDefaultMessage());
    }

}
