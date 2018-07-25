package com.o2o.massage.order.statemachine;

import com.o2o.massage.core.component.SpringContextHolder;
import com.o2o.massage.core.constants.OrderStatus;
import com.o2o.massage.core.exception.BizException;
import com.o2o.massage.dao.entity.OrderInfo;

/**
 * @Author: zhongli
 * @Date: 2018/4/13 12:07
 * @Description:
 */
public class OrderStateManager {

    public static IOrderState getOrderState(OrderInfo orderInfo) {
        int orderStatus = orderInfo.getOrderStatus();
        IOrderState bean = getOrderState(orderStatus);
        return bean;
    }

    public static IOrderState getOrderState(int orderStatus) {
        OrderStatus orderStatusEnum = OrderStatus.getByCode(orderStatus);
        IOrderState bean = null;
        switch (orderStatusEnum) {
            case WAIT_PAY:
                bean = SpringContextHolder.getBean(WaitPaymentStateImpl.class);
                break;
            case DONE:
                bean = SpringContextHolder.getBean(CompleteStateImpl.class);
                break;
            case SERVICE_ING:
                bean = SpringContextHolder.getBean(ServicingStateImpl.class);
                break;
            case WAIT_PROVIDER_CONFIRM:
                bean = SpringContextHolder.getBean(WaitProviderConfirmStateImpl.class);
                break;
            case WAIT_SERVICE:
                bean = SpringContextHolder.getBean(WaitServiceStateImpl.class);
                break;
            case CANCELLED:
                bean = SpringContextHolder.getBean(CancelledStateImpl.class);
                break;
        }
        if (bean == null) {
            throw new BizException("No suitable order state found");
        }
        return bean;
    }
}
