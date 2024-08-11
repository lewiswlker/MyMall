package com.hust.mymall.vo;

import com.hust.mymall.pojo.Shopping;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author Liubo Ren
 * @version 1.0
 */
@Data
public class OrderVo {

    private Long orderNo;

    private BigDecimal payment;

    private Integer paymentType;

    private Integer postage;

    private Integer status;

    private Date paymentTime;

    private Date sendTime;

    private Date endTime;

    private Date closeTime;

    private Date createTime;

    private List<OrderItemVo> orderItemVoList;

    private Integer shoppingId;

    private Shopping shopping;
}
