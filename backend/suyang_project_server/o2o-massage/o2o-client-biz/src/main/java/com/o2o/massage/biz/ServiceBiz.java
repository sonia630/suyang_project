package com.o2o.massage.biz;

import com.o2o.massage.dao.entity.ServiceDef;
import com.o2o.nm.entity.ServiceCategory;

import java.util.List;

public interface ServiceBiz {
    List<ServiceDef> getServices();

    ServiceDef getDefaultService(String serviceId);

    ServiceDef getService(String providerId, String serviceId);

    List<ServiceCategory> getCats();

}
