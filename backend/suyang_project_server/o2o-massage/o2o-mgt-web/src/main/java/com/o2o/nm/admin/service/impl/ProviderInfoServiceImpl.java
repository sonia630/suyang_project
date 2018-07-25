package com.o2o.nm.admin.service.impl;

import cn.jeeweb.core.query.wrapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.o2o.nm.admin.entity.ProviderInfo;
import com.o2o.nm.admin.mapper.ProviderInfoMapper;
import com.o2o.nm.admin.service.ProviderInfoService;
import com.o2o.nm.admin.vo.ProviderDetail;
import com.o2o.nm.sys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 服务提供者基本信息表 服务实现类
 * </p>
 *
 * @author warning5
 * @since 2018-03-11
 */
@Service
public class ProviderInfoServiceImpl extends ServiceImpl<ProviderInfoMapper, ProviderInfo> implements ProviderInfoService {

    @Autowired
    private UserService userService;

    @Override
    public ProviderDetail getProviderDetailInfo(String userId) {
        return baseMapper.getProviderDetailInfo(userId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(ProviderDetail providerDetail) {
        userService.updateById(providerDetail.getUserInfo());
        if (providerDetail.getProviderInfo().getProviderId() != null) {
            updateById(providerDetail.getProviderInfo());
        } else {
            providerDetail.getProviderInfo().setProviderUserId(providerDetail.getUserInfo().getUserId());
            insert(providerDetail.getProviderInfo());
        }
    }

    @Override
    public void audit(String userId) {
        ProviderInfo providerInfo = new ProviderInfo();
        providerInfo.setStatus(1);
        baseMapper.update(providerInfo, new EntityWrapper<>(ProviderInfo.class).eq("provider_user_id", userId));
    }
}
