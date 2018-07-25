package com.o2o.massage.wechat.entity;

import lombok.Data;

import java.util.Map;
import java.util.TreeMap;

/**
 * @Author: zhongli
 * @Date: 2018/3/21 18:00
 * @Description:
 */
@Data
public class AuthTokenParams {
    private String appid; //公众号的唯一标识
    private String secret; //公众号的appsecret
    private String code; //填写第一步获取的code参数
    private String grant_type = "authorization_code";

    public AuthTokenParams() {
        super();
    }

    public AuthTokenParams(String appid, String secret, String code, String grant_type) {
        super();
        this.appid = appid;
        this.secret = secret;
        this.code = code;
        this.grant_type = grant_type;
    }

    /**
     * 参数组装
     * @return
     */
    public Map<String, String> getParams() {
        Map<String, String> params = new TreeMap<String, String>();
        params.put("appid", this.appid);
        params.put("secret", this.secret);
        params.put("code", this.code);
        params.put("grant_type", this.grant_type);
        return params;
    }
}
