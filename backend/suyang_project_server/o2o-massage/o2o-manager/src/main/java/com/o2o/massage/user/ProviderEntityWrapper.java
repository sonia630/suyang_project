package com.o2o.massage.user;

import com.o2o.massage.dao.entity.ProviderInfo;
import com.o2o.massage.dao.entity.UserBaseInfo;

/**
 * @Author: zhongli
 * @Date: 2018/3/8 0:07
 * @Description:
 */
public class ProviderEntityWrapper{
    private UserBaseInfo baseInfo;
    private ProviderInfo providerInfo;

    public ProviderEntityWrapper(){

    }
    public ProviderEntityWrapper(UserBaseInfo baseInfo,ProviderInfo providerInfo){
        this.baseInfo=baseInfo;
        this.providerInfo=providerInfo;
    }
    public UserBaseInfo getBaseInfo() {
        return baseInfo;
    }

    public void setBaseInfo(UserBaseInfo baseInfo) {
        this.baseInfo = baseInfo;
    }

    public ProviderInfo getProviderInfo() {
        return providerInfo;
    }

    public void setProviderInfo(ProviderInfo providerInfo) {
        this.providerInfo = providerInfo;
    }
}
