package com.o2o.massage.model.request;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: zhongli
 * @Date: 2018/3/4 12:57
 * @Description:
 */
@Data
public class OrderSubmitRequest {
    private String providerUserId;
    private String serviceId;
    private Integer serviceCount;
    private Double latitude;
    private Double longitude;
    private Integer cityId;
    private String address;
    private String contactMan;
    private String contactPhone;
    private BigDecimal servicePrice;
    private BigDecimal journeyFee;
    private Integer familyMemberId;
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
    private Date serviceStartTime;
    private String memberId;
}
