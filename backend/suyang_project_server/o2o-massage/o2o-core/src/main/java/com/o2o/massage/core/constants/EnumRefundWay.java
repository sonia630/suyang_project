package com.o2o.massage.core.constants;


/**
 * 退款方式
 */
public enum EnumRefundWay {
    SameWay(101, "原路退回"),
    Offline(102, "线下退款");

    private Integer code;
    private String description;

    EnumRefundWay(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    public Integer getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
