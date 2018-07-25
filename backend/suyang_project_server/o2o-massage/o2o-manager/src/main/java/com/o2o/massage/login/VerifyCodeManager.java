package com.o2o.massage.login;

import com.o2o.massage.core.constants.VerifyCodeScenario;
import com.o2o.massage.core.exception.BizException;
import com.o2o.massage.core.utils.BusinessHelper;
import com.o2o.massage.dao.component.UserBaseDao;
import com.o2o.massage.dao.component.VerifyCodeDao;
import com.o2o.massage.dao.entity.UserBaseInfo;
import com.o2o.massage.dao.entity.VerifyCodeInfo;
import com.o2o.massage.sms.SmsSendService;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @Author: zhongli
 * @Date: 2018/2/23 15:11
 * @Description:
 */
@Component
public class VerifyCodeManager {
    private final static Logger logger = LoggerFactory.getLogger(VerifyCodeManager.class);
    @Autowired
    private UserBaseDao userDao;

    @Autowired
    private VerifyCodeDao verifyCodeDao;

    @Resource
    private SmsSendService smsSendService;

    /**
     * 获取注册验证码
     *
     * @param phone
     * @throws BizException
     */
    @Transactional
    public void registerCode(String phone) throws BizException {

        Validate.isTrue(StringUtils.isNotBlank(phone), "手机号不得为空");

        Validate.isTrue(BusinessHelper.isPhoneValid(phone), "请输入正确的手机号");

        UserBaseInfo parkinsonUser = userDao.getBaseUser(phone);
        if (parkinsonUser != null) {
            throw new BizException("该手机号已成功注册，请直接登录");
        }

        VerifyCodeInfo verifyCodeInfo = verifyCodeDao.getCodeByPhone(phone, VerifyCodeScenario.REGISTER_LOGIN);
        if (verifyCodeInfo != null) {
            long timeInterval = System.currentTimeMillis() - verifyCodeInfo.getCreateTime().getTime();
            if (timeInterval <= 60 * 1000) {
                throw new BizException("一分钟内，只能发送一条短信");
            }
        }

        int code = (int) ((Math.random() * 9 + 1) * 1000);
        verifyCodeDao.addOneCodeInfo( phone, code,VerifyCodeScenario.REGISTER_LOGIN);
        int returnValue = smsSendService.sendVerifyCode(phone,String.valueOf(code));

        logger.info("register verify code,phone:{},result:{}", phone, returnValue);
        if (returnValue!= 0) {
            throw new BizException("发送验证码异常!");
        }
    }

    /**
     * 获取验证码
     *
     * @param phone
     * @throws BizException
     */
    @Transactional
    public void getCommonVerifyCode(String phone, VerifyCodeScenario scenario) throws BizException {

        Validate.isTrue(StringUtils.isNotBlank(phone), "手机号不得为空");

        Validate.isTrue(BusinessHelper.isPhoneValid(phone), "请输入正确的手机号");

        VerifyCodeInfo verifyCodeInfo = verifyCodeDao.getCodeByPhone(phone,scenario);
        if (verifyCodeInfo != null) {
            long timeInterval = System.currentTimeMillis() - verifyCodeInfo.getCreateTime().getTime();
            if (timeInterval <= 60 * 1000) {
                throw new BizException("一分钟内，只能发送一条短信");
            }
        }
        UserBaseInfo parkinsonUser = userDao.getUserInfoByPhone(phone);
        if (VerifyCodeScenario.CHANGE_TELEPHONE == scenario) {
            if (parkinsonUser != null) {
                throw new BizException("该手机号已经绑定其他用户，请重新输入");
            }
        } else if (VerifyCodeScenario.FORGET_PASSWORD == scenario) {
            if (parkinsonUser == null) {
                throw new BizException("该手机号在系统中不存在，请重新输入");
            }
        }

        int code = (int) ((Math.random() * 9 + 1) * 1000);
        verifyCodeDao.addOneCodeInfo( phone, code,VerifyCodeScenario.REGISTER_LOGIN);
        int returnValue = smsSendService.sendVerifyCode(phone,String.valueOf(code));

        logger.info("register verify code,phone:{},result:{}", phone, returnValue);
        if (returnValue!= 0) {
            throw new BizException("发送验证码异常!");
        }
    }

    public int addOneCodeInfo(String phone, int code,VerifyCodeScenario scenario) {
        return verifyCodeDao.addOneCodeInfo(phone, code, scenario);
    }

    public int updateStateById(long id) {
        return verifyCodeDao.updateStateById(id);
    }

    public VerifyCodeInfo getCodeByPhone(String phone,VerifyCodeScenario scenario) {
        return verifyCodeDao.getCodeByPhone(phone,scenario);
    }

}
