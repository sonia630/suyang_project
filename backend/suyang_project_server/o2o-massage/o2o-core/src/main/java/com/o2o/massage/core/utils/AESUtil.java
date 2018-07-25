package com.o2o.massage.core.utils;


/**
 * Created by zhuifengbuaa on 17/2/8.
 */

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;

public class AESUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(AESUtil.class);
    public static final String KEY_ALGORITHM = "AES";
    public static final String ENCODING = "utf-8";

    public AESUtil() {
    }

    public static String generateAESKey() {
        KeyGenerator keyGenerator = null;

        try {
            keyGenerator = KeyGenerator.getInstance("AES");
        } catch (NoSuchAlgorithmException var3) {
            return null;
        }

        keyGenerator.init(128);
        SecretKey key = keyGenerator.generateKey();
        byte[] keyExternal = key.getEncoded();
        return Base64.encodeBase64String(keyExternal);
    }

    public static String encrypt(String content, String key) {
        try {
            byte[] e = Base64.decodeBase64(key);
            SecretKeySpec secretKey = new SecretKeySpec(e, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            byte[] byteContent = content.getBytes("utf-8");
            cipher.init(1, secretKey);
            byte[] result = cipher.doFinal(byteContent);
            return Base64.encodeBase64String(result);
        } catch (Exception var7) {
            LOGGER.error("encrypt error", var7);
            return null;
        }
    }

    public static String decrypt(String content, String key) {
        try {
            byte[] e = Base64.decodeBase64(key);
            SecretKeySpec secretKey = new SecretKeySpec(e, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(2, secretKey);
            byte[] result = cipher.doFinal(Base64.decodeBase64(content));
            return new String(result);
        } catch (Exception var6) {
            LOGGER.error("decrypt error", var6);
            return null;
        }
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {
        String content = "test";
        String key = generateAESKey();
        System.out.println("加密前：" + content);
        String encryptResult = encrypt(content, key);
        String decryptResult = decrypt(encryptResult, key);
        System.out.println("解密后：" + new String(decryptResult));
    }
}
