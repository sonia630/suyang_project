package com.o2o.massage.core.utils;

import org.apache.commons.lang.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * 根据request请求参数生成校验码。
 * 目前生成规则
 * 1. 将request中key值为s的参数对去掉
 * 2. 按照key值的字母顺序给request中的参数对排序
 * 3. 生成url_encoded格式的请求字符串，然后取md5值。
 */
public class SaltUtil {

    @SuppressWarnings("unused")
    private static class SMSItem {
        String phoneNumber;

        String content;
    }

    private static final Logger logger = LoggerFactory.getLogger(SaltUtil.class.getName());

    public static boolean isBlank(String str) {
        int strLen;
        if (str != null && (strLen = str.length()) != 0) {
            for (int i = 0; i < strLen; ++i) {
                if (!Character.isWhitespace(str.charAt(i))) {
                    return false;
                }
            }
            return true;
        } else {
            return true;
        }
    }

    /*
    * @Param request HttpRequest请求参数
    * @Param publishId  分配的token
    * 根据规则生成校验码，与传过来的校验码比对，判断请求是否合法
   */
    public static boolean isValidRequest(HttpServletRequest request, String uuid) {
        if (isBlank(request.getParameter("sign"))) {
            return false;
        }
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        for (Object keyObj : request.getParameterMap().keySet()) {
            String key = (String) keyObj;
            logger.debug("key : " + key + " param: " + request.getParameter(key));

            if (key.equalsIgnoreCase("sign")) {
                continue;
            }
            nameValuePairs.add(new BasicNameValuePair(key, request.getParameter(key)));
        }
        String keyFromParam = generateSign(nameValuePairs, uuid);
        logger.debug("sing:" + keyFromParam + " request param: " + request.getParameter("sign"));
        return request.getParameter("sign").toLowerCase().equals(keyFromParam.toLowerCase());
    }

    /*
    * @Param request HttpRequest请求参数
    * @Param publishId  分配的token
    * 根据规则生成校验码，与传过来的校验码比对，判断请求是否合法
    */
    public static boolean isValidRequestWithMap(HttpServletRequest request, Map<String, String> map, String uuid) {

        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

        for (String key : map.keySet()) {
            if (key.equalsIgnoreCase("sign")) {
                continue;
            }
            nameValuePairs.add(new BasicNameValuePair(key, map.get(key)));
        }

        /*for (Object keyObj : request.getParameterMap().keySet()) {
            String key = (String) keyObj;
            logger.debug("key : " + key + " param: " + request.getParameter(key));

            if (key.equalsIgnoreCase("sign")) {
                continue;
            }
            nameValuePairs.add(new BasicNameValuePair(key, request.getParameter(key)));
        }*/

        String sign = generateSign(nameValuePairs, uuid);
        logger.info("sign input:{}, sign required:{}", map.get("sign"),sign);
        boolean ok = map.get("sign").equalsIgnoreCase(sign);
        if (!ok) {
            String key = getSortedSignKeys(nameValuePairs, uuid);
            logger.warn("验证失败 {} {} {}", key, sign, map.get("sign"));
        }
        return ok;
    }

    /*
    * @Param request HttpRequest请求参数
    * @Param publishId  分配的token
    * 按照规则生成校验码
   */
    public static String generateSalt(HttpServletRequest request, String uuid) {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        for (Object keyObj : request.getParameterMap().keySet()) {
            String key = (String) keyObj;
            if (key.equalsIgnoreCase("sign")) {
                continue;
            }
            nameValuePairs.add(new BasicNameValuePair(key, request.getParameter(key)));
        }
        return generateSign(nameValuePairs, uuid);
    }

    public static String getSortedSignKeys(List<NameValuePair> nameValuePairs, String uuid) {
        Collections.sort(nameValuePairs, new Comparator<NameValuePair>() {
            @Override
            public int compare(NameValuePair p1, NameValuePair p2) {
                return p1.getName().compareTo(p2.getName());
            }
        });
        StringBuilder keyBuilder = new StringBuilder();
        boolean isFirst = true;
        for (NameValuePair nvp : nameValuePairs) {
            if (!isFirst) {
                keyBuilder.append("&");
            }
            keyBuilder.append(nvp.getName()).append("=").append(nvp.getValue());
            isFirst = false;
        }
        if (StringUtils.isNotBlank(uuid)) {
            keyBuilder.append("&").append(uuid);
        }

        String key = keyBuilder.toString();
        return key;
    }

    public static String generateSign(List<NameValuePair> nameValuePairs, String uuid) {
        String key = getSortedSignKeys(nameValuePairs, uuid);
        logger.debug("key: " + key);
        String md5 = getMd5Digest(key);
        logger.debug("md5: " + key);

        logger.info("sign sorted key:{}, sign md5:{}", key,md5);
        return md5;
    }

    private static byte[] getBytes(String s) {
        try {
            return s.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            return s.getBytes();
        }
    }

    private static String getMd5Digest(String pInput) {
        try {
            MessageDigest lDigest = MessageDigest.getInstance("MD5");
            lDigest.update(getBytes(pInput));
            BigInteger lHashInt = new BigInteger(1, lDigest.digest());
            return String.format("%1$032X", lHashInt);
        } catch (NoSuchAlgorithmException lException) {
            throw new RuntimeException(lException);
        }
    }

}
