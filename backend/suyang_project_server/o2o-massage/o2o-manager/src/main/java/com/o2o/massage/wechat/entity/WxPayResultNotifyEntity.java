package com.o2o.massage.wechat.entity;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

/**
 * @Author: zhongli
 * @Date: 2018/3/22 17:42
 * @Description:
 */
@Data
public class WxPayResultNotifyEntity extends WxBaseReturnData {
    //以下字段在return_code为SUCCESS的时候有返回
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

    @XStreamAlias("result_code")
    private String resultCode;

    @XStreamAlias("err_code")
    private String errCode;

    @XStreamAlias("err_code_des")
    private String errCodeDes;

    @XStreamAlias("openid")
    private String openid;//	用户标识

    @XStreamAlias("is_subscribe")
    private String isSubscribe;//	是否关注公众账号

    @XStreamAlias("trade_type")
    private String tradeType;//	交易类型

    @XStreamAlias("bank_type")
    private String bankType;//	付款银行

    @XStreamAlias("total_fee")
    private String totalFee;//	订单金额
    @XStreamAlias("settlement_total_fee")
    private String settlement_total_fee;//	应结订单金额
    @XStreamAlias("fee_type")
    private String feeType;//	货币种类
    @XStreamAlias("cash_fee")
    private String cashFee;//	现金支付金额
    @XStreamAlias("cash_fee_type")
    private String cashFeeType;//	现金支付货币类型
    @XStreamAlias("coupon_fee")
    private String couponFee;//	总代金券金额
    @XStreamAlias("coupon_count")
    private String couponCount;//	代金券使用数量
    /*private String 	coupon_type_$n	;//	代金券类型
    private String 	coupon_id_$n	;//	代金券ID
    private String 	coupon_fee_$n	;//	单个代金券支付金额*/
    @XStreamAlias("transaction_id")
    private String transactionId;//	微信支付订单号
    @XStreamAlias("out_trade_no")
    private String outTradeNo;//	商户订单号
    @XStreamAlias("attach")
    private String attach;//	商家数据包
    @XStreamAlias("timeEnd")
    private String time_end;//	支付完成时间

}
