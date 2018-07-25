package com.o2o.massage.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: zhongli
 * @Date: 2018/3/4 13:24
 * @Description:
 */
@Data
public class ProviderSimpleInfoVO extends UserSimpleInfoVO {

    private String providerUserId;

    /**
     * 服务者教育程度
     */
    private String providerEduLev;

    /**
     * 服务者籍贯
     */
    private String providerBirthPlace;

    /**
     * 服务者简介
     */
    private String providerIntroduction;

    /**
     * 资质证书
     */
    private Long certificationId;

    /**
     * 健康状况
     */
    private String health;

    /**
     * 健康报告
     */
    private String healthReport;

    /**
     * 健康报告时间
     */
    private Date healthReportTime;

    /**
     * 纬度
     */
    private BigDecimal latitude;

    /**
     * 经度
     */
    private BigDecimal longitude;

    private String commonAddress;

}
