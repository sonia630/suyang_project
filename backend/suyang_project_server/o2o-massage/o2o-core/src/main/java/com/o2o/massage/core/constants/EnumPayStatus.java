package com.o2o.massage.core.constants;

import java.util.Arrays;

/**
 * 支付流水状态
 */
public enum EnumPayStatus {

    UNPAID(200, "未支付"),
    SUCCESS(201, "支付成功");

    EnumPayStatus(Integer code, String name) {
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

    public static EnumPayStatus getByCode(Integer code) {
        return Arrays.stream(EnumPayStatus.values()).filter(a -> a.getCode().equals(code)).findFirst().orElse(null);

    }
}
