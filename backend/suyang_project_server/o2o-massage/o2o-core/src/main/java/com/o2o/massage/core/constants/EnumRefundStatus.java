
package com.o2o.massage.core.constants;

import java.util.Arrays;

/**
 * Created by yuhong on 2017/8/8.
 */
public enum EnumRefundStatus {

    WAITING(100, "待退款"),
    ONGOING(101, "退款中"),
    SUCCESS(102, "退款成功"),
    FAILED(103, "退款失败"),
    CANCEL(104, "取消退款");

    EnumRefundStatus(Integer code, String name) {
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

    public static EnumRefundStatus getByCode(Integer code) {
        return Arrays.stream(EnumRefundStatus.values()).filter(a -> a.getCode().equals(code)).findFirst().orElse(null);

    }
}
