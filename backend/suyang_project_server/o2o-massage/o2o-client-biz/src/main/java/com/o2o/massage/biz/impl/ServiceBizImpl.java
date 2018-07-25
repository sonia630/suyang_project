package com.o2o.massage.biz.impl;

import com.o2o.massage.biz.ServiceBiz;
import com.o2o.massage.dao.component.ServiceDao;
import com.o2o.massage.dao.entity.ServiceDef;
import com.o2o.nm.entity.ServiceCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.o2o.massage.srv.ServiceManager.DEFAULT_PROVIDERID;

@Service
public class ServiceBizImpl implements ServiceBiz {

    @Autowired
    ServiceDao serviceDao;

    @Override
    public List<ServiceDef> getServices() {
        return serviceDao.getDefaultServiceDefs(DEFAULT_PROVIDERID);
    }

    @Override
    public ServiceDef getDefaultService(String serviceId) {
        return serviceDao.getService(DEFAULT_PROVIDERID, serviceId);
    }

    @Override
    public ServiceDef getService(String providerId, String serviceId) {
        return serviceDao.getService(providerId, serviceId);
    }

    @Override
    public List<ServiceCategory> getCats() {
        return serviceDao.getCats();
    }
}
