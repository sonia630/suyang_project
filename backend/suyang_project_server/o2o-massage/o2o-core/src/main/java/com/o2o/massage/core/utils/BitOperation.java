package com.o2o.massage.core.utils;

/**
 * @author zhuifengbuaa
 * @email tongyao1@xiaomi.com
 * @since Oct 11, 2016 11:00:15 AM
 */
public class BitOperation {


    /**
     * 检查相应位置是否为1
     * @param l
     * @param h
     * @return
     */
    public static boolean isOpen(long l, long h){
        return (l & h) > 0;
    }

    /**
     * 检查相应位置是否为0
     * @param l
     * @param h
     * @return
     */
    public static boolean isClose(long l, long h){
        return (l & h) == 0;
    }
    /**
     * 与h的反做与运算
     * @param l
     * @param h
     * @return
     */
    public static long close(long l, long h){
        if(h <= 0) throw new IllegalArgumentException("h must greater than 0");
        return l & (~h);
    }

    /**
     * 与h做或运算
     * @param l
     * @param h
     * @return
     */
    public static long open(long l, long h){
        if(h <= 0) throw new IllegalArgumentException("h must greater than 0");
        return l | h;
    }


    /**
     *
     * @param l
     * @param h
     * @param isOpen
     * @return
     */
    public static long operate(long l, long h, boolean isOpen){
        if(isOpen) return open(l,h);
        return close(l,h);
    }

    /**
     * 十进制计算
     * 将l第n位修改为0
     * @param l
     * @param n
     * @return
     */
    public static long setZeroByDec(long l, int n){
        if(n <= 0) throw new IllegalArgumentException("n must greater than 0");
        return l & (~(int)Math.pow(2, n - 1));
    }

    /**
     * 十进制计算
     * 将l第n位修改为1
     * @param l
     * @param n
     * @return
     */
    public static long setOneByDec(long l, int n){
        if(n <= 0) throw new IllegalArgumentException("n must greater than 0");
        return l | (int)Math.pow(2, n -1);
    }
}
