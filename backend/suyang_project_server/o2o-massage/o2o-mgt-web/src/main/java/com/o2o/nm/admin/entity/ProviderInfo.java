package com.o2o.nm.admin.entity;

import cn.jeeweb.core.utils.DateUtils;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.o2o.nm.entity.BasicEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;

/**
 * <p>
 * 服务提供者基本信息表
 * </p>
 *
 * @author warning5
 * @since 2018-03-11
 */
@Getter
@Setter
@ToString
public class ProviderInfo extends BasicEntity<Long> {

    private static final long serialVersionUID = 1L;

    @Override
    public Long getId() {
        return providerId;
    }

    /**
     * 服务提供者ID
     */
    @TableId(value = "provider_id", type = IdType.AUTO)
    private Long providerId;
    /**
     * 服务提供者ID,关联user_info.user_id
     */
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

    public String getHealthReportTimeString() {
        if (healthReportTime != null) {
            return DateUtils.formatDate(healthReportTime, "dd/MM/yyyy");
        }
        return "";
    }

    public void setHealthReportTimeString(String date) {
        if (date != null) {
            try {
                healthReportTime = DateUtils.parseDate(date, "dd/MM/yyyy");
            } catch (ParseException e) {

            }
        }
    }

    /**
     * 纬度
     */
    private BigDecimal latitude;
    /**
     * 经度
     */
    private BigDecimal longitude;
    /**
     * 常用地址
     */
    private String commonAddress;

    /**
     * 服务者状态
     */
    private Integer status;
    /**
     * 备注说明
     */
    private String remarks;
}
