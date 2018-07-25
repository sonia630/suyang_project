package com.o2o.nm.bo;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class OrderDetail {
    String headPic;
    String providerName;
    String serviceName;
    String address;
    String contactPhone;
    BigDecimal servicePrice;
    BigDecimal journeyFee;
    BigDecimal totalAmount;
    Date bookStartTime;
    Date bookEndTime;
    int orderStatus;
    String serviceId;
    int evaluationId;
    String memberName;
}
