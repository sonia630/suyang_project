package com.o2o.massage.core.exception;

import com.o2o.massage.core.common.WebRespResult;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.MessageSource;

import java.util.HashMap;
import java.util.Locale;

/**
 * Created by lawrence on 16-12-27.
 */
public class BizException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * 默认：服务器内部错误
     */
    private int errCode = -1;

    private String key;

    private Object[] args;

    public BizException(Throwable cause) {
        super(cause);
    }

    public BizException(int errCode, Throwable cause) {
        super(cause);
        this.errCode = errCode;
    }

    public BizException(String message) {
        super(message);
    }

    public BizException(int errCode, String message) {
        super(message);
        this.errCode = errCode;
    }

    public BizException(int errCode, String key, Object[] args) {
        super(key);
        this.errCode = errCode;
        this.key = key;
        this.args = args;
    }

    /*public BizException(String key, Object[] args) {
        this.key = key;
        this.args = args;
    }*/

    /**
     * @param errCode 错误码
     * @param message 错误附加信息
     * @param cause Throwable
     */
    public BizException(int errCode, String message, Throwable cause) {
        super(message, cause);
        this.errCode = errCode;
    }

    public BizException(int errCode, String key, Object[] args, Throwable cause) {
        super(key, cause);
        this.key = key;
        this.args = args;
        this.errCode = errCode;
    }

    /**
     * 该异常对应的HTTP 响应码
     *
     * @return
     */
    public int getHttpRespCode() {
        return 200;
    }

    public WebRespResult getErrorResult() {
        WebRespResult respResult = new WebRespResult();
        respResult.setError(errCode, getMessage());
        respResult.setData(new HashMap<String, String>());
        return respResult;
    }

    public WebRespResult getErrorResult(MessageSource messageSource, Locale locale) {
        String description = null;
        if (StringUtils.isNotBlank(key)) {
            description = messageSource.getMessage(key, args,
                    getLocalizedMessage(), locale);
        } else {
            description = getMessage();
        }
        WebRespResult respResult = new WebRespResult();
        respResult.setError(errCode, description);
        respResult.setData(new HashMap<String, String>());
        return respResult;
    }

    public int getErrCode() {
        return errCode;
    }

    public Object[] getArgs() {
        return args;
    }
}
