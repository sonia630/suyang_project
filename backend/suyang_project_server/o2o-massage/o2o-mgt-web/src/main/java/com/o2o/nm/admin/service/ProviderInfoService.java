package com.o2o.nm.admin.service;

import com.baomidou.mybatisplus.service.IService;
import com.o2o.nm.admin.entity.ProviderInfo;
import com.o2o.nm.admin.vo.ProviderDetail;

/**
 * <p>
 * 服务提供者基本信息表 服务类
 * </p>
 *
 * @author warning5
 * @since 2018-03-11
 */
public interface ProviderInfoService extends IService<ProviderInfo> {

    ProviderDetail getProviderDetailInfo(String userId);

    void save(ProviderDetail providerDetail);

    void audit(String userId);
}
