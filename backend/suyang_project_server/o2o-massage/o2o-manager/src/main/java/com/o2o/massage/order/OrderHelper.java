package com.o2o.massage.order;

import com.o2o.massage.dao.entity.OrderInfo;
import org.apache.commons.lang3.time.DateUtils;

import java.util.Date;

/**
 * @Author: zhongli
 * @Date: 2018/3/21 12:02
 * @Description:
 */
public class OrderHelper {
    public static final int PAYMENT_EXPIRE_MINUTES = 30;

    public static Date getPaymentExpireTime(OrderInfo orderInfo, int minutes) {
        Date expireTime = null;
        if (orderInfo.getCustomerConfirmTime() != null) {
            expireTime = DateUtils.addMinutes(orderInfo.getCustomerConfirmTime(), minutes);
        } else {
            expireTime = DateUtils.addMinutes(orderInfo.getProviderConfirmTime(), minutes);
        }
        return expireTime;
    }
}
