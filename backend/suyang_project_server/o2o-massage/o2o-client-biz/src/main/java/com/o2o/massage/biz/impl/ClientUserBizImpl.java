package com.o2o.massage.biz.impl;

import com.google.common.base.Preconditions;
import com.o2o.massage.ModelTransHelper;
import com.o2o.massage.biz.ClientUserBiz;
import com.o2o.massage.builder.UserInfoBulilder;
import com.o2o.massage.core.constants.VerifyCodeScenario;
import com.o2o.massage.core.exception.BizException;
import com.o2o.massage.core.utils.BusinessHelper;
import com.o2o.massage.core.utils.SnowFlake;
import com.o2o.massage.dao.component.CustomerDao;
import com.o2o.massage.dao.component.ProviderDao;
import com.o2o.massage.dao.component.UserBaseDao;
import com.o2o.massage.dao.entity.*;
import com.o2o.massage.login.VerifyCodeManager;
import com.o2o.massage.model.CustomerInfoVO;
import com.o2o.massage.model.ProviderInfoVO;
import com.o2o.massage.model.ProviderSimpleInfoVO;
import com.o2o.massage.model.UserBaseInfoVO;
import com.o2o.massage.model.request.CustomerProfileUpdateRequest;
import com.o2o.massage.model.request.ProviderProfileUpdateRequest;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Author: zhongli
 * @Date: 2018/2/6 18:43
 * @Description:
 */
@Service
public class ClientUserBizImpl implements ClientUserBiz {

    @Resource
    private UserBaseDao clientUserDao;

    @Resource
    private VerifyCodeManager verifyCodeManager;

    @Resource
    private CustomerDao customerDao;

    @Resource
    private ProviderDao providerDao;

    @Override
    public UserBaseInfoVO getBaseUserInfo(String userId) {
        UserBaseInfo baseUser = clientUserDao.getBaseUser(userId);
        if (baseUser == null) {
            return null;
        }
        return ModelTransHelper.transfer(baseUser);
    }

    @Override
    public void changeHeadUrl(String uid, String headUrl) throws BizException {
        Validate.isTrue(StringUtils.isNotBlank(headUrl), "头像不得为空");
        UserBaseInfo user = clientUserDao.getBaseUser(uid);
        if (user == null) {
            throw new BizException("未发现对应用户");
        }
        clientUserDao.updateHeadUrl(uid, headUrl);
    }

    @Override
    public void changeUserTel(String uid, String tel, String verifyCode) throws BizException {
        Validate.isTrue(StringUtils.isNotBlank(tel), "手机号不得为空");
        Validate.isTrue(BusinessHelper.isPhoneValid(tel), "不合法的手机号码");
        UserBaseInfo user = clientUserDao.getBaseUser(uid);
        if (user == null) {
            throw new BizException("未发现对应用户");
        }
        if (StringUtils.equals(tel, user.getPhone())) {
            throw new BizException("新旧手机号不得相同");
        }
        UserBaseInfo userExist = clientUserDao.getUserInfoByPhone(tel);
        if (userExist != null && !StringUtils.equals(userExist.getUserId(), uid)) {
            throw new BizException("该手机号已经绑定其他用户，请重新输入");
        }
        VerifyCodeInfo verifyCodeInfo = verifyCodeManager.getCodeByPhone(tel, VerifyCodeScenario.CHANGE_TELEPHONE);
        if (verifyCodeInfo == null) {
            throw new BizException("数据信息异常,请检查手机号码输入是否正确！");
        }

        if (!StringUtils.equals(verifyCode, String.valueOf(verifyCodeInfo.getVerifyCode()))) {
            throw new BizException("请输入正确的验证码");
        }
        verifyCodeManager.updateStateById(verifyCodeInfo.getId());
        clientUserDao.updateTel(uid, tel);
    }

    @Override
    public ProviderInfoVO providerSelfProfile(String userId) {
        UserBaseInfo baseUser = clientUserDao.getBaseUser(userId);
        ProviderInfo providerInfo = providerDao.findOneByUserId(userId);
        ProviderInfoVO providerInfoVO = new ProviderInfoVO();
        BeanUtils.copyProperties(baseUser, providerInfoVO);
        BeanUtils.copyProperties(providerInfo, providerInfoVO);
        return providerInfoVO;
    }

    @Override
    public ProviderSimpleInfoVO providerOtherProfile(String userId) {
        UserBaseInfo baseUser = clientUserDao.getBaseUser(userId);
        ProviderInfo providerInfo = providerDao.findOneByUserId(userId);
        if (baseUser == null || providerInfo == null) {
            return null;
        }
        ProviderSimpleInfoVO simpleInfoVO = new ProviderSimpleInfoVO();
        BeanUtils.copyProperties(baseUser, simpleInfoVO);
        BeanUtils.copyProperties(providerInfo, simpleInfoVO);
        return simpleInfoVO;
    }

    @Override
    public CustomerInfoVO customerSelfProfile(String userId) {
        UserBaseInfo baseUser = clientUserDao.getBaseUser(userId);
        CustomerInfo customerInfo = customerDao.findOneByUserId(userId);
        CustomerAddress address = customerDao.getUserDefaultLocation(userId);
        if (baseUser == null || customerInfo == null) {
            return null;
        }

        CustomerInfoVO customerInfoVO = new CustomerInfoVO();
        BeanUtils.copyProperties(customerInfo, customerInfoVO);
        BeanUtils.copyProperties(baseUser, customerInfoVO);
        if (address != null) {
            customerInfoVO.setDefaultAddress(address.getAddress());
        }
        return customerInfoVO;
    }

    @Override
    public void completeCustomerInfo(String userId, CustomerProfileUpdateRequest updateRequest) {
        Preconditions.checkArgument(StringUtils.isNotBlank(updateRequest.getUserName())
                , "用户昵称不得为空");
        Preconditions.checkArgument(updateRequest.getBirthday() != null
                , "出生年月不得为空");
        Preconditions.checkArgument(updateRequest.getGender() != null
                , "性别不得为空");
        if (updateRequest.getSource() != null) {
            CustomerInfo customerInfo = new CustomerInfo();
            customerInfo.setCustomerUserId(userId);
            customerInfo.setCustomerSource(updateRequest.getSource());
            customerDao.updateCustomer(customerInfo);
        }
        UserBaseInfo userBaseInfo = UserInfoBulilder.buildUserBaseInfo(userId, updateRequest);
        clientUserDao.updateOneUser(userBaseInfo);
    }

    @Override
    public void completeProviderInfo(String userId, ProviderProfileUpdateRequest updateRequest) {
//        Preconditions.checkArgument(StringUtils.isNotBlank(updateRequest.getUserName())
//                , "用户昵称不得为空");
        Preconditions.checkArgument(updateRequest.getBirthday() != null
                , "出生年月不得为空");
        Preconditions.checkArgument(StringUtils.isNotBlank(updateRequest.getAddress())
                , "地址不得为空");
        Preconditions.checkArgument(updateRequest.getGender() != null
                , "性别不得为空");
        Preconditions.checkArgument(StringUtils.isNotBlank(updateRequest.getIdNum())
                , "身份证不得为空");
        Preconditions.checkArgument(StringUtils.isNotBlank(updateRequest.getRealName())
                , "姓名不得为空");

        UserBaseInfo userBaseInfo = UserInfoBulilder.buildUserBaseInfo(userId, updateRequest);
        clientUserDao.updateOneUser(userBaseInfo);

        ProviderInfo providerInfo = UserInfoBulilder.buildProviderInfo(userId, updateRequest);
        providerDao.updateProvider(providerInfo);

        if (StringUtils.isNotBlank(updateRequest.getCertificateReport())) {
            for (ProviderCertificationInfo providerCertificationInfo : UserInfoBulilder.buildProviderCertificationInfo(userId, updateRequest)) {
                providerDao.insertCertificateReport(providerCertificationInfo);
            }
        }
    }

    @Override
    public List<CustomerAddress> getUserLocations(String userId) {
        return customerDao.getUserLocations(userId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveUserDefaultLocation(CustomerAddress customerAddress, String userId) throws Exception {
        CustomerAddress address = new CustomerAddress();
        address.setDefaultAddress((byte) 0);
        customerDao.updateUserLocation(userId, address);

        customerAddress.setCreateBy(userId);
        customerAddress.setUpdateBy(userId);
        customerAddress.setCreateTime(new Date());
        customerAddress.setUserid(userId);
        customerAddress.setUpdateTime(new Date());

        if (StringUtils.isBlank(customerAddress.getId())) {
            if (customerDao.containAddress(customerAddress)) {
                throw new Exception("该地址已存在");
            }
            SnowFlake sf = new SnowFlake(0, 0);
            customerAddress.setId(String.valueOf(sf.nextId()));
            customerDao.saveUserLocation(customerAddress);
        } else {
            address = new CustomerAddress();
            address.setId(customerAddress.getId());
            address.setDefaultAddress(customerAddress.getDefaultAddress());
            customerDao.updateUserLocation(address);
        }
    }

    @Override
    public void updateUserRealName(String userId, String name) {
        clientUserDao.updateRealName(userId, name);
    }

    @Override
    public void updateSex(String userId, int sex) {
        clientUserDao.updateSex(userId, (byte) sex);
    }

    @Override
    public void updateBirthday(String userId, Date date) {
        clientUserDao.updateBirthday(userId, date);
    }

    @Override
    public List<FamilyMember> getFamilyMembers(String userId) {
        return customerDao.getFamilyMembers(userId);
    }

    @Override
    public void saveMember(FamilyMember familyMember, String userId) {
        familyMember.setCreateBy(userId);
        familyMember.setUpdateBy(userId);
        familyMember.setUpdateTime(new Date());
        familyMember.setCreateTime(new Date());

        SnowFlake sf = new SnowFlake(0, 0);
        familyMember.setMemberId(String.valueOf(sf.nextId()));
        familyMember.setCustomerUserId(userId);
        customerDao.saveMember(familyMember);
    }

    @Override
    public FamilyMember getFamilyMember(String memberId) {
        return customerDao.getFamilyMember(memberId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveSympton(MemberSympton memberSympton, String userId) {
        if (StringUtils.isBlank(memberSympton.getDesc()) && StringUtils.isBlank(memberSympton.getUpload())) {
            return;
        }
        memberSympton.setCreateBy(userId);
        memberSympton.setUpdateBy(userId);
        memberSympton.setUpdateTime(new Date());
        memberSympton.setCreateTime(new Date());
        memberSympton.setCustomerUserId(userId);
        customerDao.saveSympton(memberSympton);
    }

    @Override
    public void updateHeadPic(String fromUid, String url) {
        clientUserDao.updateHeadUrl(fromUid, url);
    }

    @Override
    public void updateMemberSympton(String orderNo, String userId, String memberId) {
        customerDao.updateMemberSympton(orderNo, userId, memberId);
    }

    /*public void changePassword(String userId, String originPass, String newPass) throws BizException {
        Validate.isTrue(StringUtils.isNotBlank(originPass), "旧密码不得为空");
        Validate.isTrue(StringUtils.isNotBlank(originPass), "新密码不得为空");
        Validate.isTrue(!StringUtils.equals(originPass, newPass), "新旧密码不得相同");

        UserBaseInfo user = clientUserDao.getBaseUser(userId);
        if (user == null) {
            throw new BizException("未发现对应用户");
        }
        if (!StringUtils.equals(originPass, user.getPassword())) {
            throw new BizException("旧密码输入不正确，请重新输入");
        }

        clientUserDao.updatePassword(userId, newPass);
    }*/
}
