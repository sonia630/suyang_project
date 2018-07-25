package com.o2o.massage.order.statemachine;

import com.o2o.massage.core.constants.EnumOrderCommentStatus;
import com.o2o.massage.dao.entity.OrderInfo;
import com.o2o.massage.order.EnumOrderAction;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: zhongli
 * @Date: 2018/3/22 20:52
 * @Description:
 */
@Component
public class CompleteStateImpl extends AbstractOrderState {

    @Override
    public List<EnumOrderAction> providerActions(OrderInfo orderInfo) {
        List<EnumOrderAction> actions = new ArrayList<>();
        if (EnumOrderCommentStatus.CommentDone.getValue() == orderInfo.getCommentStatus()) {
            actions.add(EnumOrderAction.CommentDetail);
        }
        return actions;
    }

    @Override
    public List<EnumOrderAction> customerActions(OrderInfo orderInfo) {
        List<EnumOrderAction> actions = new ArrayList<>();
        EnumOrderCommentStatus currentCommentStatus = EnumOrderCommentStatus.valueOf(orderInfo.getCommentStatus());
        switch (currentCommentStatus) {
            case CommentDone:
                actions.add(EnumOrderAction.CommentDetail);
                break;
            case WaitComment:
                actions.add(EnumOrderAction.Comment);
                break;
        }
        return actions;
    }
}
