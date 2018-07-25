package com.o2o.massage.web.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class JspFunctions {

    public static String computeAge(Date date) {
        Calendar end = Calendar.getInstance();
        Calendar start = Calendar.getInstance();
        start.setTime(date);
        int month = end.get(Calendar.MONTH) - start.get(Calendar.MONTH);
        int year = (end.get(Calendar.YEAR) - start.get(Calendar.YEAR)) * 12;
        int months = year + month;
        int _year = months / 12;
        if (_year > 0) {
            return _year + "岁" + months % 12 + "个月";
        } else {
            return months % 12 + "个月";
        }
    }

    public static void main(String[] args) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(JspFunctions.computeAge(simpleDateFormat.parse("2017-05-12")));
    }
}
