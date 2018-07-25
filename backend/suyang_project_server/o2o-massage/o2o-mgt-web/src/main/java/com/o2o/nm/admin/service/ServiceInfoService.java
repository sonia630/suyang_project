package com.o2o.nm.admin.service;


import com.baomidou.mybatisplus.service.IService;
import com.o2o.nm.admin.entity.ServiceInfo;
import com.o2o.nm.admin.entity.SrvProviderSrvRel;
import com.o2o.nm.admin.vo.ServiceDefVo;

import java.util.List;

/**
 * <p>
 * 服务定义表 服务类
 * </p>
 *
 * @author warning5
 * @since 2018-03-05
 */
public interface ServiceInfoService extends IService<ServiceInfo> {

    List<ServiceDefVo> getAllServiceDef();

    void save(ServiceDefVo serviceInfo) throws Exception;

    ServiceDefVo getServiceDef(String userId, String serviceId);

    void enableServices(List<String> serviceIds);

    void disableServices(List<String> serviceIds);

    void relationUser(String userId, String relationId);

    void relationUser(SrvProviderSrvRel srvProviderSrvRel);

    List<ServiceDefVo> getUserRelationServices(String userId);

    void deRelation(Long relationId);
}
