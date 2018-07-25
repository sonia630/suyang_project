package com.o2o.massage.wechat.entity;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

/**
 * @Author: zhongli
 * @Date: 2018/3/21 18:39
 * @Description:
 */
@Data
public class WxUnifiedOrderData {
    /**
     * 公众账号ID 必须
     */
    @XStreamAlias("appid")
    private String appId;

    /**
     * 商户号 必须
     */
    @XStreamAlias("mch_id")
    private String mchId;

    /**
     * 设备号
     */
    @XStreamAlias("device_info")
    private String deviceInfo;

    /**
     * 随机字符串 必须
     */
    @XStreamAlias("nonce_str")
    private String nonceStr;

    /**
     * 签名 必须
     */
    @XStreamAlias("sign")
    private String sign;

    /**
     * 签名Type 必须
     */
    @XStreamAlias("sign_type")
    private String signType;


    /**
     * 商品描述 必须
     */
    @XStreamAlias("body")
    private String body;

    /**
     * 商品详情
     */
    @XStreamAlias("detail")
    private String detail;

    /**
     * 附加数据
     */
    @XStreamAlias("attach")
    private String attach;

    /**
     * 商户订单号 必须
     */
    @XStreamAlias("out_trade_no")
    private String outTradeNo;

    /**
     * 货币类型
     */
    @XStreamAlias("fee_type")
    private String feeType;

    /**
     * 交易金额 必须[JSAPI，NATIVE，APP]
     */
    @XStreamAlias("total_fee")
    private int totalFee;

    /**
     * 交易类型 [必须]
     */
    @XStreamAlias("trade_type")
    private String tradeType;

    /**
     * 通知地址 [必须]
     */
    @XStreamAlias("notify_url")
    private String notifyUrl;

    /**
     * 终端Ip [必须]
     */
    @XStreamAlias("spbill_create_ip")
    private String spBillCreateIp;

    /**
     * 订单生成时间yyyyMMddHHmmss
     */
    @XStreamAlias("time_start")
    private String timeStart;

    /**
     * 订单失效时间yyyyMMddHHmmss 间隔>5min
     */
    @XStreamAlias("time_expire")
    private String timeExpire;

    /**
     * 用户标识 tradeType=JSAPI时必须
     */
    @XStreamAlias("openid")
    private String openId;

    /**
     * 商品标记
     */
    @XStreamAlias("goods_tag")
    private String goodsTag;

    /**
     * 商品ID tradeType=NATIVE时必须
     */
    @XStreamAlias("product_id")
    private String productId;

    /**
     * 指定支付方式
     */
    @XStreamAlias("limit_pay")
    private String limitPay;

    @XStreamAlias("scene_info")
    private String sceneInfo;
}
