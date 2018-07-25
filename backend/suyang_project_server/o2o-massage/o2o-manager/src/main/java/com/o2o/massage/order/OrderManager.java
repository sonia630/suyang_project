package com.o2o.massage.order;

import com.o2o.massage.builder.SrvTimeBuilder;
import com.o2o.massage.core.exception.DataVersionChangedException;
import com.o2o.massage.dao.component.OrderDao;
import com.o2o.massage.dao.component.ServiceTimeDefDao;
import com.o2o.massage.dao.entity.OrderInfo;
import com.o2o.massage.dao.entity.SrvTimeDefinition;
import com.o2o.massage.model.ProviderDayHourInfo;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Author: zhongli
 * @Date: 2018/3/10 18:00
 * @Description:
 */
@Component
public class OrderManager {
    @Resource
    private OrderDao orderDao;

    @Resource
    private ServiceTimeDefDao serviceTimeDefDao;

    public List<ProviderDayHourInfo> queryProviderDayHourInfo(String providerUserId, int days) {
        List<SrvTimeDefinition> timeDefinitionList = serviceTimeDefDao.querySrvTimeDefinition(providerUserId, days);

        List<OrderInfo> orderInfoList = orderDao.queryFutureOrdersByProviderId(providerUserId, days);

        List<ProviderDayHourInfo> infoList = SrvTimeBuilder.getInstance().buildProviderDayHourInfo(providerUserId, days,
                timeDefinitionList, orderInfoList);
        return infoList;
    }

    public boolean checkProviderHasTimeBetween(String providerUserId, Date startTime, Date endTime) {
        List<SrvTimeDefinition> timeDefinitionList = serviceTimeDefDao
                .querySrvTimeDefinitionByTime(providerUserId, startTime, endTime);
        if (timeDefinitionList == null || timeDefinitionList.size() == 0) {
            return false;
        }

        List<OrderInfo> orderInfoList = orderDao.queryExistOrdersByProviderIdWithTime(providerUserId, startTime, endTime);
        if (orderInfoList != null && orderInfoList.size() > 0) {
            return false;
        }
        return true;
    }

    public int updateOrder(long orderId, OrderInfo record, int currentVersion, boolean throwException) {
        int updateCount = orderDao.updateOrder(orderId, record, currentVersion);
        if (updateCount <= 0 && throwException) {
            throw new DataVersionChangedException();
        }
        return updateCount;
    }
}
