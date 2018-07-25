package com.o2o.massage.builder;

import com.o2o.massage.core.common.ShortDate;
import com.o2o.massage.core.common.ShortTime;
import com.o2o.massage.core.constants.EnumWorkTimePoint;
import com.o2o.massage.dao.entity.OrderInfo;
import com.o2o.massage.dao.entity.SrvTimeDefinition;
import com.o2o.massage.model.ProviderDayHourInfo;
import com.o2o.massage.model.TimePointInfo;
import org.apache.commons.lang3.time.DateUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: zhongli
 * @Date: 2018/3/11 18:23
 * @Description:
 */
public class SrvTimeBuilder {
    private static SrvTimeBuilder INSTANCE = new SrvTimeBuilder();

    private SrvTimeBuilder() {

    }

    public static SrvTimeBuilder getInstance() {
        return INSTANCE;
    }

    /**
     * 不指定推拿师,未来若干天对应时间点
     *
     * @param days
     * @return
     */
    public List<ProviderDayHourInfo> buildDefaultDayHourInfo(int days) {
        List<ProviderDayHourInfo> dayHourInfoList = new ArrayList<>();

        for (int i = 0; i < days; i++) {
            Date currentDate = DateUtils.addDays(new Date(), i);
            ShortDate currentShortDay = ShortDate.valueOf(currentDate);
            ProviderDayHourInfo providerDayHourInfo = new ProviderDayHourInfo();
            providerDayHourInfo.setDay(currentShortDay.toDate());
            List<TimePointInfo> hourInfoList = new ArrayList<>();
            ShortTime shortTimeStart = ShortTime.parseHHmm(EnumWorkTimePoint.START_TIME.title());
            ShortTime shortTimeEnd = ShortTime.parseHHmm(EnumWorkTimePoint.END_TIME.title());

            ShortTime shortTimeTmp = shortTimeStart;
            while (shortTimeTmp.before(shortTimeEnd)) {
                TimePointInfo timePointInfo = new TimePointInfo();
                Date currentTimePoint = DateUtils.addHours(currentDate, shortTimeTmp.getHour());
                currentTimePoint = DateUtils.addMinutes(currentTimePoint, shortTimeTmp.getMinute());
                hourInfoList.add(timePointInfo);
                timePointInfo.setFree(true);
                timePointInfo.setStartTime(currentTimePoint);
                shortTimeTmp = shortTimeTmp.addMinutes(30);
            }
            providerDayHourInfo.setHourInfoList(hourInfoList);
            dayHourInfoList.add(providerDayHourInfo);
        }

        return dayHourInfoList;
    }

    public List<ProviderDayHourInfo> buildProviderDayHourInfo(String providerUserId, int days,
                                                              List<SrvTimeDefinition> srvTimeDefinitions, List<OrderInfo> orderInfos) {
        List<ProviderDayHourInfo> defaultInfos = buildDefaultDayHourInfo(days);
        for (ProviderDayHourInfo providerDayHourInfo : defaultInfos) {
            List<TimePointInfo> hourInfoList = providerDayHourInfo.getHourInfoList();
            for (TimePointInfo timePointInfo : hourInfoList) {
                boolean isInProviderSrvTime = isInProviderSrvTime(timePointInfo.getStartTime(), srvTimeDefinitions);
                if (isInProviderSrvTime) {
                    boolean hasOrder = hasOrderAtTheTime(timePointInfo.getStartTime(), orderInfos);
                    if (hasOrder) {
                        timePointInfo.setNotFreeReason("EXIST_ORDER");
                    } else {
                        timePointInfo.setFree(true);
                    }
                } else {
                    timePointInfo.setNotFreeReason("NO_SRV_TIME_DEFINITION");
                }
            }
        }
        return defaultInfos;
    }


    private boolean isInProviderSrvTime(Date currentTime, List<SrvTimeDefinition> srvTimeDefinitions) {
        if (srvTimeDefinitions == null || srvTimeDefinitions.size() == 0) {
            return false;
        }
        for (SrvTimeDefinition definition : srvTimeDefinitions) {
            if (currentTime.compareTo(definition.getStartTime()) >= 0
                    && currentTime.compareTo(definition.getEndTime()) <= 0) {
                return true;
            }
        }
        return false;
    }

    private boolean hasOrderAtTheTime(Date currentTime, List<OrderInfo> orderInfos) {
        if (orderInfos == null || orderInfos.size() == 0) {
            return false;
        }
        for (OrderInfo orderInfo : orderInfos) {
            if (currentTime.compareTo(orderInfo.getBookStartTime()) >= 0
                    && currentTime.compareTo(orderInfo.getBookEndTime()) <= 0) {
                return true;
            }
        }
        return false;
    }
}
