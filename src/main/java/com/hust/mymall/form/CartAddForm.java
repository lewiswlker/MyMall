package com.hust.mymall.form;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 添加商品
 * @author Liubo Ren
 * @version 1.0
 */
@Data
public class CartAddForm {
    @NotNull
    private Integer productId;

    private Boolean selected = true;

}
