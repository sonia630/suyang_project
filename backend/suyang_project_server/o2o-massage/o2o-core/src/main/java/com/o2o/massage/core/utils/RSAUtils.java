package com.o2o.massage.core.utils;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class RSAUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(RSAUtils.class);
    public static final String KEY_ALGORITHM = "RSA";
    public static final String SIGNATURE_ALGORITHM = "SHA1WithRSA";
    public static final String ENCODING = "utf-8";
    public static final String X509 = "X.509";

    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;

    /**
     * RSA最大解密密文大小
     *
     * 当密钥的长度为1024时，小于117字节的明文，加密后长度都是128字节（所以分段加密后，密文的长度都是128的整数倍），所以上面rsa最大解密密文大小设置为128。但是，如果密钥的长度为2048时，这个值就要随之翻倍，设置为256。如果仍设置为128，则会抛出异常
     */
    private static final int MAX_DECRYPT_BLOCK = 128;//针对密钥的长度为1024

    public RSAUtils() {
    }

    public static PublicKey getPublicKey(String key) throws Exception {
        byte[] keyBytes = Base64.decodeBase64(key);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;
    }

    public static PrivateKey getPrivateKey(String key) throws Exception {
        byte[] keyBytes = Base64.decodeBase64(key.getBytes("utf-8"));
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;
    }

    public static String signByPrivateKey(String content, String privateKey) {
        try {
            PrivateKey e = getPrivateKey(privateKey);
            Signature signature = Signature.getInstance("SHA1WithRSA");
            signature.initSign(e);
            signature.update(content.getBytes("utf-8"));
            byte[] signed = signature.sign();
            return new String(Base64.encodeBase64URLSafe(signed), "utf-8");
        } catch (Exception e) {
            LOGGER.warn("sign error, content: {}, priKey: {}", new Object[] {content, privateKey});
            LOGGER.error("sign error", e);
            return null;
        }
    }

    public static boolean verifySignByPublicKey(String content, String sign, String publicKey) {
        try {
            PublicKey e = getPublicKey(publicKey);
            Signature signature = Signature.getInstance("SHA1WithRSA");
            signature.initVerify(e);
            signature.update(content.getBytes("utf-8"));
            return signature.verify(Base64.decodeBase64(sign.getBytes("utf-8")));
        } catch (Exception e) {
            LOGGER.warn("sign error, content: {}, sign: {}, pubKey: {}", new Object[] {content, sign, publicKey});
            LOGGER.error("sign error", e);
            return false;
        }
    }

    public static String encryptByPublicKey(String plainText, String publicKey) {
        try {
            PublicKey e = getPublicKey(publicKey);
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(1, e);
            byte[] enBytes = cipher.doFinal(plainText.getBytes("utf-8"));
            return Base64.encodeBase64String(enBytes);
        } catch (Exception e) {
            LOGGER.error("encrypt error", e);
            return null;
        }
    }

    public static String decryptByPrivateKey(String enStr, String privateKey) {
        try {
            PrivateKey e = getPrivateKey(privateKey);
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(2, e);
            byte[] deBytes = cipher.doFinal(Base64.decodeBase64(enStr));
            return new String(deBytes);
        } catch (Exception e) {
            LOGGER.error("decrypt error", e);
            return null;
        }
    }

    /** */
    /**
     * <p>
     * 公钥加密
     * </p>
     *
     * @param plainText 源数据
     * @param publicKey 公钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static String encryptByPublicKeyWithBlock(String plainText, String publicKey) {
        try {
            PublicKey e = getPublicKey(publicKey);
            // 对数据加密
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, e);
            byte[] data = plainText.getBytes("utf-8");
            int inputLen = data.length;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int offSet = 0;
            byte[] cache;
            int i = 0;
            // 对数据分段加密
            while ((inputLen - offSet) > 0) {
                if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                    cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(data, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * MAX_ENCRYPT_BLOCK;
            }
            byte[] encryptedData = out.toByteArray();
            out.close();
            return Base64.encodeBase64String(encryptedData);
        } catch (Exception ex) {
            ex.printStackTrace();
            LOGGER.error("encrypt data with block error,data:" + plainText, ex);
        }
        return null;
    }

    /**
     * <P>
     * 私钥解密
     * </p>
     *
     * @param encryptedDataStr 已加密数据
     * @param privateKey 私钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static String decryptByPrivateKeyWithBlock(String encryptedDataStr, String privateKey) {
        try {
            PrivateKey e = getPrivateKey(privateKey);
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, e);
            byte[] encryptedData = Base64.decodeBase64(encryptedDataStr);
            int inputLen = encryptedData.length;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int offSet = 0;
            byte[] cache;
            int i = 0;
            // 对数据分段解密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                    cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * MAX_DECRYPT_BLOCK;
            }
            byte[] decryptedData = out.toByteArray();
            out.close();
            return new String(decryptedData);
        } catch (Exception ex) {
            ex.printStackTrace();
            LOGGER.error("decrypt data with block error,data:" + encryptedDataStr, ex);
        }
        return null;
    }

    public static void main1(String[] args) throws Exception {
        String content = "test";
        System.out.println("加密前：" + content);
        String publicKey1 = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDpP0zU2ZjTS2kZOnas8ro+ANz55NnVAZTFoV335BWxFr+jMDX2op/ikLJ9ZO7Cawnuk53Ymk76ljqJ5572nk5FxWC91TCILb1njea4lNCqPd1/Ai4/fLtCBy8vnk0UxDZc7cUlYVyZdeXoeOHPrOfqLZtiBsvUXeP7WLVSq7zHtQIDAQAB";
        String privateKey1 = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAOk/TNTZmNNLaRk6dqzyuj4A3Pnk2dUBlMWhXffkFbEWv6MwNfain+KQsn1k7sJrCe6TndiaTvqWOonnnvaeTkXFYL3VMIgtvWeN5riU0Ko93X8CLj98u0IHLy+eTRTENlztxSVhXJl15eh44c+s5+otm2IGy9Rd4/tYtVKrvMe1AgMBAAECgYBnfwcAi20clKGV6DMwmqO5T3i+CaX+tizlZKzlifd2FLoEFyhav95iEgKhUWAANK67hhTMP+w7lN10w6ntlGD2C7uKkqtWjPvDSC2q4jFt5ZRcyJBvf7O6kBtSgQgP48p5sdHwe9LjhxqdGIJFtXtwkJvmPQS8fyFfenKchCjcAQJBAPR8viRifWGbUtO5/4ENUeImi3KNxpfodY2/72iCYKHOrk1RaYCJsSFPzJ0z99y/tyeX1FjLl49WUi3rW3xV4BUCQQD0Ow+NZhUBoweF1uqom0WQKZW1rnyOB5k9lzrjyvosvKN2KgwvG7vxuVsAzKKSqic6BQrjx9AI0JQBbiN+g5EhAkBXJwiY68sKOlZCR3F/TYI8/cSD52o9yI3vI9ZyCNftlkGoKjdTrMHJwCqOd3IJ7QWbRZJavOigHgNZfyazoeEpAkAv+uZcRgC8eJXCwm0JQK/S2YiSz7uif3WoduxX0gmB/nEyhqIsIwuyOlLZflo16/2W1WTFSPzirm9VNJj+gsRhAkBJ0woh7ohwWvvCWHZQxqMJ3e2fvdiHrAvjjGdPHdpO1FK1E23ANWHdYvRnvHM7bTm8NU74B1+eIHyW4hYOXbei";

       /* String publicKey2 = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAs261LPF0KU1LXRIFd71B" +
                "lPpqjUgXO4uX+77mxtfHATO+PwmDJ/Vh5qXCKA7azycih7jZUaqUpZCmJBAxyE95" +
                "IIrJlKvamuGJXmj9/5DkIV2xHyYDc9f7GLfAmnIcQP6rLbtSETZm7ziGFEvKZhry" +
                "TzNlKMu+fBaw9aV8HUoE2id/W3WipZ7vx5Re44IWlS6LIksFwzM3kfsxLT6MCAN3" +
                "m16r9TDxXCKsgE/c1aBF8GhbU0385FpQwby5lBZ5dmOTUbYlZAjyGJiXXsAVl1t/" +
                "udgono3xzFtI4nhMLVItoWUZ+Uh0/rmluuU/j08Qr8dA6EgNjcEMX+lxMwmX7QV3" +
                "2wIDAQAB";
        String privateKey2 = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCzbrUs8XQpTUtdEgV3vUGU+mqNSBc7i5f7vubG18cBM74/CYMn9WHmpcIoDtrPJyKHuNlRqpSlkKYkEDHIT3kgismUq9qa4YleaP3/kOQhXbEfJgNz1/sYt8CachxA/qstu1IRNmbvOIYUS8pmGvJPM2Uoy758FrD1pXwdSgTaJ39bdaKlnu/HlF7jghaVLosiSwXDMzeR+zEtPowIA3ebXqv1MPFcIqyAT9zVoEXwaFtTTfzkWlDBvLmUFnl2Y5NRtiVkCPIYmJdewBWXW3+52CiejfHMW0jieEwtUi2hZRn5SHT+uaW65T+PTxCvx0DoSA2NwQxf6XEzCZftBXfbAgMBAAECggEAayODkt5pme/JPD1yfljHPAl6bfnJxYhIq80FR124OgOHaWRoqAdg+bOoBvs3s9GOULQTLyxHrcR9L7NrxkuN69xzGQ60bR6WQ4U1hNAjJ7kl9Y9hKF6iU9QEcTQJotVtqNTc3+I3/Ecn6B6JMAaPALOTh5SAnN9/sm2pemOkGg3S9mPkN16UGWQ+OFT5AuvU0mEeqOCpaQtaDoTpxp62xsRNbm3pYjBiw7dnsuoBhEcJrOhYjkPVKcSs4XPO4vX6PVvonmTQ5S+U/I9shimVR2zFP/jXHDri2l7aOMNm6whCqhOEl1/C9svhJJnNwdwPZiLXnWsYCWQsdNt3ScOyQQKBgQDldxF3jO7gMpxQQJ6rOC0/FczuLYiFA6mHMnKGvVDP64q23A+/X+ckhY32fcJvojhqkWrsXBJjB9r+8yrpSXhuH+VLlI4tB8gXVkLbdb3S09cdQTJV0AGxIQKbub/b6Yru8AGXvRPo3ibGb2EekEF5GeGIweRb1EnSDnN7edeKuQKBgQDILoGQXu4buVcMigINmsgl7xfmYEM9tJJHPHV0nONKup15hAeUAmWGOcFhV9RIdfVxS06oPIXEWCIBjkILshtxemrjUgw3HkL4YM2OJVgZR3CRPAhStXK+/855DoAv3gKjf2kMVvIbO6Ld0MCiQtZX0dcHbKwCB2Vi2HA+zNz9MwKBgGA1ehdb+dP6pFtpnJ1BYocuxsueP701eeD7n9UqjzV+Fpzwy5F7zlgbvxf4Rouzwl+8NM6BFBx9cK6wme9SJLJXoTX9RVdQCe7u9f058qrwsyLK8SXVnr/7jTJpwS2dT+k6rBBI8I4UhsFwdZHw/eKenVdPpEV0TrdJ49WDkoHRAoGBAMeUdmC2BLdnZb5+qeVeeEY5xp5NQzz2FxEm2gPVl1ceD3rjg5qxeP8F/iNLKb7EvFykNFvPW56SL/VVG8wFWw8ceg0B9GPPq/1upgIegWNSsoxaSgIMHccHy5cuOed/dSoFEXrYBLHaF1sHkcc00/bio3wngKbVMOQH3oD+RyW5AoGBAJKS3L37bIB2KXSu8obTcou8oZiK2CLOioteob2I9yVYdEhAZj35qtloO8T9+MVpuRGD9ZsI3qY1sI1cqBhB7JTP6hngAZdKKcaVtgkwYSAoa++oCqyALoMFMlRl7O5RufDGp5/go2pgeKYNodmw/++PyLMfZcAtKAjhuquVELK+";
*/
        System.out.println(String.format("public key1:%s,privateKey1:%s",
                publicKey1.length(), privateKey1.length()));

        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        keyPairGen.initialize(1024);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        System.out.println(new String(Base64.encodeBase64(publicKey.getEncoded())));
        System.out.println(new String(Base64.encodeBase64(privateKey.getEncoded())));
         /*System.out.println(String.format("public key2:%s,privateKey2:%s",
                publicKey2.length(), privateKey2.length()));*/
        /*String encryptResult = encryptByPublicKeyWithBlock(content, publicKey2);
        System.out.println("加密后：" + encryptResult);
        String decryptResult = decryptByPrivateKeyWithBlock(encryptResult, privateKey2);
        System.out.println("解密后：" + decryptResult);*/
    }

    public static void main(String[] args) throws Exception{
        String privateKey="MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMgCnOaiBEtrR+nj" +
                "5j7qeGBXLrrTV3tmUHN6JwRezTrFGZ/J/nBxbYTnzJWbHKHKgcmClGw4U83oNuEV" +
                "szTlU7NZk61VQ09zdViLGa0C05QoOqVUp/1er3uN6eVhnOcQrO57ORW/tCV+ZoAK" +
                "qDObZ1uVHKdceHfaUrNmjGsdQJJ1AgMBAAECgYBW2ZucInpO7ZTaoVWXuGTxIKpw" +
                "AdmZAgKpQj+zDB9+B5xq4GGWHgr6jVUvGZ3DO5ZEFGmy3Hq3jMBX2P5qV7KSB5BT" +
                "FZJLYNyDZhmgpZ/Hs69rAaQoGwwBV7u2r9/HuONzyQxkCan+3oAUjiTfKnSbhkS5" +
                "11RPKrmTZugkwErUAQJBAPTgJEXZNkVcP+ampYH4CcRf7Ig1gI0sLlzdXBBVtnIc" +
                "VjRzylHQzvjckO1SbcaZZydeahegksGFO0A/CeeTmoECQQDRGLICd1l4lWDxxGpx" +
                "5yIUoMiDe0UIvt4daSujzPEaiH4cxvCpebABq81qJUYrj6stflUyb89fNuFE6OaT" +
                "gTX1AkB79dtm4IZwpjpS8JloKcAIAOckLLFexbbSm9w3CKzLJz0cYGFS+XlM0Zss" +
                "CEY5+v4VF95tB4RDIsbTxxWkfTwBAkEAyfJP+pHKl+ut9dL9+4SAJewjvNkRk6DE" +
                "ZSNuQoVtV3L8Wk7JoOUmWHbVre7SFsBrU0TwhAX5Ary+VQ+bolrpAQJBAN5CzOpd" +
                "5TcGpcgJQlz5GAQe1joFb5jbFAwrVtksRmjGE2vFAIWsEnD7wBjxid7r4kz1CDf0" +
                "oPIRpNgWeTObrhA=";
        String result=decryptByPrivateKeyWithBlock("hQ/F/HAzgNMY6GoMh+Rba8OU1qsMrobnM8OzlvycNUgufhJ6vrjBkbY1t2InR/tSf2bYtgvldNuNRc+b8vLzSXw13YrQPjNIhzBUx9c4GokbL4elW8HocTqkz1zQoeQBbNBzdl7rlrEFIhq6dVKWw3KnhcqrAMlFl1VBjBfsafo=",privateKey);
        System.out.println(result);
    }
}


