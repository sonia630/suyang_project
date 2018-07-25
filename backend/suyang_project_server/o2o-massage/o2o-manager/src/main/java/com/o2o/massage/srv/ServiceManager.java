package com.o2o.massage.srv;

import com.o2o.massage.dao.component.ServiceDao;
import com.o2o.massage.dao.entity.ServiceInfo;
import com.o2o.massage.dao.entity.SrvProviderSrvRel;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author: zhongli
 * @Date: 2018/3/9 20:00
 * @Description:
 */
@Component
public class ServiceManager {

    public static final String DEFAULT_PROVIDERID = "5456280903";
    @Resource
    private ServiceDao serviceDao;

    public QServiceInfoWithPrice qryServiceInfoWithDefaultPrice(String serviceId) {
        QServiceInfoWithPrice srvInfoWithPrice = qryServiceInfoWithDefaultPrice(serviceId, DEFAULT_PROVIDERID);
        return srvInfoWithPrice;
    }

    public QServiceInfoWithPrice qryServiceInfoWithDefaultPrice(String serviceId, String providerUserId) {
        ServiceInfo serviceInfo = serviceDao.findOneByServiceId(serviceId);
        SrvProviderSrvRel srvRel = serviceDao.qrySrvRelByUserIdAndServiceId(providerUserId, serviceId);
        if (serviceInfo == null || srvRel == null) {
            return null;
        }
        QServiceInfoWithPrice serviceInfoWithPrice = new QServiceInfoWithPrice();
        BeanUtils.copyProperties(serviceInfo, serviceInfoWithPrice);
        serviceInfoWithPrice.setPrice(srvRel.getPrice());
        serviceInfoWithPrice.setEstimateTime(srvRel.getEstimatedTime());
        return serviceInfoWithPrice;
    }
}
