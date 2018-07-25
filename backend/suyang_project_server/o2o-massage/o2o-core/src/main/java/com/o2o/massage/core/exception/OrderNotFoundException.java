package com.o2o.massage.core.exception;

/**
 * @Author: zhongli
 * @Date: 2018/4/13 21:30
 * @Description:
 */
public class OrderNotFoundException extends BizException {
    public OrderNotFoundException() {
        super(BizExceptionCode.ORDER_NOT_FOUND.getValue(),
                BizExceptionCode.ORDER_NOT_FOUND.getTitle());
    }

    public OrderNotFoundException(String orderNo) {
        super(BizExceptionCode.ORDER_NOT_FOUND.getValue(), "订单号不存在:" + orderNo);
    }
}
