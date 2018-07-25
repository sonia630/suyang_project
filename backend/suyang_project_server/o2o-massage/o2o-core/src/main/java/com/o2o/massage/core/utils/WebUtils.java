package com.o2o.massage.core.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by lawrence on 17-1-11.
 */
public class WebUtils {

    /**
     * 获取客户端Ip地址
     *
     * @return
     */
    public static String getRemoteIpAddr(HttpServletRequest request) {
        if (null == request) {
            throw new NullPointerException("request");
        }
        // IP headers
        String ip = request.getHeader("X-Forwarded-For");
        if (org.apache.commons.lang.StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (org.apache.commons.lang.StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("REMOTE_ADDR");
        }
        if (org.apache.commons.lang.StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (org.apache.commons.lang.StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (org.apache.commons.lang.StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        return ip;
    }
}
