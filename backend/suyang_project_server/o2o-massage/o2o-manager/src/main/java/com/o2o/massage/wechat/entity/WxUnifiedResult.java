package com.o2o.massage.wechat.entity;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

/**
 * @Author: zhongli
 * @Date: 2018/3/21 19:52
 * @Description:
 */
@Data
public class WxUnifiedResult extends WxBaseReturnData {

    //以下字段在return_code为SUCCESS的时候有返回
    /**
     * 公众账号ID
     */
    @XStreamAlias("appid")
    private String appId;

    /**
     * 商户号
     */
    @XStreamAlias("mch_id")
    private String mchId;

    /**
     * 设备号
     */
    @XStreamAlias("device_info")
    private String deviceInfo;

    /**
     * 随机字符串
     */
    @XStreamAlias("nonce_str")
    private String nonceStr;

    /**
     * 签名
     */
    @XStreamAlias("sign")
    private String sign;

    /**
     * 签名Type 必须
     */
    @XStreamAlias("sign_type")
    private String signType;

    @XStreamAlias("result_code")
    private String resultCode;

    @XStreamAlias("err_code")
    private String errCode;

    @XStreamAlias("err_code_des")
    private String errCodeDes;

    //以下字段在return_code 和result_code都为SUCCESS的时候有返回
    /**
     * 交易类型 JSAPI-公众号支付 ATIVE-扫码支付   APP-APP支付
     */
    @XStreamAlias("trade_type")
    private String tradeType;
    /**
     * 预支付交易会话标识
     */
    @XStreamAlias("prepay_id")
    private String prepayId;
    /**
     * 二维码链接
     */
    @XStreamAlias("code_url")
    private String codeUrl;
}
