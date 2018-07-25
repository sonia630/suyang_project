package com.o2o.nm.admin.service.impl;

import cn.jeeweb.core.query.wrapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.o2o.nm.admin.entity.ServiceInfoCategory;
import com.o2o.nm.admin.service.ServiceCategoryService;
import com.o2o.nm.admin.service.ServiceInfoCategoryService;
import com.o2o.nm.entity.ServiceCategory;
import com.o2o.nm.mapper.ServiceCategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务分类表 服务实现类
 * </p>
 *
 * @author warning5
 * @since 2018-03-23
 */
@Service
public class ServiceCategoryServiceImpl extends ServiceImpl<ServiceCategoryMapper, ServiceCategory> implements ServiceCategoryService {

    @Autowired
    ServiceInfoCategoryService serviceInfoCategoryService;

    @Override
    public List<ServiceCategory> getCats() {
        return selectList(new EntityWrapper<>());
    }

    @Override
    public void delete(String catId) throws Exception {
        int count = serviceInfoCategoryService.selectCount(new EntityWrapper<>(ServiceInfoCategory.class).eq("cat_id", catId));
        if (count > 0) {
            throw new Exception("该分类已被分配");
        }
        deleteById(catId);
    }
}
