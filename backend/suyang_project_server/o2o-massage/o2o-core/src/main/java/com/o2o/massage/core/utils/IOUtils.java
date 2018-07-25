package com.o2o.massage.core.utils;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author zhuifengbuaa
 * @email tongyao1@xiaomi.com
 * @since Oct 11, 2016 11:00:15 AM
 */
public class IOUtils {

    private IOUtils() {
        throw new RuntimeException("Can't instantiate IOUtils instance");
    }

    public static byte[] readBytes(InputStream in) throws IOException {
        if(in == null) throw new NullPointerException("input stream can't be null");
        byte[] bytes = new byte[512];
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        BufferedInputStream  bis = new BufferedInputStream(in);
        int rc;
        while((rc = bis.read(bytes, 0, 512)) > 0){
            bos.write(bytes, 0, rc);
        }
        return bos.toByteArray();
    }

    public static byte[] readBytes(InputStream in, int length) throws IOException {
        if(in == null) throw new NullPointerException("input stream can't be null");
        if(length < 0) throw new IllegalArgumentException("length < 0");
        if(length == 0) return new byte[0];
        byte[] bytes = new byte[length];
        int size;
        int off = 0;
        while((size = in.read(bytes, off, length - off)) > 0) off += size;
        return bytes;
    }
}
