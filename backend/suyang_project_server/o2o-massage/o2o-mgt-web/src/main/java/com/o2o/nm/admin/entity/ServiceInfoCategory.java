package com.o2o.nm.admin.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author warning5
 * @since 2018-03-25
 */
@Getter
@Setter
public class ServiceInfoCategory implements Serializable {


    @TableId(type = IdType.UUID)
    private String id;
    private String catId;
    private String serviceId;

}
