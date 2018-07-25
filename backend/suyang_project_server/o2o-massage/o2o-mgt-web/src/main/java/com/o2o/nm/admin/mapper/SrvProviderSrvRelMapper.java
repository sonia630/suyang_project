package com.o2o.nm.admin.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.o2o.nm.admin.entity.SrvProviderSrvRel;

/**
 * <p>
 * 服务者能够提供的服务 Mapper 接口
 * </p>
 *
 * @author warning5
 * @since 2018-03-08
 */
public interface SrvProviderSrvRelMapper extends BaseMapper<SrvProviderSrvRel> {

    void updatePriceAndEsTimeAndSort(SrvProviderSrvRel srvProviderSrvRel);
}
