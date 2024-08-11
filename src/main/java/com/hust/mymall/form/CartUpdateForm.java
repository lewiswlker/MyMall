package com.hust.mymall.form;

import lombok.Data;

/**
 * @author Liubo Ren
 * @version 1.0
 */
@Data
public class CartUpdateForm {
    private Integer quantity; //非必填
    private Boolean selected; //非必填

    public CartUpdateForm() {
    }


}
