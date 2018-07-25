package com.o2o.massage.dao.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ServiceDef implements Serializable {

    ServiceInfo serviceInfo;
    SrvProviderSrvRel srvProviderSrvRel;

}
