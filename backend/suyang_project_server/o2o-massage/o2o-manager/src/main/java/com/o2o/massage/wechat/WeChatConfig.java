package com.o2o.massage.wechat;

import com.o2o.massage.core.utils.PropertyUtil;

/**
 * @Author: zhongli
 * @Date: 2018/3/21 17:11
 * @Description:
 */
public class WeChatConfig {
    static {
        PropertyUtil.addPropertyFile("wechat.properties");
    }

    public static String getAppId() {
        return PropertyUtil.getProperty("wechat.app.id");
    }

    public static String getAppSecret() {
        return PropertyUtil.getProperty("wechat.app.secret");
    }

    public static String getMchId() {
        return PropertyUtil.getProperty("wechat.mch.id");
    }

    public static String getKey() {
        return PropertyUtil.getProperty("wechat.key");
    }

    public static String getNofityUrl() {
        return PropertyUtil.getProperty("wechat.notify.url");
    }
}
