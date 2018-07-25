package com.o2o.nm.admin.service;


import com.baomidou.mybatisplus.service.IService;
import com.o2o.nm.admin.entity.SrvProviderSrvRel;

/**
 * <p>
 * 服务者能够提供的服务 服务类
 * </p>
 *
 * @author warning5
 * @since 2018-03-08
 */
public interface SrvProviderSrvRelService extends IService<SrvProviderSrvRel> {

    void updatePriceAndEsTimeAndSort(SrvProviderSrvRel srvProviderSrvRel);

    void realtionUser(String userId, String relationId);
}
