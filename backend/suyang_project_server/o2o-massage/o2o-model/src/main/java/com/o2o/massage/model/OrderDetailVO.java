package com.o2o.massage.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: zhongli
 * @Date: 2018/4/1 15:53
 * @Description:
 */
@Data
public class OrderDetailVO extends SimpleOrderInfoVO{
    private String contactPhone;

    private String contactMan;

    private BigDecimal journeyFee;

    private BigDecimal servicePrice;

    private Integer serviceCount;

    private BigDecimal totalAmount;

    private Date bookStartTime;

    private Date bookEndTime;

    private Date departureTime;

    private Date realServStartTime;

    private Date realServDoneTime;

    private Byte cancelReason;

    private Date cancelTime;

    private Date createTime;

    private Date customerConfirmTime;

    private Date providerConfirmTime;

    private Date providerGrabTime;
}
