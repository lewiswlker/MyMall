package com.hust.mymall.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Liubo Ren
 * @version 1.0
 */
@Data
public class CartProductVo {
    private Integer productId;

    private Integer quantity; // 购买的数量

    private String productName;

    private String productSubtitle;

    private String productMainImage;

    private BigDecimal productPrice;

    private Integer productStatus;

    private BigDecimal productTotalPrice; // 单价*数量

    private Integer productStock;


    private Boolean productSelected; // 商品是否选中

    public CartProductVo(Integer productId, Integer quantity, String productName, String productSubtitle,
                         String productMainImage, BigDecimal productPrice, Integer productStatus,
                         BigDecimal productTotalPrice, Integer productStock, Boolean productSelected) {
        this.productId = productId;
        this.quantity = quantity;
        this.productName = productName;
        this.productSubtitle = productSubtitle;
        this.productMainImage = productMainImage;
        this.productPrice = productPrice;
        this.productStatus = productStatus;
        this.productTotalPrice = productTotalPrice;
        this.productStock = productStock;
        this.productSelected = productSelected;
    }
}
