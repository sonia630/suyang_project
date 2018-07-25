package com.o2o.massage.web.client.aop;

import com.o2o.massage.biz.ClientTokenBiz;
import com.o2o.massage.biz.ClientUserBiz;
import com.o2o.massage.core.common.ApiRequestContext;
import com.o2o.massage.core.exception.NeedLoginException;
import com.o2o.massage.core.exception.TokenIllegalException;
import com.o2o.massage.core.exception.UserIdentityInvalidException;
import com.o2o.massage.dao.entity.LoginToken;
import com.o2o.massage.model.UserBaseInfoVO;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;

/**
 * @Author: zhongli
 * @Date: 2018/2/6 15:32
 * @Description: 校验接口请求源的用户身份信息
 */
@Aspect
@Component
@Order(1)
public class ClientUserIdentifyAspect {

    @Resource
    private ClientTokenBiz clientTokenBiz;

    @Resource
    private ClientUserBiz clientUserBiz;

    @Pointcut("@annotation(com.o2o.massage.web.client.aop.ClientUserIdentify)")
    public void controllerAspect() {
    }

    @Before("controllerAspect()")
    public void doBefore(JoinPoint joinPoint) throws Exception {

        Method method = getMethod(joinPoint);
        if (method == null) {
            return;
        }
        ClientUserIdentify clientUserIdentify = method.getAnnotation(ClientUserIdentify.class);

        ApiRequestContext apiRequestContext = ApiRequestContext.getCurrent();

        if (apiRequestContext == null) {
            throw new NeedLoginException();
        }

        boolean isMustLogin = clientUserIdentify.mustLogin();
        if (isMustLogin) {

            EnumRequiredUserType requiredClientUserType = clientUserIdentify.requiredClientUserType();
            apiRequestContext.setUserType(requiredClientUserType.getEnumUserType().getCode());

            LoginToken tokenInfo = clientTokenBiz.getAppTokenInfoByToken(apiRequestContext.getLoginToken());
            if (tokenInfo == null || !StringUtils.equalsIgnoreCase(tokenInfo.getLoginToken(),
                    apiRequestContext.getLoginToken())) {
                throw new TokenIllegalException("Token不存在或已过期");
            }
            apiRequestContext.setUserId(tokenInfo.getUserId());
            if (requiredClientUserType != EnumRequiredUserType.EITHER) {
                UserBaseInfoVO clientBaseUserInfo = clientUserBiz.getBaseUserInfo(apiRequestContext.getUserId());
                if ((clientBaseUserInfo.getUserType() & requiredClientUserType.getEnumUserType().getCode())
                        != requiredClientUserType.getEnumUserType().getCode()) {
                    throw new UserIdentityInvalidException("用户当前身份无法访问该接口");
                }
                apiRequestContext.setUserName(clientBaseUserInfo.getRealName());
            }
        } else if (StringUtils.isNotBlank(apiRequestContext.getLoginToken())) {
            LoginToken tokenInfo = clientTokenBiz.getAppTokenInfoByToken(apiRequestContext.getLoginToken());
            if (tokenInfo != null) {
                apiRequestContext.setUserId(tokenInfo.getUserId());
            }
        }
    }

    @AfterThrowing(value = "controllerAspect()", throwing = "e")
    public void doAfter(JoinPoint joinPoint, Exception e) {

    }

    @AfterReturning(returning = "rvt", pointcut = "controllerAspect()")
    public void afterReturn(Object rvt) {

    }

    private Method getMethod(JoinPoint joinPoint) throws Exception {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        return methodSignature.getMethod();
    }
}
