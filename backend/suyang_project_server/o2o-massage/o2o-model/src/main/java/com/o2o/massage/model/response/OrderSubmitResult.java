package com.o2o.massage.model.response;

import lombok.Data;

/**
 * @Author: zhongli
 * @Date: 2018/3/4 16:14
 * @Description:
 */
@Data
public class OrderSubmitResult {
    private String orderNo;

    public OrderSubmitResult(String orderNo) {
        this.orderNo = orderNo;
    }

}
