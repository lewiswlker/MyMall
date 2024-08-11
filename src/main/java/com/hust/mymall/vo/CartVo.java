package com.hust.mymall.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 购物车
 * @author Liubo Ren
 * @version 1.0
 */
@Data
public class CartVo {
    private List<CartProductVo> cartProductVoList;

    private Boolean selectAll;

    private BigDecimal cartTotalPrice;

    private  Integer cartTotalQuantity;
}
