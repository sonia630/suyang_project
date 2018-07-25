package com.o2o.massage.model;

import lombok.Data;

import java.util.Date;

/**
 * @Author: zhongli
 * @Date: 2018/3/14 0:31
 * @Description:
 */
@Data
public class SimpleOrderInfoVO {
    protected String orderNo;
    protected String customerUserId;
    protected String customerName;
    protected String providerUserId;
    protected String providerName;
    protected Integer orderStatus;
    protected String address;
    protected String serviceId;
    protected String serviceName;
    protected Date bookStartTime;
}
