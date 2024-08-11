package com.hust.mymall.form;

import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotNull;

/**
 * @author Liubo Ren
 * @version 1.0
 */
@Data
public class OrderCreateForm {

    @NotNull
    private Integer shippingId;
}
