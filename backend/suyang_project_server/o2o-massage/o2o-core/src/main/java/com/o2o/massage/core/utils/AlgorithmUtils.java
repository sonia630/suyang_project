package com.o2o.massage.core.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.NameValuePair;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * @author zhuifengbuaa
 * @email tongyao1@xiaomi.com
 * @since Oct 11, 2016 11:00:15 AM
 */
public class AlgorithmUtils {

    private AlgorithmUtils(){
        throw new RuntimeException("No AlgorithmUtils instance for you");
    }

    public static String getMd5Digest(String source) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(ByteUtils.getBytes(source, StandardCharsets.UTF_8));
            BigInteger hashInt = new BigInteger(1, digest.digest());
            return String.format("%1$032x", hashInt);
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Legacy method copied from SaltUtils. No suggest to use
     * @param nameValuePairs
     * @param uuid
     * @return
     */
    @Deprecated
    public static String getKeyFromParams(List<NameValuePair> nameValuePairs, String uuid) {
        Collections.sort(nameValuePairs, new Comparator<NameValuePair>() {
            @Override
            public int compare(NameValuePair p1, NameValuePair p2) {
                return p1.getName().compareTo(p2.getName());
            }
        });

        //*****

        StringBuilder keyBuilder = new StringBuilder();
        boolean isFirst = true;
        for (NameValuePair nvp : nameValuePairs) {
            if (!isFirst) {
                keyBuilder.append("&");
            }
            keyBuilder.append(nvp.getName()).append("=").append(nvp.getValue());
            isFirst = false;
        }

        keyBuilder.append("&").append(uuid);

        String key = keyBuilder.toString();
        byte[] keyBytes = ByteUtils.getBytes(key);
        String base64 = new String(Base64.encodeBase64(keyBytes));
        String md5 = getMd5Digest(base64).toUpperCase();
        return md5;
    }

    /**
     * 加密
     *
     * @param content 需要加密的内容
     * @param password  加密密码
     * @return
     */
    public static byte[] encryptAES(String content, String password) throws Exception{
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            kgen.init(128, new SecureRandom(password.getBytes()));
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器
            byte[] byteContent = content.getBytes("utf-8");
            cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
            byte[] result = cipher.doFinal(byteContent);
            return result; // 加密
    }

    /**解密
     * @param content  待解密内容
     * @param password 解密密钥
     * @return
     */
    public static byte[] decryptAES(byte[] content, String password) throws Exception{
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            kgen.init(128, new SecureRandom(password.getBytes()));
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器
            cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
            byte[] result = cipher.doFinal(content);
            return result; // 加密
    }



    public static String getKeyFromParameters(Map<String, String> parameters, String uuid) throws UnsupportedEncodingException {
        List<String> keys = CollectionUtils.trans2List(parameters.keySet());
        Collections.sort(keys, new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                return s1.compareTo(s2);
            }
        });

        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (String key : keys) {
            if (!first) sb.append("&");
            if("sign".equals(key)) continue;
            sb.append(key).append("=").append(parameters.get(key));
            first = false;
        }

        sb.append("&").append(uuid);

        String line = sb.toString();
        byte[] keyBytes = getBytes(line);
        String base64 = new String(Base64.encodeBase64(keyBytes));
        String md5 = getMd5Digest(base64).toUpperCase();
        return md5;
    }

    public static  byte[] getBytes(String s) throws UnsupportedEncodingException {
        return s.getBytes("UTF-8");
    }
}
