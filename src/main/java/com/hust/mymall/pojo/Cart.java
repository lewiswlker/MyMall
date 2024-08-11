package com.hust.mymall.pojo;

import lombok.Data;

import java.util.Iterator;

/**
 * @author Liubo Ren
 * @version 1.0
 */
@Data
public class Cart {

    private Integer productId;

    private Integer quantity;

    private Boolean productSelected;

    public Cart(Integer productId, Integer quantity, Boolean productSelected) {
        this.productId = productId;
        this.quantity = quantity;
        this.productSelected = productSelected;
    }

    public Cart() {
    }
}
