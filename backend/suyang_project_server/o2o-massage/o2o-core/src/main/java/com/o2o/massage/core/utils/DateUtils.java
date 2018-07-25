package com.o2o.massage.core.utils;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
    private static Logger logger = LoggerFactory.getLogger(DateUtils.class);
    public static final String YYYY_MM_dd_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String YYYY_MM_dd = "yyyy-MM-dd";
    public static final String YYYYMMdd = "yyyyMMdd";

    public static String getWeekOfDate(Date dt) {
        String[] weekDays = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) {
            w = 0;
        }

        return weekDays[w];
    }


    public static Date parseDate_YYYY_MM_dd_HH_MM_SS(String dateStr) {
        return parseDate(dateStr, YYYY_MM_dd_HH_MM_SS);
    }

    public static Date parseDate_YYYY_MM_dd(String dateStr) {
        return parseDate(dateStr, YYYY_MM_dd);
    }

    public static Date parseDate_YYYYMMdd(String dateStr) {
        return parseDate(dateStr, YYYYMMdd);
    }

    private static Date parseDate(String dateStr, String pattern) {
        try {
            return new SimpleDateFormat(pattern).parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getNowDate() {
        Date date = new Date();
        DateFormat format = new SimpleDateFormat(YYYY_MM_dd_HH_MM_SS);
        String time = format.format(date);
        return time;
    }

    /**
     * 获取传入时间的之前count天,type 为0,拼接当天开始,1拼接当天结束
     */
    public static String getSpecifiedDayBefore(String specifiedDay, int count, int type) throws ParseException {//可以用new Date().toLocalString()传递参数
        String dayBefore = new SimpleDateFormat(YYYY_MM_dd_HH_MM_SS).format(
                getSpecifiedDateBefore(specifiedDay, count));
        if (type == 0) {
            dayBefore = dayBefore.substring(0, 11) + "00:00:00";
        } else {
            dayBefore = dayBefore.substring(0, 11) + "23:59:59";
        }
        return dayBefore;
    }

    public static Date getSpecifiedDateBefore(String specifiedDay, int count) {//可以用new Date().toLocalString()传递参数
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day - count);

        return c.getTime();
    }

    /**
     * 获取一个原始时间往后 count 天的Date
     *
     * @param origin 原始日期
     * @param count  往后天数
     * @return Date
     */
    public static Date getDateAfter(Date origin, int count) {
        Calendar c = Calendar.getInstance();
        c.setTime(origin);
        c.add(Calendar.DATE, count);
        return c.getTime();
    }


    public static long getBeginOfDateTime(String strDate) throws ParseException {
        return getBeginOfDate(strDate).getTime();
    }

    public static Date getBeginOfDate(String strDate) throws ParseException {
        if (strDate == null || strDate.equals("")) {
            return null;
        }
        return new SimpleDateFormat(YYYYMMdd).parse(strDate);
    }

    public static Date getBeginOfDate(Date date) throws ParseException {
        if (date == null) {
            return null;
        }
        DateFormat format = new SimpleDateFormat(YYYYMMdd);
        String strDate = format.format(date);
        return new SimpleDateFormat(YYYYMMdd).parse(strDate);
    }

    public static Date getEndOfDate(Date date) throws ParseException {
        if (date == null) {
            return null;
        }
        DateFormat format = new SimpleDateFormat(YYYYMMdd);
        String strDate = format.format(date);
        return getEndOfDate(strDate);
    }

    public static Date getEndOfDate(String strDate) throws ParseException {
        if (strDate == null || strDate.equals("")) {
            return null;
        }
        return new SimpleDateFormat("yyyyMMdd HHmmss").parse(strDate + " 235959");
    }

    public static long getEndOfDateTime(String strDate) throws ParseException {
        return getEndOfDate(strDate).getTime();
    }

    public static String formatDate(Date date) {
        return new SimpleDateFormat(YYYYMMdd).format(date);
    }

    public static String formatDate(Date date, String pattern) {
        return new SimpleDateFormat(pattern).format(date);
    }

    public static String formatToLongDate(Date date) {
        return new SimpleDateFormat(YYYY_MM_dd_HH_MM_SS).format(date);
    }

    public static long getTimestamp(String dateStr) {
        SimpleDateFormat f = new SimpleDateFormat(YYYY_MM_dd_HH_MM_SS);
        try {
            Date d = f.parse(dateStr);
            return d.getTime();
        } catch (ParseException e) {
            return 0;
        }
    }

    public static String formatDate(long time, String pattern) {
        return new SimpleDateFormat(pattern).format(new Date(time));
    }

    /**
     * yyyyMMdd 转 yyyy-MM-dd HH:mm:ss，注意起始的时间
     */
    public static String changFormat(String date, int type) {
        if (StringUtils.isEmpty(date)) return null;
        Date date1 = parseDate_YYYYMMdd(date);
        long timestamp = date1.getTime();
        String time = formatDate(timestamp, YYYY_MM_dd_HH_MM_SS);
        if (type == 0) {
            time = time.substring(0, 11) + "00:00:00";
        } else {
            time = time.substring(0, 11) + "23:59:59";
        }
        return time;
    }

    /**
     * 判断一个时间戳是不是今天
     *
     * @param timeStamp yyyy-MM-dd HH:mm:ss格式
     */
    public static boolean isToday(String timeStamp) {
        if (StringUtils.isEmpty(timeStamp)) {
            return false;
        }
        Date date = new Date();
        long now = date.getTime();
        String time = formatDate(now, YYYY_MM_dd_HH_MM_SS);
        time = time.substring(0, 11) + "00:00:00";
        try {
            date = new SimpleDateFormat(YYYY_MM_dd_HH_MM_SS).parse(time);
            return Long.valueOf(timeStamp).longValue() > date.getTime();
        } catch (Exception e) {
            logger.info("[isToday],{}", e.getMessage());
            return false;
        }
    }

    /**
     * 距离今天结束还有多少秒
     */
    public static int getTimeToEndOfToday() {
        String todayDate = formatDate(new Date());
        String endOfToday = changFormat(todayDate, 1);
        long endOfTodayTimeStamp = getTimestamp(endOfToday);
        long now =System.currentTimeMillis();
        return (int) ((endOfTodayTimeStamp - now) / 1000L);
    }

    /**
     * 得到几天前（后）的开始时间
     */
    public static Date getDateByOffset(int offset) throws ParseException {
        Date targetDate = getDateAfter(new Date(), offset);
        String time = formatDate(targetDate.getTime(), YYYY_MM_dd_HH_MM_SS);
        time = time.substring(0, 11) + "00:00:00";
        return new SimpleDateFormat(YYYY_MM_dd_HH_MM_SS).parse(time);
    }
}
