package com.o2o.massage.core.constants;

import java.util.Arrays;

/**
 * 支付单状态
 */
public enum EnumPayTradeStatus {
    NONE(0, ""),
    WAITING(100, "待支付"),
    PARTIAL(101, "部分支付"),
    SUCCESS(102, "交易成功"),
    CANCEL(103, "交易取消");

    EnumPayTradeStatus(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    private Integer code;
    private String name;

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static EnumPayTradeStatus parseByCode(Integer code) {
        return Arrays.stream(EnumPayTradeStatus.values()).filter(a -> a.getCode().equals(code)).findFirst().orElse(null);
    }
}
