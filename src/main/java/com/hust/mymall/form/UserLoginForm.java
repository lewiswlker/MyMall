package com.hust.mymall.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author Liubo Ren
 * @version 1.0
 */
@Data
public class UserLoginForm {
    @NotBlank(message = "用户名不能为空")
    private String username;
    @NotBlank(message = "密码不能为空")
    private String password;


    public UserLoginForm(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
