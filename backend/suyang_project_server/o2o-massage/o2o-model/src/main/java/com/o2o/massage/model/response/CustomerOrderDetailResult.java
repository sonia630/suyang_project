package com.o2o.massage.model.response;

import com.o2o.massage.model.OrderAction;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Builder
public class CustomerOrderDetailResult {
    String head_pic;
    String provider_name;
    String service_name;
    String address;
    String contact_phone;
    BigDecimal service_price;
    BigDecimal journey_fee;
    BigDecimal total_amount;
    String date;
    long est;
    String order_status;
    int orderStatusCode;
    String serviceId;
    int evaluationId;
    List<OrderAction> orderActions;
}
