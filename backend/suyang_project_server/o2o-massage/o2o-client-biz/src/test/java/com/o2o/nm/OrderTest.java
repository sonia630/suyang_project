package com.o2o.nm;

import com.o2o.massage.biz.ClientOrderBiz;
import com.o2o.massage.core.common.ApiRequestContext;
import com.o2o.massage.core.common.DeviceInfo;
import com.o2o.massage.core.utils.DateUtils;
import com.o2o.massage.model.request.OrderSubmitRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * @Author: zhongli
 * @Date: 2018/4/6 15:30
 * @Description:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:spring-config.xml" })
public class OrderTest {

    @Resource
    private ClientOrderBiz clientOrderBiz;

    @Test
    public void addOrder(){
        OrderSubmitRequest submitRequest=new OrderSubmitRequest();
        submitRequest.setAddress("北京市海淀区人民大学");
        submitRequest.setContactMan("lizhong");
        submitRequest.setContactPhone("15201291846");
        submitRequest.setProviderUserId("982158398155915264");
        submitRequest.setServiceCount(1);
        submitRequest.setServiceId("982128331052810240");
        submitRequest.setServicePrice(BigDecimal.valueOf(100));
        submitRequest.setCityId(100);
        submitRequest.setServiceStartTime(DateUtils.parseDate_YYYY_MM_dd_HH_MM_SS("2018-04-08 10:00:00"));
        ApiRequestContext apiRequestContext=new ApiRequestContext();
        apiRequestContext.setUserId("982135970344206336");
        DeviceInfo deviceInfo=new DeviceInfo();
        deviceInfo.setLatitude(39.970016337269676);
        deviceInfo.setLongitude(116.30698800086975);
        submitRequest.setLatitude(deviceInfo.getLatitude());
        submitRequest.setLongitude(deviceInfo.getLongitude());
        clientOrderBiz.orderSubmit(submitRequest,apiRequestContext);
    }
}
