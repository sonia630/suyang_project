package com.o2o.massage.builder;

import com.o2o.massage.wechat.entity.WxRefundRequestData;
import com.o2o.massage.wechat.entity.WxUnifiedOrderData;
import org.apache.commons.lang3.StringUtils;

import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @Author: zhongli
 * @Date: 2018/3/21 18:44
 * @Description:
 */
public class WxPayDataBuilder {

    public static SortedMap<String, String> buildParamMap(WxUnifiedOrderData data) {
        SortedMap<String, String> paramters = new TreeMap<String, String>();
        if (null != data) {
            if (StringUtils.isNotEmpty(data.getAppId())) {
                paramters.put("appid", data.getAppId());
            }
            if (StringUtils.isNotEmpty(data.getAttach())) {
                paramters.put("attach", data.getAttach());
            }
            if (StringUtils.isNotEmpty(data.getBody())) {
                paramters.put("body", data.getBody());
            }
            if (StringUtils.isNotEmpty(data.getDetail())) {
                paramters.put("detail", data.getDetail());
            }
            if (StringUtils.isNotEmpty(data.getDeviceInfo())) {
                paramters.put("device_info", data.getDeviceInfo());
            }
            if (StringUtils.isNotEmpty(data.getFeeType())) {
                paramters.put("fee_type", data.getFeeType());
            }
            if (StringUtils.isNotEmpty(data.getGoodsTag())) {
                paramters.put("goods_tag", data.getGoodsTag());
            }
            if (StringUtils.isNotEmpty(data.getLimitPay())) {
                paramters.put("limit_pay", data.getLimitPay());
            }
            if (StringUtils.isNotEmpty(data.getMchId())) {
                paramters.put("mch_id", data.getMchId());
            }
            if (StringUtils.isNotEmpty(data.getNonceStr())) {
                paramters.put("nonce_str", data.getNonceStr());
            }
            if (StringUtils.isNotEmpty(data.getNotifyUrl())) {
                paramters.put("notify_url", data.getNotifyUrl());
            }
            if (StringUtils.isNotEmpty(data.getOpenId())) {
                paramters.put("openid", data.getOpenId());
            }
            if (StringUtils.isNotEmpty(data.getOutTradeNo())) {
                paramters.put("out_trade_no", data.getOutTradeNo());
            }
            if (StringUtils.isNotEmpty(data.getSign())) {
                paramters.put("sign", data.getSign());
            }
            if (StringUtils.isNotEmpty(data.getSpBillCreateIp())) {
                paramters.put("spbill_create_ip", data.getSpBillCreateIp());
            }
            if (StringUtils.isNotEmpty(data.getTimeStart())) {
                paramters.put("time_start", data.getTimeStart());
            }
            if (StringUtils.isNotEmpty(data.getTimeExpire())) {
                paramters.put("time_expire", data.getTimeExpire());
            }
            if (StringUtils.isNotEmpty(data.getProductId())) {
                paramters.put("product_id", data.getProductId());
            }
            if (data.getTotalFee() > 0) {
                paramters.put("total_fee", String.valueOf(data.getTotalFee()));
            }
            if (StringUtils.isNotEmpty(data.getTradeType())) {
                paramters.put("trade_type", data.getTradeType());
            }
        }
        return paramters;
    }

    public static SortedMap<String, String> buildParamMap(WxRefundRequestData data) {
        SortedMap<String, String> paramters = new TreeMap<String, String>();
        if (null != data) {
            if (StringUtils.isNotEmpty(data.getAppId())) {
                paramters.put("appid", data.getAppId());
            }
            if (StringUtils.isNotEmpty(data.getMchId())) {
                paramters.put("mch_id", data.getMchId());
            }
            if (StringUtils.isNotEmpty(data.getNonceStr())) {
                paramters.put("nonce_str", data.getNonceStr());
            }
            if (StringUtils.isNotEmpty(data.getNotifyUrl())) {
                paramters.put("notify_url", data.getNotifyUrl());
            }
            if (StringUtils.isNotEmpty(data.getOutTradeNo())) {
                paramters.put("out_trade_no", data.getOutTradeNo());
            }
            if (StringUtils.isNotEmpty(data.getSign())) {
                paramters.put("sign", data.getSign());
            }
            if (StringUtils.isNotEmpty(data.getOutRefundNo())) {
                paramters.put("out_refund_no", data.getOutRefundNo());
            }
            if (StringUtils.isNotEmpty(data.getTransactionId())) {
                paramters.put("transaction_id", data.getTransactionId());
            }
            if (data.getRefundFee() != null) {
                paramters.put("refund_fee", String.valueOf(data.getRefundFee()));
            }
            if (StringUtils.isNotEmpty(data.getRefundDesc())) {
                paramters.put("refund_desc", data.getRefundDesc());
            }
            if (data.getTotalFee() > 0) {
                paramters.put("total_fee", String.valueOf(data.getTotalFee()));
            }
            if (StringUtils.isNotEmpty(data.getRefundAccount())) {
                paramters.put("refund_account", data.getRefundAccount());
            }
        }
        return paramters;
    }
}
