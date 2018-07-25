package com.o2o.massage.core.utils;

import java.util.LinkedHashMap;
import java.util.Map;

import static java.util.concurrent.TimeUnit.*;

public class TimeUtils {
    private static final Map<Long, String> millisStringMap = new LinkedHashMap<Long, String>();

    public static long MAX_VALUE = 9999999999999L;

    static {
        millisStringMap.put(DAYS.toMillis(365), "年");
        millisStringMap.put(DAYS.toMillis(30), "月");
        millisStringMap.put(DAYS.toMillis(7), "周");
        millisStringMap.put(DAYS.toMillis(1), "天");
        millisStringMap.put(HOURS.toMillis(1), "小时");
        millisStringMap.put(MINUTES.toMillis(1), "分钟");
    }

    public static String getRelativeTime(final long date) {
        long duration = Math.abs(System.currentTimeMillis() - date);
        StringBuilder sb = new StringBuilder();
        for (Long current : millisStringMap.keySet()) {
            long temp = duration / current;
            if (temp > 0) {
                sb.append(temp)
                        .append(millisStringMap.get(current))
                        .append("前");
                break;
            }
        }
        return sb.toString().isEmpty() ? "刚刚" : sb.toString();
    }

}
