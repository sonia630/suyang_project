package com.o2o.massage.model;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author: zhongli
 * @Date: 2018/3/4 13:10
 * @Description:
 */
@Data
public class ServiceInfoVO {
    /**
     *   服务定义ID
     */
    private String serviceId;

    /**
     *   服务名称
     */
    private String serviceName;

    /**
     *   服务定义描述
     */
    private String description;

    /**
     *   预估服务时长-min
     */
    private Integer estimateDuration;

    private BigDecimal servicePrice;
}
