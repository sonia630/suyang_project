package com.o2o.massage.web.common.interceptors;


import com.o2o.massage.core.common.DeviceInfo;
import com.o2o.massage.core.utils.GlobalStates;
import com.o2o.massage.core.utils.JSONUtils;
import com.o2o.massage.web.common.GlobalExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static com.o2o.massage.web.common.filter.RequestCaptureFilter.KEY_REQUEST_BODY_OBJECT;


/**
 * Created by lawrence on 16-12-20.
 */
public class LogInterceptor implements HandlerInterceptor {
    private final static Logger logger = LoggerFactory.getLogger(LogInterceptor.class);
    public static final String NEXUS_REQUEST_START_TIME = "nexus_request_start_time";
    private List<String> logUriPrefixes;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        request.setAttribute(NEXUS_REQUEST_START_TIME, System.currentTimeMillis());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        if (needLog(request)) {

            Object currException = request.getAttribute(GlobalExceptionHandler.CURRENT_REQUEST_EXCEPTION);
            boolean success = currException == null;

            //timecost: millseconds
            long timecost = System.currentTimeMillis() - (Long) request.getAttribute(NEXUS_REQUEST_START_TIME);

            String userId = "0";//UserUtils.getFromUid(request);
            DeviceInfo deviceInfo = null;//UserUtils.getDeviceInfo(request);
            if (logger.isInfoEnabled()) {
                logger.info("request uri:{}, fromuid:{}, success:{}, timecost:{}ms, deviceInfo:{},requestBody:{},requestParas:{}",
                        request.getRequestURI(), userId, success, timecost, deviceInfo == null ? null : deviceInfo.toString(),
                        request.getAttribute(KEY_REQUEST_BODY_OBJECT), JSONUtils.writeValue(request.getParameterMap()));
            }
        }
        GlobalStates.cleanTraceId();
    }

    /**
     * @param request
     * @return
     */
    private boolean needLog(HttpServletRequest request) {
        /*boolean needLog = false;
        String requestURI = request.getRequestURI();
        String contextPath = request.getContextPath();
        if (logUriPrefixes != null) {
            for (String logURI : logUriPrefixes) {
                if (requestURI.startsWith(contextPath)) {
                    needLog = true;
                }
            }
        }
        return needLog;*/
        return true;
    }

    public List<String> getLogUriPrefixes() {
        return logUriPrefixes;
    }

    public void setLogUriPrefixes(List<String> logUriPrefix) {
        this.logUriPrefixes = logUriPrefix;
    }

}
