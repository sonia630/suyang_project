package com.o2o.massage.biz.impl;

import com.o2o.massage.ModelTransHelper;
import com.o2o.massage.biz.ClientLoginBiz;
import com.o2o.massage.core.common.DeviceInfo;
import com.o2o.massage.core.constants.VerifyCodeScenario;
import com.o2o.massage.core.exception.BizException;
import com.o2o.massage.core.utils.BusinessHelper;
import com.o2o.massage.core.utils.DateUtils;
import com.o2o.massage.core.utils.RandomUUID;
import com.o2o.massage.core.utils.SnowFlake;
import com.o2o.massage.dao.component.CustomerDao;
import com.o2o.massage.dao.component.LoginTokenDao;
import com.o2o.massage.dao.component.ProviderDao;
import com.o2o.massage.dao.component.UserBaseDao;
import com.o2o.massage.dao.entity.*;
import com.o2o.massage.dao.entity.type.EnumUserType;
import com.o2o.massage.login.VerifyCodeManager;
import com.o2o.massage.model.request.ForgetPasswordRequest;
import com.o2o.massage.model.request.LoginByPhoneRequest;
import com.o2o.massage.model.response.LoginResult;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Author: zhongli
 * @Date: 2018/2/23 11:53
 * @Description:
 */
@Service
public class ClientLoginBizImpl implements ClientLoginBiz {

    @Autowired
    private UserBaseDao userDao;

    @Autowired
    private VerifyCodeManager verifyCodeManager;

    @Autowired
    private LoginTokenDao appTokenDao;

    @Resource
    private CustomerDao customerDao;

    @Resource
    private ProviderDao providerDao;

    @Override
    public void registerCode(String phone) throws BizException {
        verifyCodeManager.registerCode(phone);
    }

    /*@Transactional
    public RegisterResult fastRegister(RegisterRequest request, DeviceInfo deviceInfo) throws BizException {
        String phone = request.getPhone();
        String verifyCode = request.getVerifyCode();
        String userName = request.getUserName();
        String password = request.getPassword();

        String imei = deviceInfo.getDeviceId();
        ;

        Validate.isTrue(StringUtils.isNotBlank(phone), "手机号不得为空");

        Validate.isTrue(BusinessHelper.isPhoneValid(phone), "请输入正确的手机号");

        Validate.isTrue(StringUtils.isNotBlank(userName), "用户名不得为空");

        Validate.isTrue(StringUtils.isNotBlank(password), "密码不得为空");

        UserBaseInfo UserBaseInfo = userDao.getUserInfoByPhone(phone);
        if (UserBaseInfo != null) {
            throw new BizException("该手机号已成功注册，请直接登录");
        }

        VerifyCodeInfo verifyCodeInfo = verifyCodeManager.getCodeByPhone(phone, VerifyCodeScenario.REGISTER_LOGIN);
        if (verifyCodeInfo == null) {
            throw new BizException("数据信息异常,请检查手机号码输入是否正确！");
        }

        if (!StringUtils.equals(verifyCode, String.valueOf(verifyCodeInfo.getVerifyCode()))) {
            throw new BizException("请输入正确的验证码");
        }
        verifyCodeManager.updateStateById(verifyCodeInfo.getId());

        SnowFlake sf = new SnowFlake(0, 0);

        UserBaseInfo user = new UserBaseInfo();
        user.setUserId(String.valueOf(sf.nextId()));
        user.setName(userName);
        user.setPhone(phone);
        user.setUserType(EnumUserType.CUSTOMER.getCode().byteValue());
        user.setPassword(password);
        userDao.addOneUser(user);

        RegisterResult registerResult = new RegisterResult();
        registerResult.setUserInfo(ModelTransHelper.transfer(user));

        String token = RandomUUID.shorterUUID();
        appTokenDao.addLoginTokenInfo(user.getUserId(), imei,
                token, new Date(), DateUtils.getDateAfter(new Date(), 30));
        registerResult.setToken(token);

        return registerResult;
    }*/

    @Transactional
    @Override
    public LoginResult login(LoginByPhoneRequest loginRequest, EnumUserType userType, DeviceInfo deviceInfo) throws BizException {
        String imei = deviceInfo.getDeviceId();

        Validate.isTrue(StringUtils.isNotBlank(loginRequest.getPhone()), "手机号不得为空");

        Validate.isTrue(StringUtils.isNotBlank(loginRequest.getVerifyCode()), "短信验证码不得为空");

//        VerifyCodeInfo verifyCodeInfo = verifyCodeManager.getCodeByPhone(loginRequest.getPhone(), VerifyCodeScenario.REGISTER_LOGIN);
//        if (verifyCodeInfo == null) {
//            throw new BizException("数据信息异常,请检查手机号码输入是否正确！");
//        }
//
//        if (!StringUtils.equals(loginRequest.getVerifyCode(), String.valueOf(verifyCodeInfo.getVerifyCode()))) {
//            throw new BizException("请输入正确的短信验证码");
//        }
//        verifyCodeManager.updateStateById(verifyCodeInfo.getId());

        UserBaseInfo userBaseInfo = userDao.getUserInfoByPhone(loginRequest.getPhone());
        if (userBaseInfo == null) {
            SnowFlake sf = new SnowFlake(0, 0);

            UserBaseInfo user = new UserBaseInfo();
            user.setUserId(String.valueOf(sf.nextId()));
            user.setName(RandomUUID.shorterUUID());
            user.setPhone(loginRequest.getPhone());
            user.setUserType(userType.getCode().byteValue());
            user.setPassword(RandomUUID.shorterUUID());
            user.setCreateTime(new Date());
            user.setLoginDate(new Date());
            userDao.addOneUser(user);
            userBaseInfo = user;
        } else {
            //更新用户类型
            int finalUserType = (userBaseInfo.getUserType() | userType.getCode());
            UserBaseInfo baseUpdate = new UserBaseInfo();
            baseUpdate.setUserId(userBaseInfo.getUserId());
            baseUpdate.setLoginDate(new Date());

            if (finalUserType != userBaseInfo.getUserType()) {
                baseUpdate.setUserType((byte) finalUserType);
            }
            userDao.updateOneUser(baseUpdate);
        }

        switch (userType) {
            case PROVIDER:
                doRegisterProvider(userBaseInfo);
                break;
            case CUSTOMER:
                doRegisterCustomer(userBaseInfo);
                break;
        }

        String token = "";
        LoginToken appTokenInfo = appTokenDao.getLoginToken(userBaseInfo.getUserId(), imei);
        if (appTokenInfo == null || appTokenInfo.getExpireTime().before(new Date())) {
            token = RandomUUID.shorterUUID();
            appTokenDao.addLoginTokenInfo(userBaseInfo.getUserId(), imei,
                    token, new Date(), DateUtils.getDateAfter(new Date(), 30));
        } else {
            token = appTokenInfo.getLoginToken();
            appTokenDao.updateExpireTime(appTokenInfo.getId(), DateUtils.getDateAfter(new Date(), 30));
        }

        LoginResult loginResult = new LoginResult();
        loginResult.setUserInfo(ModelTransHelper.transfer(userBaseInfo));

        loginResult.setToken(token);

        return loginResult;
    }

    private boolean doRegisterCustomer(UserBaseInfo user) {
        boolean userInfoComplete = false;
        CustomerInfo customerInfo = customerDao.findOneByUserId(user.getUserId());
        if (customerInfo == null) {
            CustomerInfo customerInfoMem = new CustomerInfo();
            customerInfoMem.setCustomerUserId(user.getUserId());
            customerDao.insertCustomer(customerInfoMem);
        }
        return userInfoComplete;
    }

    private boolean doRegisterProvider(UserBaseInfo user) {
        boolean userInfoComplete = false;
        ProviderInfo providerInfo = providerDao.findOneByUserId(user.getUserId());
        if (providerInfo == null) {
            ProviderInfo providerInfoMem = new ProviderInfo();
            providerInfoMem.setProviderUserId(user.getUserId());
            providerDao.insertProvider(providerInfoMem);
        }
        return userInfoComplete;
    }

    public void logout(String uid, DeviceInfo deviceInfo) throws BizException {
        String imei = deviceInfo.getDeviceId();
        UserBaseInfo user = userDao.getBaseUser(uid);
        if (user == null) {
            throw new BizException("对应用户不存在");
        }
        appTokenDao.deleteAppTokenInfo(uid, imei);
    }


    public void getCommonVerifyCode(String phone, VerifyCodeScenario scenario) throws BizException {
        verifyCodeManager.getCommonVerifyCode(phone, scenario);
    }

    public void forgetPassword(ForgetPasswordRequest request, DeviceInfo deviceInfo) throws BizException {
        String phone = request.getPhone();
        String verifyCode = request.getVerifyCode();
        String password = request.getPassword();

        String imei = deviceInfo.getDeviceId();
        ;

        Validate.isTrue(StringUtils.isNotBlank(phone), "手机号不得为空");

        Validate.isTrue(BusinessHelper.isPhoneValid(phone), "请输入正确的手机号");

        Validate.isTrue(StringUtils.isNotBlank(password), "密码不得为空");

        UserBaseInfo userBaseInfo = userDao.getUserInfoByPhone(phone);
        if (userBaseInfo == null) {
            throw new BizException("该手机号在系统中不存在，请重新输入");
        }

        VerifyCodeInfo verifyCodeInfo = verifyCodeManager.getCodeByPhone(phone, VerifyCodeScenario.FORGET_PASSWORD);
        if (verifyCodeInfo == null) {
            throw new BizException("数据信息异常,请检查手机号码输入是否正确！");
        }

        if (!StringUtils.equals(verifyCode, String.valueOf(verifyCodeInfo.getVerifyCode()))) {
            throw new BizException("请输入正确的验证码");
        }
        verifyCodeManager.updateStateById(verifyCodeInfo.getId());

        userDao.updatePassword(userBaseInfo.getUserId(), request.getPassword());
    }
}
