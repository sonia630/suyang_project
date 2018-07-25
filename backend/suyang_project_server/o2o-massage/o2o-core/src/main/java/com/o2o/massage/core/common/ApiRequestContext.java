package com.o2o.massage.core.common;

import lombok.Data;

import java.util.Date;

/**
 * @Author: zhongli
 * @Date: 2018/2/6 15:05
 * @Description:
 */
@Data
public class ApiRequestContext {

    private static ThreadLocal<ApiRequestContext> threadLocal = new ThreadLocal() {
        @Override
        protected ApiRequestContext initialValue() {
            return null;
        }
    };

    protected String userIP;
    protected String methodName;
    protected String userId;
    protected String userName;
    protected String loginToken;
    protected String content;
    protected String requestUrl;
    protected String result;
    protected Date startTime;
    protected String traceId;
    protected EnumApiRequestSide apiRequestSide;
    protected int userType;

    public ApiRequestContext() {
        this.startTime = new Date();
    }

    public static void setCurrent(ApiRequestContext apiRequestContext) {
        threadLocal.set(apiRequestContext);
    }

    public static ApiRequestContext getCurrent() {
        return threadLocal.get();
    }
}
