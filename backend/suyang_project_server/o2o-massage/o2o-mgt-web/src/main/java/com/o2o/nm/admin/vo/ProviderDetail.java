package com.o2o.nm.admin.vo;

import com.o2o.nm.admin.entity.ProviderInfo;
import com.o2o.nm.entity.UserInfo;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ProviderDetail implements Serializable {

    private ProviderInfo providerInfo;
    private UserInfo userInfo;
}
