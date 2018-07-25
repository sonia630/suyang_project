package com.o2o.nm.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 服务分类表
 * </p>
 *
 * @author warning5
 * @since 2018-03-23
 */
@Getter
@Setter
public class ServiceCategory extends BasicEntity<String> {


    @Override
    public String getId() {
        return catId;
    }

    /**
     * 分类ID
     */
    @TableId(type = IdType.UUID)
    private String catId;
    /**
     * 分类名称
     */
    private String catName;

    private int sort;


}
