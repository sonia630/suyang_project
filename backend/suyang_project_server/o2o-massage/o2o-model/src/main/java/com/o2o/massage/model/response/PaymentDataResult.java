package com.o2o.massage.model.response;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author: zhongli
 * @Date: 2018/3/15 14:57
 * @Description:
 */
@Data
public class PaymentDataResult {
    private BigDecimal totalFee;
    private BigDecimal toBePaidFee;
    private String serviceName;
    private Integer payTradeStatus;
    private String payToken;
}
