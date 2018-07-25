package com.o2o.nm.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.o2o.nm.entity.ServiceCategory;

import java.util.List;

/**
 * <p>
 * 服务分类表 Mapper 接口
 * </p>
 *
 * @author warning5
 * @since 2018-03-23
 */
public interface ServiceCategoryMapper extends BaseMapper<ServiceCategory> {

    List<ServiceCategory> getCats();
}
