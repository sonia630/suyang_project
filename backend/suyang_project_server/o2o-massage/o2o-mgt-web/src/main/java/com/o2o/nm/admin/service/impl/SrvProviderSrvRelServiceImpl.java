package com.o2o.nm.admin.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.o2o.nm.admin.entity.SrvProviderSrvRel;
import com.o2o.nm.admin.mapper.SrvProviderSrvRelMapper;
import com.o2o.nm.admin.service.SrvProviderSrvRelService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务者能够提供的服务 服务实现类
 * </p>
 *
 * @author warning5
 * @since 2018-03-08
 */
@Service
public class SrvProviderSrvRelServiceImpl extends ServiceImpl<SrvProviderSrvRelMapper, SrvProviderSrvRel> implements SrvProviderSrvRelService {

    @Override
    public void updatePriceAndEsTimeAndSort(SrvProviderSrvRel srvProviderSrvRel) {
        baseMapper.updatePriceAndEsTimeAndSort(srvProviderSrvRel);
    }

    @Override
    public void realtionUser(String userId, String relationId) {
        SrvProviderSrvRel srvProviderSrvRel = selectById(relationId);
        srvProviderSrvRel.setId(null);
        srvProviderSrvRel.setProviderUserId(userId);
        baseMapper.insert(srvProviderSrvRel);
    }
}
