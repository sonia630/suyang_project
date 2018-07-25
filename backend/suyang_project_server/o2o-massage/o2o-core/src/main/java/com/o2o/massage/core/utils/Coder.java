
package com.o2o.massage.core.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.ArrayUtils;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

public abstract class Coder {
    public static final String KEY_SHA = "SHA";
    public static final String KEY_MD5 = "MD5";

    /**
     * MAC算法可选以下多种算法
     *
     * <pre>
     * HmacMD5
     * HmacSHA1
     * HmacSHA256
     * HmacSHA384
     * HmacSHA512
     * </pre>
     */
    public static final String KEY_MAC = "HmacMD5";

    public static final String KEY_PBKDF2 = "PBKDF2WithHmacSHA1";
    private static final String HMAC_SHA1 = "HmacSHA1";

    private static final String KEY_SHA256 = "SHA-256";

    /**
     * BASE64
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decryptBASE64(String key) {
        return Base64.decodeBase64(key);
    }

    /**
     * BASE64
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static String encryptBASE64(byte[] key) {
        try {
            return new String(Base64.encodeBase64(key), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            // never happend
            return null;
        }
    }

    /**
     * MD5
     *
     * @param data
     * @return
     * @throws NoSuchAlgorithmException
     * @throws Exception
     */
    public static byte[] encryptMD5(byte[] data) throws NoSuchAlgorithmException {

        MessageDigest md5 = MessageDigest.getInstance(KEY_MD5);
        md5.update(data);

        return md5.digest();

    }

    /**
     * MD5
     *
     * @param data
     * @return
     * @throws NoSuchAlgorithmException
     * @throws Exception
     */
    public static byte[] encryptMD5(byte[]... data) throws NoSuchAlgorithmException {

        MessageDigest md5 = MessageDigest.getInstance(KEY_MD5);
        for (byte[] one : data) {
            if (ArrayUtils.isEmpty(one)) {
                continue;
            }

            md5.update(one);
        }

        return md5.digest();

    }

    /**
     * SHA
     *
     * @param data
     * @return
     * @throws NoSuchAlgorithmException
     * @throws Exception
     */
    public static byte[] encryptSHA(byte[] data) throws NoSuchAlgorithmException {

        MessageDigest sha = MessageDigest.getInstance(KEY_SHA);
        sha.update(data);

        return sha.digest();

    }

    /**
     * 初始化HMAC密钥
     *
     * @return
     * @throws Exception
     */
    public static String initMacKey() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(KEY_MAC);

        SecretKey secretKey = keyGenerator.generateKey();
        return encryptBASE64(secretKey.getEncoded());
    }

    /**
     * HMAC-MD5
     *
     * @param data
     * @param key
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws Exception
     */
    public static byte[] encryptHMAC(byte[] data, byte[] key) throws NoSuchAlgorithmException, InvalidKeyException {

        SecretKey secretKey = new SecretKeySpec(key, KEY_MAC);
        Mac mac = Mac.getInstance(secretKey.getAlgorithm());
        mac.init(secretKey);

        return mac.doFinal(data);

    }

    /**
     * HMAC-MD5
     *
     * @param data
     * @param key
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws Exception
     */
    public static byte[] encryptHMAC(byte[] data, String key) throws NoSuchAlgorithmException, InvalidKeyException {

        SecretKey secretKey = new SecretKeySpec(decryptBASE64(key), KEY_MAC);
        Mac mac = Mac.getInstance(secretKey.getAlgorithm());
        mac.init(secretKey);

        return mac.doFinal(data);

    }

    /**
     * HMAC-SHA1
     *
     * @param data
     * @param key
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     */
    public static byte[] encryptHMACSha1(byte[] data, byte[] key) throws NoSuchAlgorithmException, InvalidKeyException {
        SecretKeySpec signingKey = new SecretKeySpec(key, HMAC_SHA1);
        Mac mac = Mac.getInstance(HMAC_SHA1);
        mac.init(signingKey);
        mac.update(data);

        return mac.doFinal();
    }

    /**
     * HMAC-SHA1
     *
     * @param data
     * @param key
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     */
    public static byte[] sha256Hash(byte[] data) throws NoSuchAlgorithmException, InvalidKeyException {
        MessageDigest sha = MessageDigest.getInstance(KEY_SHA256);
        sha.update(data);
        return sha.digest();
    }

    /**
     * 生成PBKDF2的key
     *
     * @param data
     * @param salt
     * @param iterationCount
     * @param keyLength
     * @return
     * @throws InvalidKeySpecException
     * @throws NoSuchAlgorithmException
     */
    public static byte[] createPBKDF2Key(char[] data, byte[] salt, int iterationCount, int keyLength) throws InvalidKeySpecException,
                                                                                                             NoSuchAlgorithmException {
        KeySpec spec = new javax.crypto.spec.PBEKeySpec(data, salt, iterationCount, keyLength);
        SecretKey sk = SecretKeyFactory.getInstance(KEY_PBKDF2).generateSecret(spec);
        return sk.getEncoded();
    }
}
