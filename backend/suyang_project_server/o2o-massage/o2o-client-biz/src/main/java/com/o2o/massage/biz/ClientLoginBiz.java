package com.o2o.massage.biz;

import com.o2o.massage.core.common.DeviceInfo;
import com.o2o.massage.core.constants.VerifyCodeScenario;
import com.o2o.massage.core.exception.BizException;
import com.o2o.massage.dao.entity.type.EnumUserType;
import com.o2o.massage.model.request.ForgetPasswordRequest;
import com.o2o.massage.model.request.LoginByPhoneRequest;
import com.o2o.massage.model.response.LoginResult;

/**
 * @Author: zhongli
 * @Date: 2018/2/6 17:39
 * @Description:
 */
public interface ClientLoginBiz {
    /**
     * 获取登录验证码
     * @param phone
     * @throws BizException
     */
    public void registerCode(String phone) throws BizException;

    /**
     * 快速注册
     * @param request
     * @param deviceInfo
     * @return
     * @throws BizException
     */
    //RegisterResult fastRegister(RegisterRequest request, DeviceInfo deviceInfo) throws BizException;

    /**
     * 登录
     * @param loginRequest
     * @param userType
     * @param deviceInfo
     * @return
     * @throws BizException
     */
    LoginResult login(LoginByPhoneRequest loginRequest, EnumUserType userType, DeviceInfo deviceInfo) throws BizException;

    /**
     * 退出登录
     * @param uid
     * @param deviceInfo
     * @throws BizException
     */
    void logout(String uid, DeviceInfo deviceInfo) throws BizException;

    /**
     * 忘记密码
     * @param request
     * @param deviceInfo
     * @throws BizException
     */
    void forgetPassword(ForgetPasswordRequest request, DeviceInfo deviceInfo) throws BizException;

    /**
     * 获取验证码
     * @param phone
     * @param scenario
     * @throws BizException
     */
    void getCommonVerifyCode(String phone, VerifyCodeScenario scenario) throws BizException;
}
