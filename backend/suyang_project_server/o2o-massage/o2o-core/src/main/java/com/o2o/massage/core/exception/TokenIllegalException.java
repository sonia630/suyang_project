package com.o2o.massage.core.exception;

/**
 * @Author: zhongli
 * @Date: 2018/2/6 18:19
 * @Description:
 */
public class TokenIllegalException extends BizException {

    public TokenIllegalException(String message) {
        super(BizExceptionCode.TOKEN_EXPIRED.getValue(), message);
    }
}
