package com.o2o.massage.order.statemachine;

import com.o2o.massage.core.constants.EnumOrderCancelReason;
import com.o2o.massage.core.constants.OrderStatus;
import com.o2o.massage.core.exception.OperationNotAllowedException;
import com.o2o.massage.dao.entity.OrderInfo;
import com.o2o.massage.dao.entity.PaymentRecord;
import com.o2o.massage.order.OrderManager;
import com.o2o.massage.user.CustomerEntityWrapper;
import com.o2o.massage.user.ProviderEntityWrapper;
import com.o2o.massage.wechat.entity.WxPayResultNotifyEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Author: zhongli
 * @Date: 2018/3/22 20:27
 * @Description:
 */
public abstract class AbstractOrderState implements IOrderState {

    protected Logger logger= LoggerFactory.getLogger(getClass());

    @Resource
    protected OrderManager orderManager;

    @Override
    public void onProviderConfirm(OrderInfo orderInfo, ProviderEntityWrapper providerWrapper) {
        throw new OperationNotAllowedException();
    }

    @Override
    public void onProviderDeny(OrderInfo orderInfo, ProviderEntityWrapper providerWrapper) {
        throw new OperationNotAllowedException();
    }

    @Override
    public void onProviderConfirmTimeout(OrderInfo orderInfo) {
        throw new OperationNotAllowedException();
    }

    @Override
    public void onCustomerCancel(OrderInfo orderInfo, CustomerEntityWrapper customerWrapper) {
        throw new OperationNotAllowedException();
    }

    @Override
    public void onCustomerPayTimeout(OrderInfo orderInfo) {
        throw new OperationNotAllowedException();
    }

    @Override
    public void onProviderCancel(OrderInfo orderInfo, ProviderEntityWrapper providerWrapper) {
        throw new OperationNotAllowedException();
    }

    @Override
    public void onProviderServiceStart(OrderInfo orderInfo, ProviderEntityWrapper providerWrapper) {
        throw new OperationNotAllowedException();
    }

    @Override
    public void onProviderServiceFinish(OrderInfo orderInfo, ProviderEntityWrapper providerWrapper) {
        throw new OperationNotAllowedException();
    }

    @Override
    public void onProviderGrab(OrderInfo orderInfo, ProviderEntityWrapper providerWrapper) {

    }

    @Override
    public void onCustomerConfirm(OrderInfo orderInfo, CustomerEntityWrapper customerWrapper) {
        throw new OperationNotAllowedException();
    }

    @Override
    public void onCustomerDeny(OrderInfo orderInfo, CustomerEntityWrapper customerWrapper) {
        throw new OperationNotAllowedException();
    }

    protected void doCustomerSimpleCancel(OrderInfo orderInfo, CustomerEntityWrapper entityWrapper) {
        OrderInfo memOrderInfo = new OrderInfo();
        memOrderInfo.setUpdateBy(entityWrapper.getBaseInfo().getUserId());
        memOrderInfo.setUpdateTime(new Date());
        memOrderInfo.setCancelTime(new Date());
        memOrderInfo.setCancelReason(EnumOrderCancelReason.CanceledByCustomer.getCode().byteValue());
        memOrderInfo.setOrderStatus((byte) OrderStatus.CANCELLED.code());
        orderManager.updateOrder(orderInfo.getOrderId(), memOrderInfo,
                orderInfo.getVersion(), true);
    }

    protected void doChangeOrderBookTime(OrderInfo orderInfo, Date startDate,Date endDate,String updateBy) {
        OrderInfo memOrderInfo = new OrderInfo();
        memOrderInfo.setUpdateBy(updateBy);
        memOrderInfo.setUpdateTime(new Date());
        memOrderInfo.setBookStartTime(startDate);
        memOrderInfo.setBookEndTime(endDate);
        orderManager.updateOrder(orderInfo.getOrderId(), memOrderInfo,
                orderInfo.getVersion(), true);
    }

    @Override
    public void onWxPayResult(OrderInfo orderInfo, PaymentRecord paymentRecord, WxPayResultNotifyEntity notifyEntity) {
        throw new OperationNotAllowedException();
    }

    @Override
    public void onProviderDepart(OrderInfo orderInfo){
        throw new OperationNotAllowedException();
    }

    @Override
    public void onChangeOrderBookTime(OrderInfo orderInfo, Date startDate,Date endDate,String updateBy){
        throw new OperationNotAllowedException();
    }
}
