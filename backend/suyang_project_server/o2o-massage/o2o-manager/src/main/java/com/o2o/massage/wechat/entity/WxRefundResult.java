package com.o2o.massage.wechat.entity;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

/**
 * @Author: zhongli
 * @Date: 2018/3/29 20:01
 * @Description:
 */
@Data
public class WxRefundResult extends WxBaseReturnData {

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
     * 随机字符串
     */
    @XStreamAlias("nonce_str")
    private String nonceStr;

    /**
     * 签名
     */
    @XStreamAlias("sign")
    private String sign;


    @XStreamAlias("result_code")
    private String resultCode;

    @XStreamAlias("err_code")
    private String errCode;

    @XStreamAlias("err_code_des")
    private String errCodeDes;

    /**
     * 微信订单号
     */
    @XStreamAlias("transaction_id")
    private String transactionId;

    /**
     * 微信退款单号
     */
    @XStreamAlias("refund_id")
    private String refundId;

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

    @XStreamAlias("cash_fee")
    private String cashFee;

    @XStreamAlias("total_fee")
    private int totalFee;

    @XStreamAlias("cash_fee_type")
    private String casgFeeType;

    @XStreamAlias("cash_refund_fee")
    private String cashRefundFee;
}
