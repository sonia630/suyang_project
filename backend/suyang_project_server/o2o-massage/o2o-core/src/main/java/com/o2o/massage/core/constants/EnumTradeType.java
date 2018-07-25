package com.o2o.massage.core.constants;

import java.util.Arrays;

/**
 *
 */
public enum EnumTradeType {
    CONSUMPTION(1, "消费"),
    RECHARGE(2, "充值");

    EnumTradeType(Integer code, String name) {
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

    public static EnumTradeType parseByCode(Integer code) {
        return Arrays.stream(EnumTradeType.values()).filter(a -> a.getCode().equals(code)).findFirst().orElse(null);
    }
}
