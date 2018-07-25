package com.o2o.massage.core.exception;

/**
 * 特卖服务
 */
public enum BizExceptionCode {

    BLANK_PARAMETER(101, "传入参数为空"),

    INVALID_PARAMETER(102, "传入参数有误"),

    TOKEN_EXPIRED(103, "Token无效或已过期"),

    USER_IDENTITY_INVALID(104, "用户不具备对应权限"),

    ORDER_OPERATION_NOT_SUPPORTED(105, "当前状态无法执行该操作"),

    DATA_VERSION_ALREADY_CHANGED(106, "数据版本已经发生改变"),

    ORDER_NOT_FOUND(107, "订单不存在"),

    UNKNOWN(999, "抱歉，系统不太给力，请稍后再试。:(");

    private int value;

    private String title;

    BizExceptionCode(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public int getValue() {
        return value;
    }

    public String getTitle() {
        return this.title;
    }

    public static BizExceptionCode findByCode(int errCode) {
        for (BizExceptionCode code : values()) {
            if (code.getValue() == errCode) {
                return code;
            }
        }
        return UNKNOWN;
    }
}
