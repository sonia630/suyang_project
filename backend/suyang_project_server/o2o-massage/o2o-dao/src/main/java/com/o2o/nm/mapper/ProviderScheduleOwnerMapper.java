package com.o2o.nm.mapper;

import com.o2o.massage.dao.entity.ProviderSchedule;
import com.o2o.nm.vo.ProviderScheduleVo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface ProviderScheduleOwnerMapper {

    List<ProviderSchedule> getProviderSchedules(ProviderScheduleVo providerScheduleVo);

    void cancelProviderSchedule(ProviderSchedule providerSchedule);

    List<ProviderSchedule> getProviderSchedulesByUserId(@Param("providerUserId") String providerUserId, @Param("start") Date start);

    void deleteProviderScheduleByDates(@Param("providerId") String providerId, @Param("dates") List<Date> dates);
}