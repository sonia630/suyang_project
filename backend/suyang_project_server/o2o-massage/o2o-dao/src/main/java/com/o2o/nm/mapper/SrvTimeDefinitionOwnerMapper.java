package com.o2o.nm.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface SrvTimeDefinitionOwnerMapper {

    void deleteServiceTimeDefByDates(@Param("providerUserId") String providerUserId, @Param("dates") List<Date> dates);
}
