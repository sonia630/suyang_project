package com.o2o.massage.biz.impl;

import java.util.Calendar;
import java.util.Date;

public class Schedule {

    public static final int start = 8;


    public static Date getDate(Date date, int slot) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int div = slot / 2;
        int yu = slot % 2;
        if (yu == 0) {
            div -= 1;
        }
        calendar.set(Calendar.HOUR_OF_DAY, start + div);
        calendar.set(Calendar.MINUTE, yu == 0 ? 30 : 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }
}
