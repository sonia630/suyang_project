package com.o2o.massage.core.utils;

/**
 * @author zhuifengbuaa
 * @email tongyao1@xiaomi.com
 * @since Oct 11, 2016 11:00:15 AM
 */
public class HexUtils {


    private HexUtils() {
        throw new IllegalStateException("can't instantiate HexUtils");
    }


    public static boolean isLegal(int h){
        boolean r = true;
        switch (h){
            case 32: return r;
            case 16: return r;
            case 8:  return r;
            case 4:  return r;
            case 2:  return r;
            case 1:  return r;
            default: return false;
        }
    }

    public static boolean match(int id, int h){
    if(id < 0 || h < 0) throw new IllegalArgumentException("id, h < 0");
        switch (h){
            case 32: return id < 0x1;
            case 16: return id < 0x2;
            case 8:  return id < 0x4;
            case 4:  return id < 0x8;
            case 2:  return id < 0x10;
            case 1:  return id < 0x100;
            default: throw new IllegalArgumentException("unsupported h: " + h);
        }
    }
}
