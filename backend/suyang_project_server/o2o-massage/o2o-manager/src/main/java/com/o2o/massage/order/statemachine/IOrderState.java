package com.o2o.massage.order.statemachine;

import com.o2o.massage.dao.entity.OrderInfo;
import com.o2o.massage.dao.entity.PaymentRecord;
import com.o2o.massage.order.EnumOrderAction;
import com.o2o.massage.user.CustomerEntityWrapper;
import com.o2o.massage.user.ProviderEntityWrapper;
import com.o2o.massage.wechat.entity.WxPayResultNotifyEntity;

import java.util.Date;
import java.util.List;

/**
 * @Author: zhongli
 * @Date: 2018/3/25 12:37
 * @Description:
 */
public interface IOrderState {
    /**
     * 当前状态推拿师可执行操作
     *
     * @param orderInfo
     * @return
     */
    List<EnumOrderAction> providerActions(OrderInfo orderInfo);

    /**
     * 当前状态客户可执行操作
     *
     * @param orderInfo
     * @return
     */
    List<EnumOrderAction> customerActions(OrderInfo orderInfo);

    /**
     * 推拿师确认
     *
     * @param orderInfo
     * @param providerWrapper
     */
    void onProviderConfirm(OrderInfo orderInfo, ProviderEntityWrapper providerWrapper);

    /**
     * 推拿师拒绝
     *
     * @param orderInfo
     * @param providerWrapper
     */
    void onProviderDeny(OrderInfo orderInfo, ProviderEntityWrapper providerWrapper);

    /**
     * 推拿师确认超时
     *
     * @param orderInfo
     */
    void onProviderConfirmTimeout(OrderInfo orderInfo);

    /**
     * 客户主动取消
     *
     * @param orderInfo
     * @param customerWrapper
     */
    void onCustomerCancel(OrderInfo orderInfo, CustomerEntityWrapper customerWrapper);

    /**
     * @param orderInfo
     * @param providerWrapper
     */
    void onProviderCancel(OrderInfo orderInfo, ProviderEntityWrapper providerWrapper);

    /**
     * @param orderInfo
     */
    void onProviderDepart(OrderInfo orderInfo);

    /**
     * @param orderInfo
     * @param providerWrapper
     */
    void onProviderServiceStart(OrderInfo orderInfo, ProviderEntityWrapper providerWrapper);

    /**
     * @param orderInfo
     * @param providerWrapper
     */
    void onProviderServiceFinish(OrderInfo orderInfo, ProviderEntityWrapper providerWrapper);

    /**
     * @param orderInfo
     * @param providerWrapper
     */
    void onProviderGrab(OrderInfo orderInfo, ProviderEntityWrapper providerWrapper);

    /**
     * @param orderInfo
     * @param customerWrapper
     */
    void onCustomerConfirm(OrderInfo orderInfo, CustomerEntityWrapper customerWrapper);

    /**
     * @param orderInfo
     * @param customerWrapper
     */
    void onCustomerDeny(OrderInfo orderInfo, CustomerEntityWrapper customerWrapper);


    void onWxPayResult(OrderInfo orderInfo, PaymentRecord paymentRecord, WxPayResultNotifyEntity notifyEntity);

    void onCustomerPayTimeout(OrderInfo orderInfo);

    void onChangeOrderBookTime(OrderInfo orderInfo, Date startDate,Date endDate,String updateBy);

}