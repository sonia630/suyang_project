package com.o2o.massage.order.statemachine;

import com.o2o.massage.core.constants.EnumOrderCancelReason;
import com.o2o.massage.core.constants.EnumPayStatus;
import com.o2o.massage.core.constants.EnumPayTradeStatus;
import com.o2o.massage.core.constants.OrderStatus;
import com.o2o.massage.dao.component.PaymentDao;
import com.o2o.massage.dao.entity.OrderInfo;
import com.o2o.massage.dao.entity.PaymentOrder;
import com.o2o.massage.dao.entity.PaymentRecord;
import com.o2o.massage.order.EnumOrderAction;
import com.o2o.massage.payment.PaymentManager;
import com.o2o.massage.user.CustomerEntityWrapper;
import com.o2o.massage.user.ProviderEntityWrapper;
import com.o2o.massage.wechat.WeChatConstants;
import com.o2o.massage.wechat.entity.WxPayResultNotifyEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: zhongli
 * @Date: 2018/3/22 20:27
 * @Description:
 */
@Component
public class WaitPaymentStateImpl extends AbstractOrderState {

    @Resource
    private PaymentDao paymentDao;

    @Resource
    private PaymentManager paymentManager;

    @Override
    public List<EnumOrderAction> providerActions(OrderInfo orderInfo) {
        List<EnumOrderAction> actions = new ArrayList<>();
        actions.add(EnumOrderAction.ProviderCancel);
        return actions;
    }

    @Override
    public List<EnumOrderAction> customerActions(OrderInfo orderInfo) {
        List<EnumOrderAction> actions = new ArrayList<>();
        actions.add(EnumOrderAction.UserPay);
        actions.add(EnumOrderAction.UserCancel);
        return actions;
    }

    @Override
    public void onCustomerCancel(OrderInfo orderInfo, CustomerEntityWrapper customerWrapper) {
        doCustomerSimpleCancel(orderInfo, customerWrapper);
    }

    @Override
    public void onProviderCancel(OrderInfo orderInfo, ProviderEntityWrapper providerEntityWrapper) {
        OrderInfo memOrderInfo = new OrderInfo();
        memOrderInfo.setUpdateBy(providerEntityWrapper.getBaseInfo().getUserId());
        memOrderInfo.setUpdateTime(new Date());
        memOrderInfo.setCancelTime(new Date());
        memOrderInfo.setCancelReason(EnumOrderCancelReason.CanceledByProvider.getCode().byteValue());
        memOrderInfo.setOrderStatus((byte) OrderStatus.CANCELLED.code());
        orderManager.updateOrder(orderInfo.getOrderId(), memOrderInfo,
                orderInfo.getVersion(), true);
    }

    @Override
    public void onWxPayResult(OrderInfo orderInfo, PaymentRecord paymentRecord, WxPayResultNotifyEntity notifyEntity) {
        if (!StringUtils.equals(notifyEntity.getResultCode(), WeChatConstants.RETURN_SUCCESS)) {
            return;
        }
        if (paymentRecord.getPayStatus().byteValue() == EnumPayStatus.SUCCESS.getCode().byteValue()) {
            logger.info("payment record already success.");
            return;
        }
        double totalFee = Double.parseDouble(notifyEntity.getTotalFee());
        if (paymentRecord.getPayAmount().compareTo(BigDecimal.valueOf(totalFee)) != 0) {
            logger.info("notify amount not same,expect:{},notified:{}",
                    paymentRecord.getPayAmount(), totalFee);
        }
        PaymentRecord memPaymentRecord = new PaymentRecord();
        memPaymentRecord.setId(paymentRecord.getId());
        memPaymentRecord.setPayStatus(EnumPayStatus.SUCCESS.getCode().byteValue());
        memPaymentRecord.setPaidTime(new Date());
        paymentManager.updatePayRecord(paymentRecord.getId(),
                memPaymentRecord, paymentRecord.getVersion(), true);

        PaymentOrder paymentOrder = paymentDao.queryByPayTradeNo(paymentRecord.getPayTradeNo());
        BigDecimal paidAmount = paymentOrder.getOrderPaidAmount().add(paymentRecord.getPayAmount());
        if (paidAmount.compareTo(paymentOrder.getOrderAmount()) == 0) {
            PaymentOrder memPaymentOrder = new PaymentOrder();
            memPaymentOrder.setId(paymentOrder.getId());
            memPaymentOrder.setCompleteTime(new Date());
            memPaymentOrder.setOrderPaidAmount(paidAmount);
            memPaymentOrder.setUpdateTime(new Date());
            memPaymentOrder.setPayTradeStatus(EnumPayTradeStatus.SUCCESS.getCode().byteValue());
            paymentManager.updatePaymentOrder(paymentOrder.getId(), memPaymentOrder,
                    paymentOrder.getVersion(), true);

            //更新订单状态
            OrderInfo memOrderInfo = new OrderInfo();
            memOrderInfo.setUpdateTime(new Date());
            memOrderInfo.setOrderStatus((byte) OrderStatus.WAIT_SERVICE.code());
            orderManager.updateOrder(orderInfo.getOrderId(), memOrderInfo, orderInfo.getVersion(), true);
        }
    }

    @Override
    public void onCustomerPayTimeout(OrderInfo orderInfo) {
        OrderInfo memOrderInfo = new OrderInfo();
        memOrderInfo.setUpdateBy("system");
        memOrderInfo.setUpdateTime(new Date());
        memOrderInfo.setCancelTime(new Date());
        memOrderInfo.setCancelReason(EnumOrderCancelReason.UnpaidTimeout.getCode().byteValue());
        memOrderInfo.setOrderStatus((byte) OrderStatus.CANCELLED.code());
        orderManager.updateOrder(orderInfo.getOrderId(), memOrderInfo,
                orderInfo.getVersion(), true);

    }
}
