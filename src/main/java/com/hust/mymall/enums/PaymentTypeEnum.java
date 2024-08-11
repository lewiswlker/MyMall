package com.hust.mymall.enums;

import lombok.Getter;

/**
 * @author Liubo Ren
 * @version 1.0
 */
@Getter
public enum PaymentTypeEnum {
    PAY_ONLINE(1),
    ;
    private Integer code;

    PaymentTypeEnum(Integer code) {
        this.code = code;
    }
}
