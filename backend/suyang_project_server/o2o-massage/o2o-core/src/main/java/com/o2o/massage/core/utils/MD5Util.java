package com.o2o.massage.core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5工具类
 */

public class MD5Util {

    private static Logger logger = LoggerFactory.getLogger(MD5Util.class);

    // 全局数组
    private final static String[] strDigits = {"0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    /**
     * 返回形式为数字跟字符串
     *
     * @param bByte
     * @return
     */
    private static String byteToArrayString(byte bByte) {
        int iRet = bByte;
        if (iRet < 0) {
            iRet += 256;
        }
        int iD1 = iRet / 16;
        int iD2 = iRet % 16;
        return strDigits[iD1] + strDigits[iD2];
    }

    /**
     * 转换字节数组为16进制字串
     *
     * @param bByte
     * @return
     */
    private static String byteToString(byte[] bByte) {
        StringBuffer sBuffer = new StringBuffer();
        for (int i = 0; i < bByte.length; i++) {
            sBuffer.append(byteToArrayString(bByte[i]));
        }
        return sBuffer.toString();
    }

    /**
     * 生成MD5值
     *
     * @param strObj
     * @return
     */
    public static String encode(String strObj) {
        String resultString = encode(strObj.getBytes());
        /*try {
            resultString = ;
            MessageDigest md = MessageDigest.getInstance("MD5");
            resultString = byteToString(md.digest(strObj.getBytes()));
        } catch (NoSuchAlgorithmException ex) {
            logger.error("MD5 Encode Error : ", ex);
        }*/
        return resultString;
    }

    public static String encode(byte[] input) {
        if (input == null || input.length == 0) {
            return null;
        }
        String resultString = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            resultString = byteToString(md.digest(input));
        } catch (NoSuchAlgorithmException ex) {
            logger.error("MD5 Encode Error : ", ex);
        }
        return resultString;
    }

    public static void main(String[] args) throws Exception {
        String info = "abcd1234";
        String encode = encode(info);
        System.out.println("encodeImeiString: " + encode);
    }
}