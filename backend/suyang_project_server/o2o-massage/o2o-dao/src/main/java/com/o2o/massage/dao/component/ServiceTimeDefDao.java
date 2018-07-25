package com.o2o.massage.dao.component;

import com.o2o.massage.core.common.ShortDate;
import com.o2o.massage.core.utils.DateUtils;
import com.o2o.massage.dao.entity.SrvTimeDefinition;
import com.o2o.massage.dao.entity.SrvTimeDefinitionExample;
import com.o2o.massage.dao.entity.type.DataStatus;
import com.o2o.massage.dao.mapper.SrvTimeDefinitionMapper;
import com.o2o.nm.mapper.SrvTimeDefinitionOwnerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: zhongli
 * @Date: 2018/3/11 10:24
 * @Description:
 */
@Component
public class ServiceTimeDefDao {

    @Autowired
    private SrvTimeDefinitionMapper srvTimeDefinitionMapper;
    @Autowired
    private SrvTimeDefinitionOwnerMapper srvTimeDefinitionOwnerMapper;

    public List<SrvTimeDefinition> querySrvTimeDefinition(String providerUserId, int days) {
        Date startDate = new ShortDate(new Date()).toDate();
        Date endDate = DateUtils.getDateAfter(startDate, days);

        SrvTimeDefinitionExample example = new SrvTimeDefinitionExample();
        example.createCriteria().andStatusEqualTo((byte) DataStatus.NORMAL.getValue())
                .andProviderUserIdEqualTo(providerUserId).andStartTimeGreaterThanOrEqualTo(startDate)
                .andEndTimeLessThan(endDate);
        List<SrvTimeDefinition> resultList = srvTimeDefinitionMapper.selectByExample(example);
        return resultList;
    }

    public List<SrvTimeDefinition> querySrvTimeDefinitionByTime(String providerUserId, Date startTime, Date endTime) {
        SrvTimeDefinitionExample example = new SrvTimeDefinitionExample();
        example.createCriteria().andStatusEqualTo((byte) DataStatus.NORMAL.getValue())
                .andProviderUserIdEqualTo(providerUserId).andStartTimeLessThanOrEqualTo(startTime)
                .andEndTimeGreaterThanOrEqualTo(endTime);
        List<SrvTimeDefinition> resultList = srvTimeDefinitionMapper.selectByExample(example);
        return resultList;
    }

    public void insertServiceTimeDefs(List<SrvTimeDefinition> serviceTimeDefs) {
        List<Date> dates = serviceTimeDefs.stream().map(serviceTimeDef -> serviceTimeDef.getDate()).collect(Collectors.toList());
        srvTimeDefinitionOwnerMapper.deleteServiceTimeDefByDates(serviceTimeDefs.get(0).getProviderUserId(), dates);
        serviceTimeDefs.forEach(srvTimeDefinition -> {
            srvTimeDefinitionMapper.insertSelective(srvTimeDefinition);
        });
    }
}
