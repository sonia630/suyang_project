package com.o2o.nm;

import com.o2o.massage.core.utils.SnowFlake;
import com.o2o.massage.dao.component.ServiceDao;
import com.o2o.massage.dao.entity.ServiceInfo;
import com.o2o.massage.dao.entity.SrvProviderSrvRel;
import com.o2o.massage.dao.entity.type.DataStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: zhongli
 * @Date: 2018/4/6 13:07
 * @Description:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:spring-config.xml" })
public class ServiceTest {

    @Resource
    private ServiceDao serviceDao;

    @Test
    public void addService(){
        ServiceInfo serviceInfo=new ServiceInfo();
        serviceInfo.setServiceId(SnowFlake.getInstance().nextId()+"");
        serviceInfo.setServiceName("小儿推拿001");
        serviceInfo.setServiceSummary("身体棒棒，吃嘛嘛香");
        serviceInfo.setStatus((byte) DataStatus.NORMAL.getValue());
        serviceInfo.setCreateTime(new Date());
        serviceInfo.setRemarks("test");
        serviceDao.insertServiceInfo(serviceInfo);
    }

    @Test
    public void addServiceDef(){
        SrvProviderSrvRel srvProviderSrvRel=new SrvProviderSrvRel();
        srvProviderSrvRel.setPrice(BigDecimal.valueOf(100));
        srvProviderSrvRel.setProviderUserId("982158398155915264");
        srvProviderSrvRel.setServiceId("982128331052810240");
        srvProviderSrvRel.setEstimatedTime(60);
        srvProviderSrvRel.setStatus((byte)DataStatus.NORMAL.getValue());
        serviceDao.insertSrvProviderSrvRel(srvProviderSrvRel);
    }

}
