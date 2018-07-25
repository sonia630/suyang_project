package com.o2o.massage.core.utils;

import org.slf4j.MDC;

/**
 * add by lizhong 2017-09-04
 */
public class GlobalStates {

    private static String TRACE_ID_KEY = "bingo_trace_id";

    private static final ThreadLocal<String> stringThreadLocal = new ThreadLocal<String>();

    public static String getTraceId() {
        String traceId = MDC.get(TRACE_ID_KEY);
        if (org.apache.commons.lang.StringUtils.isBlank(traceId)) {
            traceId = RandomUUID.shorterUUID();
            MDC.put(TRACE_ID_KEY, traceId);
        }
        return traceId;
    }

    public static void setTraceId(String traceId) {
        //stringThreadLocal.set(traceId);
        MDC.put(TRACE_ID_KEY, traceId);
    }

    public static void cleanTraceId() {
        MDC.remove(TRACE_ID_KEY);
    }

    public static void resetTraceId() {
        MDC.remove(TRACE_ID_KEY);
        getTraceId();
    }
}
