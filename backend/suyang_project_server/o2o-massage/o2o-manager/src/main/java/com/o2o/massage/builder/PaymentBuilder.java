package com.o2o.massage.builder;

import com.o2o.massage.core.constants.EnumPayTradeStatus;
import com.o2o.massage.core.constants.EnumTradeType;
import com.o2o.massage.core.utils.SnowFlake;
import com.o2o.massage.dao.entity.OrderInfo;
import com.o2o.massage.dao.entity.PaymentOrder;
import com.o2o.massage.order.OrderHelper;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: zhongli
 * @Date: 2018/3/20 18:41
 * @Description:
 */
public class PaymentBuilder {

    public static PaymentOrder buildPaymentOrder(OrderInfo orderInfo) {
        PaymentOrder paymentOrder = new PaymentOrder();
        paymentOrder.setBizOrderNo(orderInfo.getOrderNo());
        paymentOrder.setCreateTime(new Date());
        paymentOrder.setCustomerUserId(orderInfo.getCustomerUserId());
        paymentOrder.setCustomerName(orderInfo.getCustomerName());
        paymentOrder.setOrderAmount(orderInfo.getTotalAmount());
        paymentOrder.setOrderPaidAmount(BigDecimal.ZERO);
        paymentOrder.setProviderUserId(orderInfo.getProviderUserId());
        paymentOrder.setProviderName(orderInfo.getProviderName());
        paymentOrder.setPayTradeNo(String.valueOf(SnowFlake.getInstance().nextId()));
        boolean needPay = orderInfo.getTotalAmount().compareTo(BigDecimal.ZERO) > 0;
        paymentOrder.setPayTradeStatus(needPay ?
                EnumPayTradeStatus.WAITING.getCode().byteValue() :
                EnumPayTradeStatus.SUCCESS.getCode().byteValue());
        paymentOrder.setOrderPeriod(OrderHelper.PAYMENT_EXPIRE_MINUTES);
        Date orderExpireTime = OrderHelper.getPaymentExpireTime(orderInfo, paymentOrder.getOrderPeriod());
        paymentOrder.setOrderExpireTime(orderExpireTime);
        paymentOrder.setTradeType(EnumTradeType.CONSUMPTION.getCode().byteValue());
        paymentOrder.setVersion(1);
        return paymentOrder;
    }
}
