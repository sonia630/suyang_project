package com.o2o.massage.order.statemachine;

import com.o2o.massage.dao.entity.OrderInfo;
import com.o2o.massage.order.EnumOrderAction;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: zhongli
 * @Date: 2018/4/13 12:24
 * @Description:
 */
@Component
public class CancelledStateImpl extends AbstractOrderState{

    @Override
    public List<EnumOrderAction> providerActions(OrderInfo orderInfo) {
        return null;
    }

    @Override
    public List<EnumOrderAction> customerActions(OrderInfo orderInfo) {
        return null;
    }
}
