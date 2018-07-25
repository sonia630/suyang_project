package com.o2o.massage.order.statemachine;

import com.o2o.massage.core.constants.EnumOrderCommentStatus;
import com.o2o.massage.core.constants.OrderStatus;
import com.o2o.massage.dao.entity.OrderInfo;
import com.o2o.massage.order.EnumOrderAction;
import com.o2o.massage.user.ProviderEntityWrapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: zhongli
 * @Date: 2018/3/22 20:52
 * @Description:
 */
@Component
public class ServicingStateImpl extends AbstractOrderState {

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
        return actions;
    }

    @Override
    public void onProviderServiceFinish(OrderInfo orderInfo, ProviderEntityWrapper providerWrapper) {
        OrderInfo memOrderInfo = new OrderInfo();
        memOrderInfo.setOrderStatus((byte) OrderStatus.DONE.code());
        memOrderInfo.setRealServStartTime(new Date());
        memOrderInfo.setUpdateBy(providerWrapper.getBaseInfo().getUserId());
        memOrderInfo.setCommentStatus((byte) EnumOrderCommentStatus.WaitComment.getValue());
        orderManager.updateOrder(orderInfo.getOrderId(), memOrderInfo, orderInfo.getVersion(), true);
    }

}
