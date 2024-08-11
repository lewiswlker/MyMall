package com.hust.mymall.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author Liubo Ren
 * @version 1.0
 */
@Data
public class UserRegisterForm {
    @NotBlank(message = "用户名不能为空")
    private String username;
    @NotBlank(message = "密码不能为空")
    private String password;
    @NotBlank(message = "电子邮箱不能为空")
    private String email;

    public UserRegisterForm(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }
}
