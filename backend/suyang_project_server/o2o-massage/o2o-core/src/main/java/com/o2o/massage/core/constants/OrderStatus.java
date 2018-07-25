package com.o2o.massage.core.constants;

import java.util.Arrays;

/**
 * @Author: zhongli
 * @Date: 2018/3/10 18:14
 * @Description:
 */
public enum OrderStatus {

    WAIT_PROVIDER_CONFIRM(1, "待确认"),

    WAIT_PAY(2, "待支付"),

    WAIT_SERVICE(3, "待服务"),

    SERVICE_ING(4, "服务中"),

    DONE(5, "已完成"),

    CANCELLED(6, "已取消"),

    WAIT_GRAB(7, "待抢单"),

    WAIT_CUSTOMER_CONFIRM(8, "待用户确认"),

    WAIT_COMPLETE_HEALTH_RECORD(9, "待补充病案");

    private int code;
    private String title;

    OrderStatus(int code, String title) {
        this.code = code;
        this.title = title;
    }

    public static String getTitle(int code) {
        for (OrderStatus orderStatus : values()) {
            if (orderStatus.code == code) {
                return orderStatus.title;
            }
        }
        return null;
    }

    public String title() {
        return this.title;
    }

    public int code() {
        return this.code;
    }

    public static OrderStatus getByCode(int code) {
        return Arrays.stream(OrderStatus.values()).filter(a -> a.code() == (code)).findFirst().orElse(null);
    }
}
