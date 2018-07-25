package com.o2o.massage.core.common;

/**
 * Created by lawrence on 16-12-27.
 */
public class WebRespResult<T> {

    public static final int SUCCESS = 0;
    public static final int ERROR = -1;
    public static final String SYSTEM_ERROR_MESSAGE = "Runtime unknown internal error.";

    // WebRespResult
    private int code = SUCCESS;
    //private String status = "";
    private String desc = "success";

    // 返回json时一般使用t装填返回数据；其他类型（如XML），继承本类自行添加属性
    private T data;

    public WebRespResult() {
    }

    public WebRespResult(int resultCode, String resultMessage) {
        code = resultCode;
        setDesc(resultMessage);
    }

    public void setError(int resultCode, String resultMessage) {
        code = resultCode;
        setDesc(resultMessage);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    /*public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
*/
    @Override
    public String toString() {
        return "WebRespResult{" +
                "code=" + code +
                ", desc='" + desc + '\'' +
                ", data=" + data +
                '}';
    }
}
