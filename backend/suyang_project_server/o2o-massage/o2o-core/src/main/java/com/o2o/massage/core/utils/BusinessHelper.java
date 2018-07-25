package com.o2o.massage.core.utils;

import org.apache.commons.lang.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: zhongli
 * @Date: 2018/2/23 17:07
 * @Description:
 */
public class BusinessHelper {

    public static final String CN_PHONE = "^0?(13[0-9]|15[012356789]|17[0-9]|18[0-9]|14[57])[0-9]{8}$";//手机号

    public static final String ALL_DEVICE_ID = "guest_device_id";
    public static final String GUEST_USER = "-200";

    /**
     * 检查手机号是否有效
     *
     * @param phone 中国手机号
     * @return true 有效
     */
    public static boolean isPhoneValid(String phone) {
        Pattern pattern = Pattern.compile(CN_PHONE);
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }

    /**
     * 检查邮箱是否有效
     *
     * @param email
     * @return
     */
    public static boolean isEmailValid(String email) {
        String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern p = Pattern.compile(check);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    public static boolean isFirstPageQuery(String after) {
        if (StringUtils.isBlank(after) || StringUtils.equals(after, "0")) {
            return true;
        }
        return false;
    }

    public static boolean isGuestUser(String userId) {
        return StringUtils.isBlank(userId) || GUEST_USER.equals(userId);
    }
}
