package com.o2o.massage.wechat.entity;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

/**
 * @Author: zhongli
 * @Date: 2018/3/21 19:43
 * @Description:
 */
@Data
public class WxRefundRequestData {

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
     * 签名Type 默认MD5
     */
    @XStreamAlias("sign_type")
    private String signType;

    /**
     * 商户订单号 必须
     */
    @XStreamAlias("out_trade_no")
    private String outTradeNo;

    /**
     * 商户退款单号 必须
     */
    @XStreamAlias("out_refund_no")
    private String outRefundNo;


    /**
     * 交易金额 必须[JSAPI，NATIVE，APP]
     */
    @XStreamAlias("total_fee")
    private int totalFee;


    /**
     * 通知地址 [必须]
     */
    @XStreamAlias("notify_url")
    private String notifyUrl;

    /**
     *以下属性为申请退款参数
     */
    /**
     * 微信订单号 [商户订单号二选一]
     */
    @XStreamAlias("transaction_id")
    private String transactionId;


    /**
     * 退款金额 [必须]
     */
    @XStreamAlias("refund_fee")
    private Integer refundFee;

    /**
     * 货币种类
     */
    @XStreamAlias("refund_fee_type")
    private String refundFeeType;

    /**
     * 退款原因
     */
    @XStreamAlias("refund_desc")
    private String refundDesc;

    @XStreamAlias("refund_account")
    private String refundAccount;
}
