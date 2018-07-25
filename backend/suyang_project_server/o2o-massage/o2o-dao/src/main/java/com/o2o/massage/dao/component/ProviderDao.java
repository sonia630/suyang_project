package com.o2o.massage.dao.component;

import com.o2o.massage.dao.entity.*;
import com.o2o.massage.dao.mapper.*;
import com.o2o.nm.bo.*;
import com.o2o.nm.entity.ServiceCategory;
import com.o2o.nm.mapper.EvaluationInfoOwnerMapper;
import com.o2o.nm.mapper.ProviderOwnerMapper;
import com.o2o.nm.mapper.ProviderScheduleOwnerMapper;
import com.o2o.nm.vo.ProviderList;
import com.o2o.nm.vo.ProviderScheduleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: zhongli
 * @Date: 2018/2/25 21:22
 * @Description:
 */
@Component
public class ProviderDao {

    @Autowired
    private ProviderInfoMapper providerInfoMapper;
    @Autowired
    private ProviderScheduleOwnerMapper providerScheduleOwnerMapper;
    @Autowired
    private ProviderScheduleMapper providerScheduleMapper;
    @Autowired
    private ProviderServiceStaticMapper providerServiceStaticMapper;
    @Autowired
    private EvaluationInfoMapper evaluationInfoMapper;
    @Autowired
    private EvaluationInfoOwnerMapper evaluationInfoOwnerMapper;
    @Autowired
    private ProviderOwnerMapper providerOwnerMapper;
    @Autowired
    private ProviderCertificationInfoMapper providerCertificationInfoMapper;
    @Autowired
    private SrvProviderSrvRelMapper srvProviderSrvRelMapper;

    public int insertProvider(ProviderInfo info) {
        return providerInfoMapper.insertSelective(info);
    }

    public void insertCertificateReport(ProviderCertificationInfo providerCertificationInfo) {
        providerCertificationInfoMapper.insertSelective(providerCertificationInfo);
    }

    public ProviderInfo findOneByUserId(String userId) {
        ProviderInfoExample example = new ProviderInfoExample();
        example.createCriteria().andProviderUserIdEqualTo(userId);
        List<ProviderInfo> infoList = providerInfoMapper.selectByExample(example);
        if (infoList != null && infoList.size() > 0) {
            return infoList.get(0);
        }
        return null;
    }

    public int updateProvider(ProviderInfo info) {
        ProviderInfoExample providerInfoExample = new ProviderInfoExample();
        providerInfoExample.createCriteria().andProviderUserIdEqualTo(info.getProviderUserId());
        return providerInfoMapper.updateByExampleSelective(info, providerInfoExample);
    }

    public List<ProviderSchedule> getProviderSchedules(ProviderScheduleVo providerScheduleVo) {
        return providerScheduleOwnerMapper.getProviderSchedules(providerScheduleVo);
    }

    public void updateProviderSchedule(List<ProviderSchedule> list) {
        list.forEach(providerSchedule -> {
            ProviderScheduleExample example = new ProviderScheduleExample();
            example.createCriteria().andProviderIdEqualTo(providerSchedule.getProviderId())
                    .andDateEqualTo(providerSchedule.getDate())
                    .andSlotEqualTo(providerSchedule.getSlot());
            providerSchedule.setDate(null);
            providerSchedule.setSlot(null);
            providerSchedule.setProviderId(null);
            providerScheduleMapper.updateByExampleSelective(providerSchedule, example);
        });
    }

    public long getServiceCount(String providerUserId) {
        ProviderServiceStaticExample example = new ProviderServiceStaticExample();
        example.createCriteria().andProviderUserIdEqualTo(providerUserId);
        List<ProviderServiceStatic> list = providerServiceStaticMapper.selectByExample(example);
        if (list != null && list.size() > 0) {
            return list.get(0).getServiceTimes();
        }
        return 0;

    }

    public void saveEvaluation(EvaluationInfo evaluationInfo) {
        evaluationInfoMapper.insertSelective(evaluationInfo);
    }

    public void updateCommonAddress(String userId, String address, BigDecimal longitude, BigDecimal latitude) {
        ProviderInfo providerInfo = new ProviderInfo();
        providerInfo.setCommonAddress(address);
        providerInfo.setLongitude(longitude);
        providerInfo.setLatitude(latitude);
        ProviderInfoExample example = new ProviderInfoExample();
        example.createCriteria().andProviderUserIdEqualTo(userId);
        providerInfoMapper.updateByExampleSelective(providerInfo, example);
    }

    public List<ProviderBo> getProvidersOrderbyEval(ProviderList providerVoList) {
        return providerOwnerMapper.getProvidersOrderbyEval(providerVoList);
    }

    public ProviderValidateBo getProviderValidateInfo(String userId) {
        return providerOwnerMapper.getProviderValidateInfo(userId);
    }

    public void cancelProviderSchedule(String userId, String orderNo) {
        ProviderSchedule providerSchedule = new ProviderSchedule();
        providerSchedule.setUpdateBy(userId);
        providerSchedule.setUpdateTime(new Date());
        providerSchedule.setOrderNo(orderNo);
        providerScheduleOwnerMapper.cancelProviderSchedule(providerSchedule);
    }

    public void updateServiceStatic(String providerUserId, String serviceId) {
        ProviderServiceStaticExample example = new ProviderServiceStaticExample();
        example.createCriteria().andProviderUserIdEqualTo(providerUserId).andServiceIdEqualTo(serviceId);
        List<ProviderServiceStatic> values = providerServiceStaticMapper.selectByExample(example);
        if (values != null && values.size() > 0) {
            ProviderServiceStatic providerServiceStatic = values.get(0);
            providerServiceStatic.setServiceTimes(providerServiceStatic.getServiceTimes() + 1);
            providerServiceStaticMapper.updateByExample(providerServiceStatic, example);
        } else {
            ProviderServiceStatic providerServiceStatic = new ProviderServiceStatic();
            providerServiceStatic.setServiceTimes(1);
            providerServiceStatic.setProviderUserId(providerUserId);
            providerServiceStatic.setServiceId(serviceId);
            providerServiceStaticMapper.insert(providerServiceStatic);
        }
    }

    public void insertProviderSchedules(List<ProviderSchedule> providerSchedules) {
        List<Date> dates = providerSchedules.stream().map(providerSchedule -> providerSchedule.getDate()).collect(Collectors.toList());
        providerScheduleOwnerMapper.deleteProviderScheduleByDates(providerSchedules.get(0).getProviderId(), dates);
        providerSchedules.forEach(providerScheduleMapper::insertSelective);
    }

    public List<ProviderSchedule> getProviderSchedulesByUserId(String providerUserId, Date start) {
        return providerScheduleOwnerMapper.getProviderSchedulesByUserId(providerUserId, start);
    }

    public int getProvidersOrderbyEvalCount(ProviderList providerList) {
        return providerOwnerMapper.getProvidersOrderbyEvalCount(providerList);
    }

    public ProviderDetail getProviderInfoDetail(String providerId) {
        return providerOwnerMapper.getProviderDetail(providerId);
    }

    public List<ServiceCategory> getProviderServiceCat(String providerId) {
        return providerOwnerMapper.getProviderServiceCat(providerId);
    }

    public List<ProviderServiceInfo> getProviderServiceCatInfo(String catId, String providerId) {
        return providerOwnerMapper.getProviderServiceCatInfo(catId, providerId);
    }

    public ProviderEvalStatic getProviderEvalStatic(String providerId) {
        return providerOwnerMapper.getProviderEvalStatic(providerId);
    }

    public Integer getProviderServiceTimes(String providerId) {
        return providerOwnerMapper.getProviderServiceTimes(providerId);
    }

    public List<ProviderEval> getEvalList(String providerId, int start, int size) {
        return evaluationInfoOwnerMapper.getEvalList(providerId, start, size);
    }

    public SrvProviderSrvRel getProviderAndSrv(String providerId, String serviceId) {
        SrvProviderSrvRelExample srvProviderSrvRelExample = new SrvProviderSrvRelExample();
        srvProviderSrvRelExample.createCriteria().andServiceIdEqualTo(serviceId)
                .andProviderUserIdEqualTo(providerId);
        List<SrvProviderSrvRel> srvs = srvProviderSrvRelMapper.selectByExample(srvProviderSrvRelExample);
        if (srvs != null && srvs.size() == 1) {
            return srvs.get(0);
        }
        return new SrvProviderSrvRel();
    }

    public void saveIntroduction(String providerId, String introduction) {
        ProviderInfoExample providerInfoExample = new ProviderInfoExample();
        providerInfoExample.createCriteria().andProviderUserIdEqualTo(providerId);
        ProviderInfo providerInfo = new ProviderInfo();
        providerInfo.setProviderIntroduction(introduction);
        providerInfoMapper.updateByExampleSelective(providerInfo, providerInfoExample);

    }

    public String getIntroduction(String providerId) {
        ProviderInfoExample providerInfoExample = new ProviderInfoExample();
        providerInfoExample.createCriteria().andProviderUserIdEqualTo(providerId);
        List<ProviderInfo> infos = providerInfoMapper.selectByExample(providerInfoExample);
        if (infos != null && infos.size() == 1) {
            return infos.get(0).getProviderIntroduction();
        }
        return "";

    }

    public List<ProviderCertificationInfo> getProviderCerts(String providerId) {
        ProviderCertificationInfoExample providerCertificationInfoExample = new ProviderCertificationInfoExample();
        providerCertificationInfoExample.createCriteria().andProviderIdEqualTo(providerId)
                .andStatusEqualTo((byte) 1);
        return providerCertificationInfoMapper.selectByExample(providerCertificationInfoExample);
    }

    public void saveCertification(ProviderCertificationInfo providerCertificationInfo) {
        providerCertificationInfoMapper.insertSelective(providerCertificationInfo);
    }

    public EvalAllInfo getEvalAllInfo(String providerId) {
        return evaluationInfoOwnerMapper.getEvalAllInfo(providerId);
    }

    public List<ProviderEval> getEvalList(EvalBo evalBo) {
        return evaluationInfoOwnerMapper.getEvalListByScoreRange(evalBo);
    }

    public int getEvalListCount(EvalBo evalBo) {
        return evaluationInfoOwnerMapper.getEvalListByScoreRangeCount(evalBo);
    }
}
