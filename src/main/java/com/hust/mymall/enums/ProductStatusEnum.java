package com.hust.mymall.enums;

import lombok.Getter;

/**
 * @author Liubo Ren
 * @version 1.0
 */
@Getter
public enum ProductStatusEnum {
    /**
     * 商品状态.1-在售 2-下架 3-删除
     */
    ON_SALE(1),
    OFF_SALE(2),
    DELETE(3),

    ;

    Integer code;

    ProductStatusEnum(Integer code) {
        this.code = code;
    }
}
