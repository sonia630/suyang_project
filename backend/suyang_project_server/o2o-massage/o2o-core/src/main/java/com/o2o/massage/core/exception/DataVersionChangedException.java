package com.o2o.massage.core.exception;

/**
 * @Author: zhongli
 * @Date: 2018/3/26 17:31
 * @Description:
 */
public class DataVersionChangedException extends BizException{

    public DataVersionChangedException() {
        super(BizExceptionCode.DATA_VERSION_ALREADY_CHANGED.getValue(),
                BizExceptionCode.DATA_VERSION_ALREADY_CHANGED.getTitle());
    }

}
