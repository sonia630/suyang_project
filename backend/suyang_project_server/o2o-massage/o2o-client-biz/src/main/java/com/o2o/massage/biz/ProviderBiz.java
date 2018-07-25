package com.o2o.massage.biz;

import com.o2o.massage.dao.entity.EvaluationInfo;
import com.o2o.massage.dao.entity.ProviderCertificationInfo;
import com.o2o.massage.dao.entity.ProviderInfo;
import com.o2o.massage.dao.entity.SrvProviderSrvRel;
import com.o2o.massage.model.ProviderScheduleShowVo;
import com.o2o.massage.model.request.EvalListRequest;
import com.o2o.massage.model.request.ProviderOrderFeeRequest;
import com.o2o.nm.bo.*;
import com.o2o.nm.entity.ServiceCategory;
import com.o2o.nm.vo.ProviderList;
import com.o2o.nm.vo.ProviderScheduleVo;
import org.apache.commons.lang3.tuple.Pair;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ProviderBiz {

    List<ProviderBo> getProvidersOrderbyEval(ProviderList providerList);

    void saveIntroduction(String providerId, String introduction);

    Map<Integer, List<String>> getProviderSchedulesByLongLat(ProviderScheduleVo providerScheduleVo);

    void updateProviderSchedule(String providerUserId, String updateUserId, Date serviceStartTime, Date serviceEndTime, String orderNo);

    long getServiceCount(String providerUserId);

    void saveEvaluation(EvaluationInfo evaluationInfo);

    void updateCommonAddress(String userId, String address, BigDecimal longitude, BigDecimal latitude);

    ProviderValidateBo getProviderValidateInfo(String userId);

    void cancelProviderSchedule(String userId, String orderNo);

    void updateServiceStatic(String providerUserId, String serviceId);

    void saveSchedule(String userId, Map<Date, List<Pair<Integer, Boolean>>> mapping);

    Map<String, List<ProviderScheduleShowVo>> getProviderSchedulesByUserId(String providerUserId, Date start);

    int getProvidersOrderbyEvalCount(ProviderList providerList);

    ProviderDetail getProviderDetail(String providerId);

    List<ServiceCategory> getProviderServiceCat(String providerId);

    List<ProviderServiceInfo> getProviderServiceCatInfo(String catId, String providerId);

    List<ProviderEval> getEvalFirst2(String providerId);

    BigDecimal getJourneyFee(ProviderOrderFeeRequest providerOrderFeeRequest);

    SrvProviderSrvRel getProviderAndSrv(String providerId, String serviceId);

    String getIntroduction(String userId);

    ProviderInfo getHealthInfo(String userId);

    void saveHealth(ProviderInfo providerInfo);

    List<ProviderCertificationInfo> getProviderCerts(String providerId);

    void saveCertification(ProviderCertificationInfo providerCertificationInfo);

    EvalAllInfo getEvalAllInfo(String providerId);

    List<ProviderEval> getEvalList(EvalListRequest request);

    int getEvalListCount(EvalListRequest request);
}