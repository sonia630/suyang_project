package com.o2o.nm.admin.vo;

import com.o2o.nm.admin.entity.ServiceInfo;
import com.o2o.nm.admin.entity.SrvProviderSrvRel;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ServiceDefVo implements Serializable {

    private ServiceInfo serviceInfo;
    private SrvProviderSrvRel srvProviderSrvRel;
    private String[] catIds;
}
