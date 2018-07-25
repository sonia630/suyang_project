package com.o2o.massage.core.constants;

import java.math.BigDecimal;
import java.util.Arrays;

/**
 * @Author: zhongli
 * @Date: 2018/3/21 13:55
 * @Description:
 */
public enum EnumPayWay {
    WeChat(1, "微信支付", 0d),
    RedPacket(2, "红包支付", 0d);

    EnumPayWay(Integer code, String name, double feeRate) {
        this.code = code;
        this.name = name;
        this.feeRate = BigDecimal.valueOf(feeRate);
    }

    private Integer code;
    private String name;
    private BigDecimal feeRate;

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getFeeRate() {
        return feeRate;
    }

    public static EnumPayWay parseByCode(Integer code) {
        return Arrays.stream(EnumPayWay.values()).filter(a -> a.getCode().equals(code)).findFirst().orElse(null);
    }
}
