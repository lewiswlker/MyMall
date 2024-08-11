package com.hust.mymall.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author Liubo Ren
 * @version 1.0
 */
@Data
public class ShoppingForm {
    @NotBlank
    private String receiverName;


    private String receiverPhone = "010";

    @NotBlank
    private String receiverMobile;

    @NotBlank
    private String receiverProvince;

    @NotBlank
    private String receiverCity;

    @NotBlank
    private String receiverDistrict;

    @NotBlank
    private String receiverAddress;

    @NotBlank
    private String receiverZip;
}
