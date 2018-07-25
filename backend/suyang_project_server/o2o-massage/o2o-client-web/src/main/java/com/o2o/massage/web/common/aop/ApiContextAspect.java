package com.o2o.massage.web.common.aop;

import com.o2o.massage.core.common.ApiRequestContext;
import com.o2o.massage.web.common.context.ApiRequestContextFactory;
import com.o2o.massage.web.common.context.ApiRequestMethod;
import com.o2o.massage.web.common.filter.RequestCaptureFilter;
import com.o2o.massage.web.common.helper.WebUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @Author: zhongli
 * @Date: 2018/2/6 15:32
 * @Description:
 */
@Aspect
@Component
@Order(0)
public class ApiContextAspect {

    @Pointcut("@annotation(com.o2o.massage.web.common.context.ApiRequestMethod)")
    public void controllerAspect() {
    }

    @Before("controllerAspect()")
    public void doBefore(JoinPoint joinPoint) throws Exception {

        Method method = getMethod(joinPoint);
        if (method == null) {
            return;
        }

        ApiRequestMethod apiRequestMethod = method.getAnnotation(ApiRequestMethod.class);


        ApiRequestContext context = ApiRequestContextFactory
                .createApiRequestContext(apiRequestMethod.requestSide());

        if (context == null) {
            return;
        }
        context.setApiRequestSide(apiRequestMethod.requestSide());
        String signature = joinPoint.getSignature().toString(); // 获取目标方法签名
        String methodName = signature.substring(signature.lastIndexOf(".") + 1, signature.indexOf("("));
        context.setMethodName(methodName);
        Object bodyObj = WebUtils.httpServletRequest().getAttribute(RequestCaptureFilter.KEY_REQUEST_BODY_OBJECT);
        context.setContent(bodyObj == null ? null : (String) bodyObj);

        ApiRequestContext.setCurrent(context);
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
