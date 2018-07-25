package com.o2o.massage.model.request;

import lombok.Data;

/**
 * @Author: zhongli
 * @Date: 2018/3/4 12:56
 * @Description:
 */
@Data
public class OrderFormRequest {
    private String providerUserId;
    private String serviceId;
    private Integer serviceCount;
    private Double latitude;
    private Double longitude;
}
