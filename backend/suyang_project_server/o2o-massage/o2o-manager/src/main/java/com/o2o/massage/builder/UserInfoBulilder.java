package com.o2o.massage.builder;

import com.google.common.collect.Lists;
import com.o2o.massage.dao.entity.ProviderCertificationInfo;
import com.o2o.massage.dao.entity.ProviderInfo;
import com.o2o.massage.dao.entity.UserBaseInfo;
import com.o2o.massage.model.request.ProfileUpdateRequest;
import com.o2o.massage.model.request.ProviderProfileUpdateRequest;
import org.apache.commons.lang.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * @Author: zhongli
 * @Date: 2018/3/19 21:16
 * @Description:
 */
public class UserInfoBulilder {

    public static UserBaseInfo buildUserBaseInfo(String userId, ProfileUpdateRequest updateRequest) {
        UserBaseInfo userBaseInfo = new UserBaseInfo();
        userBaseInfo.setUserId(userId);
        if (StringUtils.isNotBlank(updateRequest.getUserName())) {
            userBaseInfo.setName(updateRequest.getUserName());
        }
        if (StringUtils.isNotBlank(updateRequest.getEmail())) {
            userBaseInfo.setEmail(updateRequest.getEmail());
        }
        if (StringUtils.isNotBlank(updateRequest.getIdNum())) {
            userBaseInfo.setIdNum(updateRequest.getIdNum());
        }
        if (StringUtils.isNotBlank(updateRequest.getHeadPic())) {
            userBaseInfo.setHeadPic(updateRequest.getHeadPic());
        }
        if (updateRequest.getGender() != null) {
            userBaseInfo.setGender(updateRequest.getGender().byteValue());
        }
        if (StringUtils.isNotBlank(updateRequest.getRealName())) {
            userBaseInfo.setRealName(updateRequest.getRealName());
        }
        if (updateRequest.getBirthday() != null) {
            userBaseInfo.setBirthday(updateRequest.getBirthday());
        }

        userBaseInfo.setCreateBy(userId);
        userBaseInfo.setUpdateBy(userId);
        userBaseInfo.setCreateTime(new Date());
        userBaseInfo.setUpdateTime(new Date());
        return userBaseInfo;
    }

    public static ProviderInfo buildProviderInfo(String userId, ProviderProfileUpdateRequest request) {
        ProviderInfo providerInfo = new ProviderInfo();
        providerInfo.setProviderUserId(userId);
        if (request.getHealthReportTime() != null) {
            providerInfo.setHealthReportTime(request.getHealthReportTime());
        }
        if (StringUtils.isNotBlank(request.getHealthReport())) {
            providerInfo.setHealthReport(request.getHealthReport());
        }
        if (StringUtils.isNotBlank(request.getProviderBirthPlace())) {
            providerInfo.setProviderBirthPlace(request.getProviderBirthPlace());
        }
        if (StringUtils.isNotBlank(request.getProviderEduLev())) {
            providerInfo.setProviderEduLev(request.getProviderEduLev());
        }
        if (StringUtils.isNotBlank(request.getProviderIntroduction())) {
            providerInfo.setProviderIntroduction(request.getProviderIntroduction());
        }

        providerInfo.setCommonAddress(request.getAddress());
        providerInfo.setLongitude(request.getLongitude());
        providerInfo.setLatitude(request.getLatitude());

        providerInfo.setCreateBy(userId);
        providerInfo.setUpdateBy(userId);
        providerInfo.setCreateTime(new Date());
        providerInfo.setUpdateTime(new Date());

        return providerInfo;
    }

    public static List<ProviderCertificationInfo> buildProviderCertificationInfo(String userId, ProviderProfileUpdateRequest request) {

        String[] certs = request.getCertificateReport().split(",");
        List<ProviderCertificationInfo> result = Lists.newArrayList();
        for (String cert : certs) {
            ProviderCertificationInfo providerCertificationInfo = new ProviderCertificationInfo();
            providerCertificationInfo.setProviderId(userId);
            providerCertificationInfo.setPic(cert);
            providerCertificationInfo.setCreateBy(userId);
            providerCertificationInfo.setUpdateBy(userId);
            providerCertificationInfo.setCreateTime(new Date());
            providerCertificationInfo.setUpdateTime(new Date());
            result.add(providerCertificationInfo);
        }
        return result;
    }
}
