package com.o2o.massage.core.constants;

import java.util.Arrays;

/**
 * @Author: zhongli
 * @Date: 2018/3/23 10:23
 * @Description:
 */
public enum EnumOrderCancelReason {
    None(Integer.valueOf(0), "无"),
    UnpaidTimeout(Integer.valueOf(1), "超时未支付"),
    RefusedByProvider(Integer.valueOf(2), "推拿师拒绝"),
    CanceledByCustomer(Integer.valueOf(3), "用户主动取消"),
    ProviderConfirmTimeout(Integer.valueOf(4), "推拿师超时未确认"),
    CanceledByProvider(Integer.valueOf(5), "推拿师主动取消"),
    NoProviderGrab(Integer.valueOf(6), "推拿师无人抢单"),
    Other(Integer.valueOf(100), "其他");

    private final Integer code;
    private final String desc;

    public Integer getCode() {
        return this.code;
    }

    public String getDesc() {
        return this.desc;
    }

    private EnumOrderCancelReason(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static EnumOrderCancelReason getByCode(Integer code) {
        return Arrays.stream(values()).filter((a) -> {
            return a.getCode().equals(code);
        }).findFirst().orElse( null);
    }
}
