package com.o2o.massage.core.utils;


import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author zhuifengbuaa
 * @email tongyao1@xiaomi.com
 * @since Oct 11, 2016 11:00:15 AM
 */
public class ByteUtils {


    public static byte[] getBytes(String s, Charset charset) {
            return s.getBytes(charset);
    }

    public static byte[] getBytes(String s){
        return getBytes(s, StandardCharsets.UTF_8);
    }

    public static String toString(byte[] bytes){
        return toString(bytes, StandardCharsets.UTF_8);
    }

    public static String toString(byte[] bytes, Charset charset){
        if(bytes == null) throw new NullPointerException("bytes");
        if(charset == null) throw new NullPointerException("charset");
        return new String(bytes, charset);
    }


    public static byte[] mergeBytes(byte[] bytes1, byte[] bytes2){
        byte[] bytes = new byte[bytes1.length + bytes2.length];
        System.arraycopy(bytes1, 0, bytes, 0, bytes1.length);
        System.arraycopy(bytes2, 0, bytes, bytes1.length, bytes2.length);
        return bytes;
    }
}
