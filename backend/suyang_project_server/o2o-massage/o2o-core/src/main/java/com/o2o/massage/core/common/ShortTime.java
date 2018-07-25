package com.o2o.massage.core.common;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Author: zhongli
 * @Date: 2018/3/11 12:51
 * @Description:
 */
public class ShortTime implements Serializable, Comparable<ShortTime> {
    private static final long serialVersionUID = -4554798261160788012L;
    protected int bits;

    public ShortTime(String time) {
        String[] p = time.split(":");
        if (p.length != 3) {
            throw new IllegalArgumentException("time string pattern should be hh:mm:ss");
        } else {
            int h = Integer.parseInt(p[0]);
            int m = Integer.parseInt(p[1]);
            int s = Integer.parseInt(p[2]);
            this.init(h, m, s);
        }
    }

    public ShortTime(String time, String format) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat(format);
        Date parsedDate = df.parse(time);
        Calendar cal = Calendar.getInstance();
        cal.setTime(parsedDate);
        this.init(cal.get(11), cal.get(12), cal.get(13));
    }

    public ShortTime(int hours, int minutes, int seconds) {
        this.init(hours, minutes, seconds);
    }

    private void init(int hours, int minutes, int seconds) {
        check(hours, 0, 47, "hours is invalid");
        check(minutes, 0, 59, "minutes is invalid");
        check(seconds, 0, 59, "seconds is invalid");
        this.bits = 0;
        this.bits |= hours;
        this.bits <<= 6;
        this.bits |= minutes;
        this.bits <<= 6;
        this.bits |= seconds;
    }

    public int getHour() {
        return this.bits >> 12 & 63;
    }

    public int getMinute() {
        return this.bits >> 6 & 63;
    }

    public int getSecond() {
        return this.bits >> 0 & 63;
    }

    public int hashCode() {
        return this.bits;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null) {
            return false;
        } else if (this.getClass() != obj.getClass()) {
            return false;
        } else {
            ShortTime other = (ShortTime) obj;
            return this.bits == other.bits;
        }
    }

    public String toString() {
        return String.format("%02d:%02d:%02d", this.getHour(), this.getMinute(), this.getSecond());
    }

    public int compareTo(ShortTime o) {
        long c = (long) (this.toSeconds() - o.toSeconds());
        return c == 0L ? 0 : (c < 0L ? -1 : 1);
    }

    public boolean after(ShortTime o) {
        return this.toSeconds() > o.toSeconds();
    }

    public boolean before(ShortTime o) {
        return this.toSeconds() < o.toSeconds();
    }

    public static ShortTime valueOf(String value) {
        return new ShortTime(value);
    }

    public static ShortTime parseHHmm(String val) {
        if (val.length() != 4) {
            throw new IllegalArgumentException("illegal param of parse shortTime");
        } else {
            StringBuilder sb = new StringBuilder(val.substring(0, 2));
            sb.append(":").append(val.substring(2, 4)).append(":00");
            return new ShortTime(sb.toString());
        }
    }

    public static ShortTime valueOf(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return new ShortTime(cal.get(11), cal.get(12), cal.get(13));
    }

    public static ShortTime valueOfSeconds(int seconds) {
        int h = seconds / 3600;
        seconds -= h * 3600;
        int m = seconds / 60;
        seconds -= m * 60;
        return new ShortTime(h, m, seconds);
    }

    private static void check(int value, int min, int max, String msg) {
        if (value < min || value > max) {
            throw new IllegalArgumentException(String.format("%s: value=%d min=%d max=%d", msg, value, min, max));
        }
    }

    public int toSeconds() {
        int s = this.bits >> 0 & 63;
        int m = this.bits >> 6 & 63;
        int h = this.bits >> 12 & 63;
        return h * 3600 + m * 60 + s;
    }

    public int toLiteral() {
        int s = this.bits >> 0 & 63;
        int m = this.bits >> 6 & 63;
        int h = this.bits >> 12 & 63;
        return h * 1000 + m * 100 + s;
    }

    public ShortTime addMinutes(int minute) {
        int finalHour = getHour();
        int finalMinute = getMinute() + minute;
        if (finalMinute >= 60) {
            finalHour += finalMinute / 60;
            finalMinute = finalMinute % 60;
            return new ShortTime(finalHour, finalMinute, getSecond());
        } else {
            return new ShortTime(finalHour, finalMinute, getSecond());
        }
    }
}
