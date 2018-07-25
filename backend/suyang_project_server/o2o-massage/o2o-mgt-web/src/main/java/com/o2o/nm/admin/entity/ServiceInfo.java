package com.o2o.nm.admin.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.o2o.nm.entity.BasicEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * <p>
 * 服务定义表
 * </p>
 *
 * @author warning5
 * @since 2018-03-05
 */
@Getter
@Setter
@ToString
public class ServiceInfo extends BasicEntity<String> {


    @Override
    public String getId() {
        return serviceId;
    }

    private static final long serialVersionUID = 1L;

    /**
     * 服务定义ID
     */
    @TableId(type = IdType.UUID)
    private String serviceId;
    /**
     * 服务名称
     */
    private String serviceName;
    /**
     * 服务简介
     */
    @JSONField(serialzeFeatures = SerializerFeature.WriteMapNullValue)
    private String serviceSummary;
    /**
     * 服务定义描述
     */
    private String description;
    /**
     * 排序
     */
    private Integer sortOrder;
    /**
     * 状态 1、有效 0、无效
     */
    private Integer status;
    /**
     * 备注说明
     */
    private String remarks;

    /**
     * 服务图片
     */
    private String pic;
}
