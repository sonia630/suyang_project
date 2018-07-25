package com.o2o.nm.admin.service;

import com.baomidou.mybatisplus.service.IService;
import com.o2o.nm.entity.ServiceCategory;

import java.util.List;

/**
 * <p>
 * 服务分类表 服务类
 * </p>
 *
 * @author warning5
 * @since 2018-03-23
 */
public interface ServiceCategoryService extends IService<ServiceCategory> {

    List<ServiceCategory> getCats();

    void delete(String catId) throws Exception;
}
