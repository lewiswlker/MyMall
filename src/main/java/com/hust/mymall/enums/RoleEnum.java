package com.hust.mymall.enums;

import lombok.Getter;

/**
 * @author Liubo Ren
 * @version 1.0
 */
@Getter
public enum RoleEnum {
    ADMIN(0),
    CUSTOMER(1),
    ;

    private Integer role;
    RoleEnum(Integer role) {
        this.role = role;
    }
}
