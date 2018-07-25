package com.o2o.nm.bo;

import com.o2o.massage.core.constants.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProviderOrderListItem {
    String serviceName;
    String bookStartTime;
    String orderNo;
    int orderStatus;
    String address;

    public String getStatus() {
        return OrderStatus.getTitle(orderStatus);
    }

    public boolean isFinish() {
        return orderStatus == OrderStatus.DONE.code();
    }
}
