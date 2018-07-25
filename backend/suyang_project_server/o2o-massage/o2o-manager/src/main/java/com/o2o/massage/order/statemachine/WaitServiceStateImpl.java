package com.o2o.massage.order.statemachine;

import com.o2o.massage.builder.WxPayDataBuilder;
import com.o2o.massage.core.constants.EnumPayStatus;
import com.o2o.massage.core.constants.EnumPayWay;
import com.o2o.massage.core.constants.EnumRefundWay;
import com.o2o.massage.core.constants.OrderStatus;
import com.o2o.massage.core.exception.BizException;
import com.o2o.massage.core.utils.RandomUUID;
import com.o2o.massage.core.utils.SnowFlake;
import com.o2o.massage.dao.component.PaymentDao;
import com.o2o.massage.dao.entity.OrderInfo;
import com.o2o.massage.dao.entity.PaymentOrder;
import com.o2o.massage.dao.entity.PaymentRecord;
import com.o2o.massage.dao.entity.RefundRecord;
import com.o2o.massage.order.EnumOrderAction;
import com.o2o.massage.payment.PaymentManager;
import com.o2o.massage.user.CustomerEntityWrapper;
import com.o2o.massage.user.ProviderEntityWrapper;
import com.o2o.massage.wechat.WeChatConfig;
import com.o2o.massage.wechat.WeChatConstants;
import com.o2o.massage.wechat.WeChatHelper;
import com.o2o.massage.wechat.WeChatPaymentManager;
import com.o2o.massage.wechat.entity.WxRefundRequestData;
import com.o2o.massage.wechat.entity.WxRefundResult;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * @Author: zhongli
 * @Date: 2018/3/22 20:47
 * @Description:
 */
@Component
public class WaitServiceStateImpl extends AbstractOrderState {

    @Resource
    private PaymentDao paymentDao;
    @Resource
    private PaymentManager paymentManager;
    @Resource
    private WeChatPaymentManager weChatPaymentManager;


    @Override
    public List<EnumOrderAction> providerActions(OrderInfo orderInfo) {
        List<EnumOrderAction> actions = new ArrayList<>();
        if (orderInfo.getDepartureTime() == null) {
            actions.add(EnumOrderAction.ProviderDepart);
        } else {
            actions.add(EnumOrderAction.ServiceStart);
        }

        return actions;
    }

    @Override
    public List<EnumOrderAction> customerActions(OrderInfo orderInfo) {
        List<EnumOrderAction> actions = new ArrayList<>();
        if (orderInfo.getDepartureTime() == null) {
            actions.add(EnumOrderAction.UserCancel);
        }
        boolean sameDay=DateUtils.isSameDay(new Date(),orderInfo.getBookStartTime());
        if(!sameDay){
            actions.add(EnumOrderAction.ChangeBookTime);
        }
        return actions;
    }

    @Override
    public void onCustomerCancel(OrderInfo orderInfo, CustomerEntityWrapper customerWrapper) {
        if (orderInfo.getDepartureTime() != null) {
            throw new BizException("推拿师已经出发,无法取消");
        }
        if (orderInfo.getTotalAmount().compareTo(BigDecimal.ZERO) > 0) {
            PaymentOrder paymentOrder = paymentDao.queryOnePaymentOrderByBizOrderNo(orderInfo.getOrderNo());
            List<PaymentRecord> paymentRecords = paymentDao.queryPaymentRecordList(paymentOrder.getPayTradeNo());
            Optional<PaymentRecord> recordOptional = paymentRecords.stream().filter(obj ->
                    obj.getPayWayNo().byteValue() == EnumPayWay.WeChat.getCode()
                            && obj.getPayStatus().byteValue() == EnumPayStatus.SUCCESS.getCode()).findFirst();
            if (recordOptional.isPresent()) {
                PaymentRecord originPayRecord = recordOptional.get();
                BigDecimal refundAmount = originPayRecord.getPayAmount().subtract(orderInfo.getJourneyFee());
                if (refundAmount.compareTo(BigDecimal.ZERO) > 0) {
                    String refundTransNo = String.valueOf(SnowFlake.getInstance().nextId());

                    PaymentRecord memPayRecord = new PaymentRecord();
                    memPayRecord.setHasRefund((byte) 1);
                    memPayRecord.setRefundAmount(refundAmount);
                    memPayRecord.setUpdateTime(new Date());
                    paymentManager.updatePayRecord(originPayRecord.getId(),
                            memPayRecord, originPayRecord.getVersion(), true);

                    RefundRecord refundRecord = new RefundRecord();
                    refundRecord.setBizOrderNo(orderInfo.getOrderNo());
                    refundRecord.setCustomerName(orderInfo.getCustomerName());
                    refundRecord.setCustomerUserId(orderInfo.getCustomerUserId());
                    refundRecord.setTransactionNo(refundTransNo);
                    refundRecord.setOrginTransactionNo(originPayRecord.getTransactionNo());
                    refundRecord.setOrginOutTransactionNo(originPayRecord.getOutTransactionNo());
                    refundRecord.setPaymentOrderId(paymentOrder.getId());
                    refundRecord.setPayAmount(originPayRecord.getPayAmount());
                    refundRecord.setPayTradeNo(paymentOrder.getPayTradeNo());
                    refundRecord.setRefundAmount(refundAmount);
                    refundRecord.setRefundWay(EnumRefundWay.SameWay.getCode().byteValue());
                    refundRecord.setCreateTime(new Date());
                    paymentDao.insertRefundRecord(refundRecord);

                    WxRefundRequestData wxRefundRequestData = new WxRefundRequestData();
                    wxRefundRequestData.setAppId(WeChatConfig.getAppId());
                    wxRefundRequestData.setMchId(WeChatConfig.getMchId());
                    wxRefundRequestData.setNonceStr(RandomUUID.withoutDash());
                    wxRefundRequestData.setTotalFee(originPayRecord.getPayAmount().multiply(BigDecimal.valueOf(100)).intValue());
                    wxRefundRequestData.setOutTradeNo(originPayRecord.getOutTradeNo());
                    wxRefundRequestData.setNotifyUrl(WeChatConfig.getNofityUrl());
                    wxRefundRequestData.setOutRefundNo(refundTransNo);
                    wxRefundRequestData.setRefundFee(refundAmount.multiply(BigDecimal.valueOf(100)).intValue());
                    wxRefundRequestData.setTransactionId(originPayRecord.getOutTransactionNo());
                    Map<String, String> paraMap = WxPayDataBuilder.buildParamMap(wxRefundRequestData);
                    String sign = WeChatHelper.getSign(paraMap, WeChatConfig.getKey());
                    wxRefundRequestData.setSign(sign);
                    WxRefundResult wxRefundResult = weChatPaymentManager.wxRefund(wxRefundRequestData);
                    if (wxRefundResult == null ||
                            !WeChatConstants.RETURN_SUCCESS.equalsIgnoreCase(wxRefundResult.getReturnCode()) ||
                            !WeChatConstants.RETURN_SUCCESS.equalsIgnoreCase(wxRefundResult.getResultCode())) {
                        throw new BizException("微信退款异常:" + wxRefundResult.getReturnMsg());
                    }
                }
            }
        }
        doCustomerSimpleCancel(orderInfo, customerWrapper);
    }

    @Override
    public void onProviderDepart(OrderInfo orderInfo) {
        if (orderInfo.getDepartureTime() != null) {
            return;
        }
        OrderInfo memOrderInfo = new OrderInfo();
        memOrderInfo.setUpdateBy(orderInfo.getProviderUserId());
        memOrderInfo.setUpdateTime(new Date());
        memOrderInfo.setDepartureTime(new Date());
        orderManager.updateOrder(orderInfo.getOrderId(), memOrderInfo,
                orderInfo.getVersion(), true);
    }

    @Override
    public void onProviderServiceStart(OrderInfo orderInfo, ProviderEntityWrapper providerWrapper) {
        OrderInfo memOrderInfo = new OrderInfo();
        memOrderInfo.setOrderStatus((byte) OrderStatus.SERVICE_ING.code());
        memOrderInfo.setRealServStartTime(new Date());
        memOrderInfo.setUpdateBy(providerWrapper.getBaseInfo().getUserId());
        orderManager.updateOrder(orderInfo.getOrderId(), memOrderInfo, orderInfo.getVersion(), true);
    }

    @Override
    public void onChangeOrderBookTime(OrderInfo orderInfo, Date startDate,Date endDate,String updateBy){
        boolean sameDay=DateUtils.isSameDay(startDate,orderInfo.getBookStartTime());
        if(sameDay){
            throw new BizException("距离服务时间太近，无法更改预约时间");
        }
        doChangeOrderBookTime(orderInfo,startDate,endDate,updateBy);
    }
}
