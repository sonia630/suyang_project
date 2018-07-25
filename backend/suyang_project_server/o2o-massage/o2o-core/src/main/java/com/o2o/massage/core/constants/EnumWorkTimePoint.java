package com.o2o.massage.core.constants;

/**
 * @Author: zhongli
 * @Date: 2018/3/11 12:37
 * @Description:
 */
public enum EnumWorkTimePoint {

    START_TIME(8, 0, "8:00"),

    END_TIME(22, 0, "22:00");

    private int hour;
    private int minute;
    private String title;

    EnumWorkTimePoint(int hour, int minute, String title) {
        this.hour = hour;
        this.minute = minute;
        this.title = title;
    }

    public String title() {
        return this.title;
    }

    public int hour() {
        return this.hour;
    }

    public int minute() {
        return this.minute;
    }
}
