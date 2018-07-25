package com.o2o.massage.biz.impl;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.o2o.massage.biz.ProviderBiz;
import com.o2o.massage.core.utils.Constants;
import com.o2o.massage.core.utils.DateUtils;
import com.o2o.massage.dao.component.ProviderDao;
import com.o2o.massage.dao.component.ServiceTimeDefDao;
import com.o2o.massage.dao.entity.*;
import com.o2o.massage.model.ProviderScheduleShowVo;
import com.o2o.massage.model.request.EvalListRequest;
import com.o2o.massage.model.request.ProviderOrderFeeRequest;
import com.o2o.massage.user.UserManager;
import com.o2o.nm.bo.*;
import com.o2o.nm.entity.ServiceCategory;
import com.o2o.nm.entity.UserType;
import com.o2o.nm.vo.ProviderList;
import com.o2o.nm.vo.ProviderScheduleVo;
import com.o2o.nm.vo.ProviderVo;
import de.alpharogroup.jgeohash.distance.DistanceCalculator;
import de.alpharogroup.jgeohash.distance.MeasuringUnit;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ProviderBizImpl implements ProviderBiz {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private ProviderDao providerDao;
    @Autowired
    private ServiceTimeDefDao serviceTimeDefDao;

    @Value("${appointment.start}")
    private String _start;
    @Value("${appointment.end}")
    private String _end;
    @Value("${appointment.span}")
    private int span;

    Map<String, Integer> appointmentMap = Maps.newHashMap();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");

    @PostConstruct
    public void init() {
        try {
            Date start = simpleDateFormat.parse(_start);
            Date end = simpleDateFormat.parse(_end);
            int index = 1;
            for (long i = start.getTime(); i <= end.getTime(); ) {
                appointmentMap.put(simpleDateFormat.format(new Date(i)), index++);
                i += span * 60 * 1000;
            }
        } catch (ParseException e) {
            logger.error("{}", e);
        }
    }

    @Override
    public List<ProviderBo> getProvidersOrderbyEval(ProviderList providerList) {
        ProviderVo providerVo = new ProviderVo();
        providerVo.setProviderStatus(1);
        providerVo.setUserStatus(1);
        providerVo.setUserTypes(UserType.getProviderCode());
        providerList.setProviderVo(providerVo);
        return providerDao.getProvidersOrderbyEval(providerList);
    }

    @Override
    public int getProvidersOrderbyEvalCount(ProviderList providerList) {
        ProviderVo providerVo = new ProviderVo();
        providerVo.setProviderStatus(1);
        providerVo.setUserStatus(1);
        providerVo.setUserTypes(UserType.getProviderCode());
        providerList.setProviderVo(providerVo);
        return providerDao.getProvidersOrderbyEvalCount(providerList);
    }

    @Override
    public ProviderDetail getProviderDetail(String providerId) {
        ProviderDetail providerDetail = providerDao.getProviderInfoDetail(providerId);
        providerDetail.setProviderEvalStatic(providerDao.getProviderEvalStatic(providerId));
        providerDetail.setServiceTimes(providerDao.getProviderServiceTimes(providerId));
        return providerDetail;
    }

    @Override
    public List<ServiceCategory> getProviderServiceCat(String providerId) {
        return providerDao.getProviderServiceCat(providerId);
    }

    @Override
    public List<ProviderServiceInfo> getProviderServiceCatInfo(String catId, String providerId) {
        return providerDao.getProviderServiceCatInfo(catId, providerId);
    }

    @Override
    public List<ProviderEval> getEvalFirst2(String providerId) {
        return providerDao.getEvalList(providerId, 0, 2);
    }

    @Override
    public BigDecimal getJourneyFee(ProviderOrderFeeRequest providerOrderFeeRequest) {
        ProviderInfo providerInfo = providerDao.findOneByUserId(providerOrderFeeRequest.getProviderId());
        double distanceMove = DistanceCalculator.distance(providerOrderFeeRequest.getUserLat().doubleValue(), providerOrderFeeRequest.getUserLng().doubleValue(),
                providerInfo.getLatitude().doubleValue(), providerInfo.getLongitude().doubleValue(), MeasuringUnit.METER);
        return new BigDecimal(distanceMove / 1000 * 2).setScale(0, BigDecimal.ROUND_HALF_DOWN);
    }

    @Override
    public SrvProviderSrvRel getProviderAndSrv(String providerId, String serviceId) {
        return providerDao.getProviderAndSrv(providerId, serviceId);
    }

    @Override
    public String getIntroduction(String providerId) {
        return providerDao.getIntroduction(providerId);
    }

    @Override
    public ProviderInfo getHealthInfo(String userId) {
        return providerDao.findOneByUserId(userId);
    }

    @Override
    public void saveHealth(ProviderInfo providerInfo) {
        Preconditions.checkArgument(StringUtils.isNotBlank(providerInfo.getHealth())
                , "健康状况不能为空");
        Preconditions.checkArgument(StringUtils.isNotBlank(providerInfo.getHealthReport())
                , "健康报告不能为空");
        Preconditions.checkArgument(providerInfo.getHealthReportTime() != null
                , "健康报告时间不得为空");
        providerDao.updateProvider(providerInfo);
    }

    @Override
    public List<ProviderCertificationInfo> getProviderCerts(String providerId) {
        return providerDao.getProviderCerts(providerId);
    }

    @Override
    public void saveCertification(ProviderCertificationInfo providerCertificationInfo) {
        Preconditions.checkArgument(StringUtils.isNotBlank(providerCertificationInfo.getDescr())
                , "证书描述不能为空");
        Preconditions.checkArgument(StringUtils.isNotBlank(providerCertificationInfo.getName())
                , "证书名称不能为空");
        Preconditions.checkArgument(StringUtils.isNotBlank(providerCertificationInfo.getPic())
                , "证书不得为空");

        providerDao.saveCertification(providerCertificationInfo);
    }

    @Override
    public EvalAllInfo getEvalAllInfo(String providerId) {
        return providerDao.getEvalAllInfo(providerId);
    }

    @Override
    public List<ProviderEval> getEvalList(EvalListRequest request) {
        switch (request.getCatId()) {
            case "1":
                return providerDao.getEvalList(EvalBo.builder().providerId(request.getProviderId())
                        .count(request.getCount()).start(request.getStart()).evalScoreBegin(1).evalScoreEnd(5).build());
            case "2":
                return providerDao.getEvalList(EvalBo.builder().providerId(request.getProviderId())
                        .count(request.getCount()).start(request.getStart()).evalScoreBegin(5).evalScoreEnd(5).build());
            case "3":
                return providerDao.getEvalList(EvalBo.builder().providerId(request.getProviderId())
                        .count(request.getCount()).start(request.getStart()).evalScoreBegin(3).evalScoreEnd(4).build());
            case "4":
                return providerDao.getEvalList(EvalBo.builder().providerId(request.getProviderId())
                        .count(request.getCount()).start(request.getStart()).evalScoreBegin(1).evalScoreEnd(2).build());

        }
        return Lists.newArrayList();
    }

    @Override
    public int getEvalListCount(EvalListRequest request) {
        switch (request.getCatId()) {
            case "1":
                return providerDao.getEvalListCount(EvalBo.builder().providerId(request.getProviderId())
                        .evalScoreBegin(1).evalScoreEnd(5).build());
            case "2":
                return providerDao.getEvalListCount(EvalBo.builder().providerId(request.getProviderId())
                        .evalScoreBegin(5).evalScoreEnd(5).build());
            case "3":
                return providerDao.getEvalListCount(EvalBo.builder().providerId(request.getProviderId())
                        .evalScoreBegin(3).evalScoreEnd(4).build());
            case "4":
                return providerDao.getEvalListCount(EvalBo.builder().providerId(request.getProviderId())
                        .evalScoreBegin(1).evalScoreEnd(2).build());

        }

        return 0;
    }

    @Override
    public void saveIntroduction(String providerId, String introduction) {
        providerDao.saveIntroduction(providerId, introduction);
    }

    @Override
    public Map<Integer, List<String>> getProviderSchedulesByLongLat(ProviderScheduleVo providerScheduleVo) {
        List<ProviderSchedule> providerSchedules = providerDao.getProviderSchedules(providerScheduleVo);
        Map<Integer, List<String>> map = Maps.newHashMap();
        providerSchedules.forEach(providerSchedule -> {
            map.compute(providerSchedule.getSlot(), (k, v) -> {
                if (v == null) {
                    v = Lists.newArrayList();
                }
                v.add(providerSchedule.getProviderId());
                return v;
            });
        });
        return map;

    }

    @Override
    public void updateProviderSchedule(String providerUserId, String updateUserId, Date serviceStartTime, Date serviceEndTime, String orderNo) {
        List<ProviderSchedule> list = Lists.newArrayList();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(serviceStartTime);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        for (long i = serviceStartTime.getTime(); i <= serviceEndTime.getTime(); ) {
            Integer index = appointmentMap.get(simpleDateFormat.format(new Date(i)));
            if (index != null) {
                ProviderSchedule providerSchedule = new ProviderSchedule();
                providerSchedule.setOrderNo(orderNo);
                providerSchedule.setProviderId(providerUserId);
                providerSchedule.setSlot(index);
                providerSchedule.setDate(calendar.getTime());
                providerSchedule.setUpdateTime(new Date());
                providerSchedule.setUpdateBy("");
                list.add(providerSchedule);
            }
            i += span * 60 * 1000;
        }
        providerDao.updateProviderSchedule(list);
    }

    @Override
    public long getServiceCount(String providerUserId) {
        return providerDao.getServiceCount(providerUserId);
    }

    @Override
    public void saveEvaluation(EvaluationInfo evaluationInfo) {
        providerDao.saveEvaluation(evaluationInfo);
    }

    @Override
    public void updateCommonAddress(String userId, String address, BigDecimal longitude, BigDecimal latitude) {
        providerDao.updateCommonAddress(userId, address, longitude, latitude);
    }

    @Override
    public ProviderValidateBo getProviderValidateInfo(String userId) {
        return providerDao.getProviderValidateInfo(userId);
    }

    @Override
    public void cancelProviderSchedule(String userId, String orderNo) {
        providerDao.cancelProviderSchedule(userId, orderNo);
    }

    @Override
    public void updateServiceStatic(String providerUserId, String serviceId) {
        providerDao.updateServiceStatic(providerUserId, serviceId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveSchedule(String providerId, Map<Date, List<Pair<Integer, Boolean>>> mapping) {
        ProviderInfo providerInfo = providerDao.findOneByUserId(providerId);
        if (providerInfo.getLatitude() == null || providerInfo.getLongitude() == null) {
            throw new RuntimeException("请先设置顾问常用地址");
        }
        mapping.forEach((date, times) -> {
            List<SrvTimeDefinition> serviceTimeDefs = Lists.newArrayList();
            List<ProviderSchedule> providerSchedules = Lists.newArrayList();
            times.sort(Comparator.comparing(Pair::getLeft));
            Integer next = times.get(0).getLeft();
            SrvTimeDefinition srvTimeDefinition = null;
            for (int i = 0; i < times.size(); i++) {
                Integer slot = times.get(i).getLeft();
                if (slot.equals(next)) {
                    if (srvTimeDefinition == null) {
                        srvTimeDefinition = create(date, slot);
                    }
                    if (i + 1 != times.size()) {
                        next++;
                    }
                } else {
                    if (srvTimeDefinition != null) {
                        buildServiceTimeDef(srvTimeDefinition, providerId, date, next);
                        serviceTimeDefs.add(srvTimeDefinition);
                        srvTimeDefinition = create(date, slot);
                        next = slot;
                        if (i + 1 != times.size()) {
                            next++;
                        }
                    }
                }
                if (times.get(i).getRight()) {
                    providerSchedules.add(buildProviderSchedule(date, providerInfo, slot));
                }
            }
            buildServiceTimeDef(srvTimeDefinition, providerId, date, next + 1);
            serviceTimeDefs.add(srvTimeDefinition);
            providerDao.insertProviderSchedules(providerSchedules);
            serviceTimeDefDao.insertServiceTimeDefs(serviceTimeDefs);
        });
    }

    @Override
    public Map<String, List<ProviderScheduleShowVo>> getProviderSchedulesByUserId(String providerUserId, Date start) {
        List<ProviderSchedule> schedules = providerDao.getProviderSchedulesByUserId(providerUserId, start);
        Map<String, List<ProviderScheduleShowVo>> result = Maps.newHashMap();
        schedules.forEach(providerSchedule -> {
            result.compute(DateUtils.formatDate(providerSchedule.getDate(), "yyyy-MM-dd"), (k, v) -> {
                if (v == null) {
                    v = Lists.newArrayList();
                }
                v.add(ProviderScheduleShowVo.builder().orderNo(StringUtils.isBlank(providerSchedule.getOrderNo()) ? Constants.SCHEDULE_NO_FREEZE : Constants.SCHEDULE_FREEZE)
                        .slot(providerSchedule.getSlot()).build());
                return v;
            });
        });
        return result;
    }

    private SrvTimeDefinition create(Date date, int slot) {
        SrvTimeDefinition srvTimeDefinition = new SrvTimeDefinition();
        srvTimeDefinition.setStartTime(Schedule.getDate(date, slot));
        return srvTimeDefinition;
    }

    private void buildServiceTimeDef(SrvTimeDefinition srvTimeDefinition, String userId, Date date, int slot) {
        srvTimeDefinition.setEndTime(Schedule.getDate(date, slot));
        srvTimeDefinition.setDate(date);
        srvTimeDefinition.setProviderUserId(userId);
        srvTimeDefinition.setCreateTime(new Date());
        srvTimeDefinition.setCreateBy(userId);
        srvTimeDefinition.setUpdateBy(userId);
        srvTimeDefinition.setUpdateTime(new Date());
    }

    private ProviderSchedule buildProviderSchedule(Date date, ProviderInfo providerInfo, Integer slot) {
        ProviderSchedule providerSchedule = new ProviderSchedule();
        providerSchedule.setProviderId(providerInfo.getProviderUserId());
        providerSchedule.setDate(date);
        providerSchedule.setSlot(slot);
        providerSchedule.setCreateTime(new Date());
        providerSchedule.setCreateBy(providerInfo.getProviderUserId());
        providerSchedule.setUpdateBy(providerInfo.getProviderUserId());
        providerSchedule.setLatitude(providerInfo.getLatitude());
        providerSchedule.setLongitude(providerInfo.getLongitude());
        providerSchedule.setUpdateTime(new Date());
        return providerSchedule;
    }
}
