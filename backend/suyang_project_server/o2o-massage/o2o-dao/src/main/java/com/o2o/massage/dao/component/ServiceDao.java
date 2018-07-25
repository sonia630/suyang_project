package com.o2o.massage.dao.component;

import com.o2o.massage.dao.entity.ServiceDef;
import com.o2o.massage.dao.entity.ServiceInfo;
import com.o2o.massage.dao.entity.SrvProviderSrvRel;
import com.o2o.massage.dao.entity.SrvProviderSrvRelExample;
import com.o2o.massage.dao.entity.type.DataStatus;
import com.o2o.massage.dao.mapper.ServiceInfoMapper;
import com.o2o.massage.dao.mapper.SrvProviderSrvRelMapper;
import com.o2o.nm.entity.ServiceCategory;
import com.o2o.nm.mapper.ServiceCategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: zhongli
 * @Date: 2018/2/25 21:23
 * @Description:
 */
@Component
public class ServiceDao {

    @Autowired
    private ServiceInfoMapper serviceInfoMapper;

    @Autowired
    private ServiceCategoryMapper serviceCategoryMapper;
    @Autowired
    private SrvProviderSrvRelMapper srvRelMapper;

    public int insertServiceInfo(ServiceInfo record) {
        return serviceInfoMapper.insertSelective(record);
    }

    public ServiceInfo findOneByServiceId(String serviceId) {
        return serviceInfoMapper.selectByPrimaryKey(serviceId);
    }

    public List<SrvProviderSrvRel> qrySrvRelByUserId(String providerUserId) {
        SrvProviderSrvRelExample example = new SrvProviderSrvRelExample();
        example.createCriteria().andStatusEqualTo((byte) DataStatus.NORMAL.getValue())
                .andProviderUserIdEqualTo(providerUserId);
        example.setOrderByClause(" sort_order asc");
        return srvRelMapper.selectByExample(example);
    }

    public SrvProviderSrvRel qrySrvRelByUserIdAndServiceId(String providerUserId, String serviceId) {
        SrvProviderSrvRelExample example = new SrvProviderSrvRelExample();
        example.createCriteria().andStatusEqualTo((byte) DataStatus.NORMAL.getValue())
                .andProviderUserIdEqualTo(providerUserId)
                .andServiceIdEqualTo(serviceId);
        List<SrvProviderSrvRel> list = srvRelMapper.selectByExample(example);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    public List<ServiceDef> getDefaultServiceDefs(String defaultProviderId) {
        return serviceInfoMapper.getDefaultServiceDefs(defaultProviderId);
    }

    public int insertSrvProviderSrvRel(SrvProviderSrvRel srvProviderSrvRel){
        return srvRelMapper.insertSelective(srvProviderSrvRel);
    }

    public ServiceDef getService(String defaultProviderId, String serviceId) {
        return serviceInfoMapper.getService(defaultProviderId, serviceId);
    }

    public List<ServiceCategory> getCats() {
        return serviceCategoryMapper.getCats();
    }
}
