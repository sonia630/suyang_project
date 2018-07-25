package com.o2o.nm.mapper;

import com.o2o.nm.bo.*;
import com.o2o.nm.entity.ServiceCategory;
import com.o2o.nm.vo.ProviderList;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProviderOwnerMapper {

    List<ProviderBo> getProvidersOrderbyEval(ProviderList providerList);

    ProviderValidateBo getProviderValidateInfo(String userId);

    int getProvidersOrderbyEvalCount(ProviderList providerList);

    ProviderDetail getProviderDetail(String providerId);

    List<ServiceCategory> getProviderServiceCat(String providerId);

    List<ProviderServiceInfo> getProviderServiceCatInfo(@Param("catId") String catId, @Param("providerId") String providerId);

    ProviderEvalStatic getProviderEvalStatic(String providerId);

    Integer getProviderServiceTimes(String providerId);
}
