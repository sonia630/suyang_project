package com.o2o.massage.core.utils;

/**
 * Created by leijian@xiaomi.com on 2017/3/2.
 */
public class Numbers {
    private Numbers() { }

    public static boolean valueOf(Boolean b) {
        return valueOf(b, false);
    }

    public static boolean valueOf(Boolean b1, boolean b2) {
        if (null == b1) {
            return b2;
        } else {
            return b1.booleanValue();
        }
    }

    public static byte valueOf(Byte b) {
        return valueOf(b, (byte)0);
    }

    public static byte valueOf(Byte b1, byte b2) {
        if (null == b1) {
            return b2;
        } else {
            return b1.byteValue();
        }
    }

    public static char valueOf(Character c) {
        return valueOf(c, '\u0000');
    }

    public static char valueOf(Character c1, char c2) {
        if (null == c1) {
            return c2;
        } else {
            return c1.charValue();
        }
    }

    public static short valueOf(Short s) {
        return valueOf(s, (short)0);
    }

    public static short valueOf(Short s1, short s2) {
        if (null == s1) {
            return s2;
        } else {
            return s1.shortValue();
        }
    }

    public static int valueOf(Integer i) {
        return valueOf(i, 0);
    }

    public static int valueOf(Integer i1, int i2) {
        if (null == i1) {
            return i2;
        } else {
            return i1.intValue();
        }
    }

    public static long valueOf(Long l) {
        return valueOf(l, 0L);
    }

    public static long valueOf(Long l1, long l2) {
        if (null == l1) {
            return l2;
        } else {
            return l1.longValue();
        }
    }

    public static float valueOf(Float f) {
        return valueOf(f, 0.0f);
    }

    public static float valueOf(Float f1, float f2) {
        if (null == f1) {
            return f2;
        } else {
            return f1.floatValue();
        }
    }

    public static double valueOf(Double d) {
        return valueOf(d, 0.0d);
    }

    public static double valueOf(Double d1, double d2) {
        if (null == d1) {
            return d2;
        } else {
            return d1.doubleValue();
        }
    }
}
