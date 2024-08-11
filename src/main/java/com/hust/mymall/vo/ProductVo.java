package com.hust.mymall.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Liubo Ren
 * @version 1.0
 */
@Data
public class ProductVo {
    private Integer id;

    private Integer categoryId;

    private String name;

    private String subtitle;

    private String mainImage;

    private BigDecimal price;

    private Integer status;
}
