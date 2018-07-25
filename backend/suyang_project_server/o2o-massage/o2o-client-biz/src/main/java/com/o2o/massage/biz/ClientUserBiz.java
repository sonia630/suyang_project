package com.o2o.massage.biz;

import com.o2o.massage.core.exception.BizException;
import com.o2o.massage.dao.entity.CustomerAddress;
import com.o2o.massage.dao.entity.FamilyMember;
import com.o2o.massage.dao.entity.MemberSympton;
import com.o2o.massage.model.CustomerInfoVO;
import com.o2o.massage.model.ProviderInfoVO;
import com.o2o.massage.model.ProviderSimpleInfoVO;
import com.o2o.massage.model.UserBaseInfoVO;
import com.o2o.massage.model.request.CustomerProfileUpdateRequest;
import com.o2o.massage.model.request.ProviderProfileUpdateRequest;

import java.util.Date;
import java.util.List;

/**
 * @Author: zhongli
 * @Date: 2018/2/6 17:36
 * @Description:
 */
public interface ClientUserBiz {

    /**
     * 获取用户基本信息
     */
    UserBaseInfoVO getBaseUserInfo(String userId);

    void changeHeadUrl(String uid, String headUrl) throws BizException;


    void changeUserTel(String uid, String tel, String verifyCode) throws BizException;

    ProviderInfoVO providerSelfProfile(String userId);

    ProviderSimpleInfoVO providerOtherProfile(String userId);

    CustomerInfoVO customerSelfProfile(String userId);

    void completeCustomerInfo(String userId, CustomerProfileUpdateRequest updateRequest);

    void completeProviderInfo(String userId, ProviderProfileUpdateRequest updateRequest);

    List<CustomerAddress> getUserLocations(String userId);

    void saveUserDefaultLocation(CustomerAddress customerAddress, String userId) throws Exception;

    void updateUserRealName(String userId, String name);

    void updateSex(String userId, int sex);

    void updateBirthday(String fromUid, Date date);

    List<FamilyMember> getFamilyMembers(String userId);

    void saveMember(FamilyMember familyMember, String userId);

    FamilyMember getFamilyMember(String memberId);

    void saveSympton(MemberSympton memberSympton, String userId);

    void updateHeadPic(String fromUid, String url);

    void updateMemberSympton(String orderNo, String userId, String memberId);
}
