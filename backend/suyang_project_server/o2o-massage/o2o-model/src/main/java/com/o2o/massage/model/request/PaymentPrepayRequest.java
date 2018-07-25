package com.o2o.massage.model.request;

import lombok.Data;

/**
 * @Author: zhongli
 * @Date: 2018/3/21 20:56
 * @Description:
 */
@Data
public class PaymentPrepayRequest {
    private String code;
    private String payToken;
    private Integer payWay;
    private String userIP;
}
