package com.o2o.massage.biz.impl;

import com.google.common.base.Preconditions;
import com.o2o.massage.biz.PaymentBiz;
import com.o2o.massage.builder.PaymentBuilder;
import com.o2o.massage.builder.WxPayDataBuilder;
import com.o2o.massage.core.constants.EnumPayStatus;
import com.o2o.massage.core.constants.EnumPayWay;
import com.o2o.massage.core.constants.OrderStatus;
import com.o2o.massage.core.exception.BizException;
import com.o2o.massage.core.utils.RandomUUID;
import com.o2o.massage.core.utils.SnowFlake;
import com.o2o.massage.dao.component.OrderDao;
import com.o2o.massage.dao.component.PaymentDao;
import com.o2o.massage.dao.entity.OrderInfo;
import com.o2o.massage.dao.entity.PaymentOrder;
import com.o2o.massage.dao.entity.PaymentRecord;
import com.o2o.massage.model.request.PaymentDataRequest;
import com.o2o.massage.model.request.PaymentPrepayRequest;
import com.o2o.massage.model.response.PaymentDataResult;
import com.o2o.massage.model.response.PaymentPrepayResult;
import com.o2o.massage.wechat.*;
import com.o2o.massage.wechat.entity.AuthAccessToken;
import com.o2o.massage.wechat.entity.WxUnifiedOrderData;
import com.o2o.massage.wechat.entity.WxUnifiedResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * @Author: zhongli
 * @Date: 2018/3/20 16:14
 * @Description:
 */
@Service
public class PaymentBizImpl implements PaymentBiz {

    @Resource
    private PaymentDao paymentDao;

    @Resource
    private OrderDao orderDao;

    @Resource
    private WeChatAuthManager weChatAuthManager;

    @Resource
    private WeChatPaymentManager weChatPaymentManager;

    @Override
    @Transactional
    public PaymentDataResult getPaymentData(String fromUid, PaymentDataRequest request) {
        Preconditions.checkArgument(StringUtils.isNotBlank(request.getOrderNo()), "orderNo不得为空");
        PaymentDataResult paymentDataResult = new PaymentDataResult();
        PaymentOrder paymentOrder = paymentDao.queryOnePaymentOrderByBizOrderNo(request.getOrderNo());
        if (paymentOrder != null) {
            if (paymentOrder.getOrderExpireTime().compareTo(new Date()) < 0) {
                throw new BizException("支付时间已过,无法支付");
            }
        } else {
            OrderInfo orderInfo = orderDao.queryByOrderNo(request.getOrderNo());
            if (orderInfo == null || orderInfo.getOrderStatus() != OrderStatus.WAIT_PAY.code()) {
                throw new BizException("订单不存在或状态异常");
            }
            if (StringUtils.equals(fromUid, orderInfo.getCustomerUserId())) {
                throw new BizException("当前用户无权获取对应信息");
            }
            paymentOrder = PaymentBuilder.buildPaymentOrder(orderInfo);
            paymentDao.insertPaymentOrder(paymentOrder);
        }

        paymentDataResult.setPayTradeStatus(paymentOrder.getPayTradeStatus().intValue());
        paymentDataResult.setServiceName(paymentOrder.getServiceName());
        paymentDataResult.setTotalFee(paymentOrder.getOrderAmount());
        paymentDataResult.setToBePaidFee(paymentOrder.getOrderAmount()
                .subtract(paymentOrder.getOrderPaidAmount()));
        paymentDataResult.setPayToken(paymentOrder.getTradeToken());
        return paymentDataResult;
    }

    @Override
    @Transactional
    public PaymentPrepayResult weChatPrepay(PaymentPrepayRequest prepayRequest) {
        Preconditions.checkArgument(StringUtils.isNotBlank(prepayRequest.getCode()), "Auth code不得为空");
        Preconditions.checkArgument(StringUtils.isNotBlank(prepayRequest.getPayToken()), "支付单号不得为空");
        EnumPayWay payWay = EnumPayWay.parseByCode(prepayRequest.getPayWay());
        Preconditions.checkArgument(payWay != null, "请选择合理的支付方式");

        AuthAccessToken authAccessToken = weChatAuthManager.getAuthAccessTokenByCode(prepayRequest.getCode());
        if (authAccessToken == null) {
            throw new BizException("无法获得OpenId");
        }
        PaymentOrder paymentOrder = paymentDao.queryByPayToken(prepayRequest.getPayToken());
        BigDecimal toBePaidAmount = paymentOrder.getOrderAmount()
                .subtract(paymentOrder.getOrderPaidAmount());
        String nonceStr = RandomUUID.withoutDash();
        String outTradeNo = String.valueOf(SnowFlake.getInstance().nextId());
        WxUnifiedOrderData unifiedOrderData = new WxUnifiedOrderData();
        unifiedOrderData.setAppId(WeChatConfig.getAppId());
        unifiedOrderData.setMchId(WeChatConfig.getMchId());
        unifiedOrderData.setDeviceInfo("WEB");
        unifiedOrderData.setNonceStr(nonceStr);
        unifiedOrderData.setBody(paymentOrder.getServiceName());
        unifiedOrderData.setTotalFee(toBePaidAmount.multiply(BigDecimal.valueOf(100)).intValue());
        unifiedOrderData.setOutTradeNo(outTradeNo);
        unifiedOrderData.setSpBillCreateIp(prepayRequest.getUserIP());
        unifiedOrderData.setTradeType(WeChatTradeType.JSAPI.name());
        unifiedOrderData.setOpenId(authAccessToken.getOpenid());
        unifiedOrderData.setNotifyUrl(WeChatConfig.getNofityUrl());
        Map<String, String> paraMap = WxPayDataBuilder.buildParamMap(unifiedOrderData);
        String sign = WeChatHelper.getSign(paraMap, WeChatConfig.getKey());
        unifiedOrderData.setSign(sign);
        WxUnifiedResult unifiedResult = weChatPaymentManager.wxUnifiedOrder(unifiedOrderData);
        if (unifiedResult == null ||
                !WeChatConstants.RETURN_SUCCESS.equalsIgnoreCase(unifiedResult.getReturnCode()) ||
                !WeChatConstants.RETURN_SUCCESS.equalsIgnoreCase(unifiedResult.getResultCode())) {
            throw new BizException("微信支付统一下单异常:" + unifiedResult.getReturnMsg());
        }
        String prepayId = unifiedResult.getPrepayId();
        PaymentRecord paymentRecord = new PaymentRecord();
        paymentRecord.setBizOrderNo(paymentOrder.getBizOrderNo());
        paymentRecord.setPayTradeNo(paymentOrder.getPayTradeNo());
        paymentRecord.setCustomerName(paymentOrder.getCustomerName());
        paymentRecord.setCustomerUserId(paymentOrder.getCustomerUserId());
        paymentRecord.setPaymentOrderId(paymentOrder.getId());
        paymentRecord.setPayWayNo(payWay.getCode().byteValue());
        paymentRecord.setPayWayName(payWay.getName());
        paymentRecord.setFeeRate(payWay.getFeeRate());
        paymentRecord.setOutTradeNo(outTradeNo);
        paymentRecord.setTransactionNo(outTradeNo);
        paymentRecord.setPayAmount(toBePaidAmount);
        paymentRecord.setPayStatus(EnumPayStatus.UNPAID.getCode().byteValue());
        paymentDao.insertPaymentRecord(paymentRecord);

        PaymentPrepayResult prepayResult = new PaymentPrepayResult();
        prepayResult.setAppId(WeChatConfig.getAppId());
        prepayResult.setNonceStr(nonceStr);
        prepayResult.setPackageName("prepay_id=" + prepayId);
        prepayResult.setSignType("MD5");
        prepayResult.setTimeStamp(String.valueOf(System.currentTimeMillis()));
        Map<String, String> stringMap = prepayResult.toParaMap();
        String paySign = WeChatHelper.getSign(stringMap, WeChatConfig.getKey());
        prepayResult.setPaySign(paySign);
        return prepayResult;
    }


}
