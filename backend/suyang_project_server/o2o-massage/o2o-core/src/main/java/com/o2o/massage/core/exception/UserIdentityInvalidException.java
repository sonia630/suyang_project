package com.o2o.massage.core.exception;

/**
 * @Author: zhongli
 * @Date: 2018/2/6 20:32
 * @Description:
 */
public class UserIdentityInvalidException extends BizException{

    public UserIdentityInvalidException(String message) {
        super(BizExceptionCode.TOKEN_EXPIRED.getValue(), message);
    }
}
