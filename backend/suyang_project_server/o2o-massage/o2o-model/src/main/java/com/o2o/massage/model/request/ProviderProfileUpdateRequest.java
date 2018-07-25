package com.o2o.massage.model.request;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: zhongli
 * @Date: 2018/3/14 11:24
 * @Description:
 */
@Data
public class ProviderProfileUpdateRequest extends ProfileUpdateRequest {
    private String providerBirthPlace;
    private String providerEduLev;
    private String healthReport;
    private Date healthReportTime;
    private String providerIntroduction;
    private String certificateReport;
    private String address;
    private BigDecimal longitude;
    private BigDecimal latitude;
}
