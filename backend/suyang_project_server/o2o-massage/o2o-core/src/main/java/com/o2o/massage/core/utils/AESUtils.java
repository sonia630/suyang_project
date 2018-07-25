package com.o2o.massage.core.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author zhuifengbuaa
 * @email tongyao1@xiaomi.com
 * @since Oct 11, 2016 11:00:15 AM
 */
public class AESUtils {

    private static final String TAG = AESUtils.class.getSimpleName();
    private static final String ALG_STR = "AES";
    private static final String TRANS_STR = "AES/CBC/PKCS5Padding";
    private static final String EMPTY_STR = "";
    private static final String KEY = "0123456789123456";
    private static final String IV = "0102030405060708";
    private static final String HEX = "0123456789ABCDEF";

    public static String encrypt(String plainText) throws Exception{
        byte[] rawKey = KEY.getBytes();
        byte[] result = encrypt(rawKey, plainText.getBytes());
        return toHex(result);
    }

    private static byte[] encrypt(byte[] key, byte[] src) throws Exception{
        SecretKeySpec sKeySpec = new SecretKeySpec(key, ALG_STR);
        Cipher cipher = Cipher.getInstance(TRANS_STR);
        cipher.init(Cipher.ENCRYPT_MODE, sKeySpec, new IvParameterSpec(IV.getBytes()));
        byte[] result = cipher.doFinal(src);
        return result;
    }

    private static String toHex(byte[] res) {
        if (res == null) {
            return EMPTY_STR;
        }
        StringBuffer sb = new StringBuffer(res.length * 2);
        for (int i = 0; i < res.length; i++) {
            appendHex(sb, res[i]);
        }
        return sb.toString();
    }

    private static void appendHex(StringBuffer sb, byte b) {
        sb.append(HEX.charAt((b >> 4) & 0x0f)).append(HEX.charAt(b & 0x0f));
    }

    public static String decrypt(String cipherText) throws Exception{
        byte[] rawKey = KEY.getBytes();
        byte[] byteText = toByte(cipherText);
        byte[] result = decrypt(rawKey, byteText);
        if (result == null) {
            return null;
        }
        return new String(result);
    }

    private static byte[] decrypt(byte[] key, byte[] byteText) throws Exception{
        SecretKeySpec sKeySpec = new SecretKeySpec(key, ALG_STR);
        Cipher cipher = Cipher.getInstance(TRANS_STR);
        cipher.init(Cipher.DECRYPT_MODE, sKeySpec, new IvParameterSpec(IV.getBytes()));
        byte[] result = cipher.doFinal(byteText);
        return result;
    }

    private static byte[] toByte(String cipherText) {
        int len = cipherText.length() / 2;
        byte[] result = new byte[len];
        for (int i = 0; i < len; i++)
            result[i] = Integer.valueOf(cipherText.substring(2 * i, 2 * i + 2),
                    16).byteValue();
        return result;
    }
}

