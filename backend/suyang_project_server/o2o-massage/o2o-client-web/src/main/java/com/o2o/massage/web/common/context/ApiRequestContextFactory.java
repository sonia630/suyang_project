package com.o2o.massage.web.common.context;

import com.o2o.massage.core.common.ApiRequestContext;
import com.o2o.massage.core.common.DeviceInfo;
import com.o2o.massage.core.common.EnumApiRequestSide;
import com.o2o.massage.web.Constants;
import com.o2o.massage.web.common.helper.CookieUtil;
import com.o2o.massage.web.common.helper.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 *
 */
public class ApiRequestContextFactory {

    private static Logger logger = LoggerFactory.getLogger(ApiRequestContextFactory.class);

    public static ApiRequestContext createApiRequestContext(EnumApiRequestSide enumApiRequestSide) {
        ApiRequestContext context = null;

        HttpServletRequest request = WebUtils.httpServletRequest();
        String traceId = request.getParameter("traceId");
        switch (enumApiRequestSide) {
            case CLIENT:
                String token = CookieUtil.getCookieValue(request, Constants.cookies_token);
                ClientRequestContext clientRequestContext = new ClientRequestContext();
//                clientRequestContext.setLoginToken(request.getHeader("Authorization"));
                clientRequestContext.setLoginToken(token);
                DeviceInfo deviceInfo = new DeviceInfo();
                Object laObj = request.getHeader("la");
                deviceInfo.setLatitude(laObj == null ? -1d : Double.parseDouble(String.valueOf(laObj)));
                Object loObj = request.getHeader("lo");
                deviceInfo.setLongitude(loObj == null ? -1d : Double.parseDouble(String.valueOf(loObj)));
                clientRequestContext.setDeviceInfo(deviceInfo);
                context = clientRequestContext;
                break;
            case BACKEND:
                context = new ApiRequestContext();
                context.setLoginToken(request.getHeader("Authorization"));
                break;
        }

        if (context != null) {
            context.setTraceId(traceId);
            context.setUserIP(WebUtils.userIp(request));
            context.setRequestUrl(request.getRequestURL().toString());
            ApiRequestContext.setCurrent(context);
        }

        return context;
    }
}
