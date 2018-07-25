package com.o2o.massage.order;

import java.util.Arrays;

/**
 * @Author: zhongli
 * @Date: 2018/3/23 10:37
 * @Description:
 */
public enum EnumOrderAction {
    ProviderConfirm("provider_confirm", "确认"),
    ProviderDeny("provider_deny", "拒绝"),
    UserCancel("user_cancel", "取消"),
    ProviderCancel("provider_cancel", "取消"),
    UserPay("user_pay", "去支付"),
    ProviderDepart("provider_depart", "出发"),
    ServiceStart("service_start", "确认服务开始"),
    ServiceFinish("service_finish", "确认服务完成"),
    Comment("comment_add", "点评"),
    CommentDetail("comment_detail", "查看点评"),
    ChangeBookTime("change_book_time", "修改预约时间"),

    UserConfirm("user_confirm", "确认"),
    UserDeny("user_deny", "拒绝");

    private final String code;
    private final String desc;

    public String getCode() {
        return this.code;
    }

    public String getDesc() {
        return this.desc;
    }

    private EnumOrderAction(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static EnumOrderAction getByCode(String code) {
        return Arrays.stream(values()).filter((a) -> {
            return a.getCode().equals(code);
        }).findFirst().orElse(null);
    }
}
