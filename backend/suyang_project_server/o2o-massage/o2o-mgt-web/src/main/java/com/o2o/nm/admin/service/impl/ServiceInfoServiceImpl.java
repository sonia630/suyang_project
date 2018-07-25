package com.o2o.nm.admin.service.impl;


import cn.jeeweb.core.query.wrapper.EntityWrapper;
import cn.jeeweb.modules.sys.Constants;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.o2o.nm.admin.entity.ServiceInfo;
import com.o2o.nm.admin.entity.ServiceInfoCategory;
import com.o2o.nm.admin.entity.SrvProviderSrvRel;
import com.o2o.nm.admin.mapper.ServiceInfoMapper;
import com.o2o.nm.admin.service.ServiceInfoCategoryService;
import com.o2o.nm.admin.service.ServiceInfoService;
import com.o2o.nm.admin.service.SrvProviderSrvRelService;
import com.o2o.nm.admin.vo.ServiceDefVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static cn.jeeweb.modules.sys.Constants.DEFAULT_PROVIDER_ID;

/**
 * <p>
 * 服务定义表 服务实现类
 * </p>
 *
 * @author warning5
 * @since 2018-03-05
 */
@Service
public class ServiceInfoServiceImpl extends ServiceImpl<ServiceInfoMapper, ServiceInfo> implements ServiceInfoService {

    @Autowired
    private SrvProviderSrvRelService srvProviderSrvRelService;
    @Autowired
    private ServiceInfoCategoryService serviceInfoCategoryService;

    @Override
    public List<ServiceDefVo> getAllServiceDef() {
        return baseMapper.selectServiceDefs(DEFAULT_PROVIDER_ID);
    }


    @Override
    public List<ServiceDefVo> getUserRelationServices(String userId) {
        return baseMapper.getUserRelationServices(DEFAULT_PROVIDER_ID, userId);
    }

    @Override
    public void deRelation(Long relationId) {
        srvProviderSrvRelService.deleteById(relationId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(ServiceDefVo serviceDefVo) throws Exception {

        if (Strings.isNullOrEmpty(serviceDefVo.getServiceInfo().getServiceId())) {
            int count = selectCount(new EntityWrapper<>(ServiceInfo.class).eq("service_name", serviceDefVo.getServiceInfo().getServiceName()));
            if (count > 0) {
                throw new Exception("已包含该服务名");
            }
            insert(serviceDefVo.getServiceInfo());
        } else {
            updateById(serviceDefVo.getServiceInfo());
        }
        serviceDefVo.getSrvProviderSrvRel().setProviderUserId(DEFAULT_PROVIDER_ID);
        serviceDefVo.getSrvProviderSrvRel().setServiceId(serviceDefVo.getServiceInfo().getServiceId());
        int count = srvProviderSrvRelService.selectCount(new EntityWrapper<>(SrvProviderSrvRel.class).eq("provider_user_id", DEFAULT_PROVIDER_ID)
                .eq("service_id", serviceDefVo.getServiceInfo().getServiceId()));
        serviceDefVo.getSrvProviderSrvRel().setSortOrder(serviceDefVo.getServiceInfo().getSortOrder());
        if (count > 0) {
            srvProviderSrvRelService.updatePriceAndEsTimeAndSort(serviceDefVo.getSrvProviderSrvRel());
        } else {
            srvProviderSrvRelService.insert(serviceDefVo.getSrvProviderSrvRel());
        }

        if (serviceDefVo.getCatIds() != null) {
            List<ServiceInfoCategory> values = Lists.newArrayList();
            for (String catId : serviceDefVo.getCatIds()) {
                ServiceInfoCategory serviceInfoCategory = new ServiceInfoCategory();
                serviceInfoCategory.setCatId(catId);
                serviceInfoCategory.setServiceId(serviceDefVo.getServiceInfo().getServiceId());
                values.add(serviceInfoCategory);
            }
            serviceInfoCategoryService.delete(new EntityWrapper<>(ServiceInfoCategory.class).eq("service_id", serviceDefVo.getServiceInfo().getServiceId()));
            serviceInfoCategoryService.insertBatch(values);
        }
    }

    @Override
    public ServiceDefVo getServiceDef(String userId, String serviceId) {
        ServiceDefVo serviceDefVo = baseMapper.selectServiceDef(StringUtils.isEmpty(userId) ? DEFAULT_PROVIDER_ID : userId, serviceId, StringUtils.isEmpty(userId) || DEFAULT_PROVIDER_ID.equals(userId));
        List<ServiceInfoCategory> list = serviceInfoCategoryService.selectList(new EntityWrapper<>(ServiceInfoCategory.class).eq("service_id", serviceId));
        List<String> mapping = list.stream().map(cat -> cat.getCatId()).collect(Collectors.toList());
        serviceDefVo.setCatIds(mapping.toArray(new String[0]));
        return serviceDefVo;
    }

    @Override
    public void enableServices(List<String> serviceIds) {
        baseMapper.changeServiceStatus(serviceIds, Constants.enable);
    }

    @Override
    public void disableServices(List<String> serviceIds) {
        baseMapper.changeServiceStatus(serviceIds, Constants.disable);
    }

    @Override
    public void relationUser(String userId, String relationId) {
        srvProviderSrvRelService.realtionUser(userId, relationId);
    }

    @Override
    public void relationUser(SrvProviderSrvRel srvProviderSrvRel) {
        if (srvProviderSrvRel.getId() == null) {
            srvProviderSrvRelService.insert(srvProviderSrvRel);
        } else {
            srvProviderSrvRelService.updateById(srvProviderSrvRel);
        }
    }
}
