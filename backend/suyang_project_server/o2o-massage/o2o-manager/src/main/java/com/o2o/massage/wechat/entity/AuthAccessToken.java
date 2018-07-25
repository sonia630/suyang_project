package com.o2o.massage.wechat.entity;

import lombok.Data;

/**
 * @Author: zhongli
 * @Date: 2018/3/21 18:01
 * @Description:
 */
@Data
public class AuthAccessToken {
    private String access_token; // 范围授权token
    private int expires_in; // 过时时间
    private String refresh_token;// 刷新token
    private String openid; // 用户的openid
    private String scope; // 范围
    private String unionid; //当且仅当该网站应用已获得该用户的userinfo授权时，才会出现该字段
    /**错误码*/
    private String errcode;
    /**错误消息*/
    private String errmsg;
}
