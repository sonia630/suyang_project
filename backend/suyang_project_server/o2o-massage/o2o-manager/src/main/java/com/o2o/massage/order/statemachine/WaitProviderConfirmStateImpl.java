package com.o2o.massage.order.statemachine;

import com.o2o.massage.core.constants.EnumOrderCancelReason;
import com.o2o.massage.core.constants.OrderStatus;
import com.o2o.massage.dao.entity.OrderInfo;
import com.o2o.massage.order.EnumOrderAction;
import com.o2o.massage.order.OrderManager;
import com.o2o.massage.user.CustomerEntityWrapper;
import com.o2o.massage.user.ProviderEntityWrapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: zhongli
 * @Date: 2018/3/22 20:26
 * @Description:
 */
@Component
public class WaitProviderConfirmStateImpl extends AbstractOrderState {

    @Resource
    private OrderManager orderManager;

    @Override
    public List<EnumOrderAction> providerActions(OrderInfo orderInfo) {
        List<EnumOrderAction> actions = new ArrayList<>();
        actions.add(EnumOrderAction.ProviderConfirm);
        actions.add(EnumOrderAction.ProviderDeny);
        return actions;
    }

    @Override
    public List<EnumOrderAction> customerActions(OrderInfo orderInfo) {
        List<EnumOrderAction> actions = new ArrayList<>();
        actions.add(EnumOrderAction.UserCancel);
        actions.add(EnumOrderAction.ChangeBookTime);
        return actions;
    }

    @Override
    public void onProviderConfirm(OrderInfo orderInfo, ProviderEntityWrapper providerWrapper) {
        OrderInfo memOrderInfo = new OrderInfo();
        memOrderInfo.setUpdateBy(providerWrapper.getBaseInfo().getUserId());
        memOrderInfo.setUpdateTime(new Date());
        memOrderInfo.setProviderConfirmTime(new Date());
        memOrderInfo.setOrderStatus((byte) OrderStatus.WAIT_PAY.code());
        orderManager.updateOrder(orderInfo.getOrderId(), memOrderInfo,
                orderInfo.getVersion(), true);
    }

    @Override
    public void onProviderDeny(OrderInfo orderInfo, ProviderEntityWrapper providerWrapper) {
        OrderInfo memOrderInfo = new OrderInfo();
        memOrderInfo.setUpdateBy(providerWrapper.getBaseInfo().getUserId());
        memOrderInfo.setUpdateTime(new Date());
        memOrderInfo.setCancelTime(new Date());
        memOrderInfo.setCancelReason(EnumOrderCancelReason.RefusedByProvider.getCode().byteValue());
        memOrderInfo.setOrderStatus((byte) OrderStatus.CANCELLED.code());
        orderManager.updateOrder(orderInfo.getOrderId(), memOrderInfo,
                orderInfo.getVersion(), true);
    }

    @Override
    public void onProviderConfirmTimeout(OrderInfo orderInfo) {
        OrderInfo memOrderInfo = new OrderInfo();
        memOrderInfo.setUpdateBy("system");
        memOrderInfo.setUpdateTime(new Date());
        memOrderInfo.setCancelTime(new Date());
        memOrderInfo.setCancelReason(EnumOrderCancelReason.ProviderConfirmTimeout.getCode().byteValue());
        memOrderInfo.setOrderStatus((byte) OrderStatus.CANCELLED.code());
        orderManager.updateOrder(orderInfo.getOrderId(), memOrderInfo,
                orderInfo.getVersion(), true);
    }

    @Override
    public void onCustomerCancel(OrderInfo orderInfo, CustomerEntityWrapper customerWrapper) {
        doCustomerSimpleCancel(orderInfo, customerWrapper);
    }

    @Override
    public void onChangeOrderBookTime(OrderInfo orderInfo, Date startDate,Date endDate,String updateBy){
        doChangeOrderBookTime(orderInfo,startDate,endDate,updateBy);
    }
}
