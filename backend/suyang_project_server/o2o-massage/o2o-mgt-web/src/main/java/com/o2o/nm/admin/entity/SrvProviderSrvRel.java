package com.o2o.nm.admin.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.o2o.nm.entity.BasicEntity;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * <p>
 * 服务者能够提供的服务
 * </p>
 *
 * @author warning5
 * @since 2018-03-08
 */
@Getter
@Setter
public class SrvProviderSrvRel extends BasicEntity<Long> {

    private static final long serialVersionUID = 1L;

    /**
     * 服务者提供服务的映射ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 服务提供者的user_id
     */
    private String providerUserId;
    /**
     * 服务ID
     */
    private String serviceId;
    /**
     * 服务定价
     */
    private BigDecimal price;

    @TableField(exist = false)
    private String priceString;

    public void setPrice(BigDecimal price) {
        BigDecimal value = price.setScale(2, BigDecimal.ROUND_HALF_UP);
        setPriceString(value.toString());
        this.price = price;
    }

    /**
     * 排序
     */
    private Integer sortOrder;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 备注说明
     */
    private String remarks;
    /**
     * 计费方式
     */
    private Integer billingType;
    /**
     * 预计服务时长(单位分钟)
     */
    private Integer estimatedTime;

}