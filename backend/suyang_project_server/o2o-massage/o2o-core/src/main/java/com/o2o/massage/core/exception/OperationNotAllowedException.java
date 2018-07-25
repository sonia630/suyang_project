package com.o2o.massage.core.exception;

/**
 * @Author: zhongli
 * @Date: 2018/3/26 13:11
 * @Description:
 */
public class OperationNotAllowedException extends BizException {

    public OperationNotAllowedException() {
        super(BizExceptionCode.ORDER_OPERATION_NOT_SUPPORTED.getValue(),
                BizExceptionCode.ORDER_OPERATION_NOT_SUPPORTED.getTitle());
    }

    public OperationNotAllowedException(String message) {
        super(BizExceptionCode.ORDER_OPERATION_NOT_SUPPORTED.getValue(), message);
    }

    public OperationNotAllowedException(String userId, String orderNo) {
        super(BizExceptionCode.ORDER_OPERATION_NOT_SUPPORTED.getValue(),
                String.format("用户ID:%s无法操作当前订单:%s", userId, orderNo));
    }


}
