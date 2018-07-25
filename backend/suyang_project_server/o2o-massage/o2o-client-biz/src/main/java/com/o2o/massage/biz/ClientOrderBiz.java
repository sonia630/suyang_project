package com.o2o.massage.biz;

import com.o2o.massage.core.common.ApiRequestContext;
import com.o2o.massage.model.request.EvaluationRequest;
import com.o2o.massage.model.request.OrderChangeBookTimeRequest;
import com.o2o.massage.model.request.OrderFormRequest;
import com.o2o.massage.model.request.OrderSubmitRequest;
import com.o2o.massage.model.response.CustomerOrderDetailResult;
import com.o2o.massage.model.response.OrderFormResult;
import com.o2o.massage.model.response.OrderSubmitResult;
import com.o2o.massage.model.response.ProviderOrderDetailResult;
import com.o2o.massage.wechat.entity.WxPayResultNotifyEntity;
import com.o2o.nm.bo.OrderEvaluation;
import com.o2o.nm.bo.OrderListItem;
import com.o2o.nm.bo.ProviderOrderListItem;
import com.o2o.nm.bo.ProviderProgramOrderInfo;
import com.o2o.nm.vo.TimeRangeVo;

import java.util.Date;
import java.util.List;

/**
 * @Author: zhongli
 * @Date: 2018/3/6 17:48
 * @Description:
 */
public interface ClientOrderBiz {
    /**
     * 填单页
     */
    OrderFormResult orderForm(OrderFormRequest request);

    /**
     * 选中推拿师提交
     */
    OrderSubmitResult orderSubmit(OrderSubmitRequest request, ApiRequestContext apiRequestContext);

    /**
     * 抢单提交
     */
    public OrderSubmitResult orderSubmitNoProvider(OrderSubmitRequest request, ApiRequestContext requestContext);

    CustomerOrderDetailResult getOrderDetailOrderNo(String orderNo);

    ProviderOrderDetailResult getProviderOrderDetail(String orderNo);

    OrderEvaluation getOrderDetailOfEvaluation(String orderNo);

    List<OrderListItem> getCustomerOrders(String userId, Byte[] orderStatus, int page, int count);

    long getCustomerOrderCount(String userId, Byte[] orderStatus);

    List<ProviderOrderListItem> getProviderOrders(String userId, Byte[] orderStatus, int page, int count);

    long getProviderOrderCount(String userId, Byte[] orderStatus);

    void saveEvaluation(EvaluationRequest evaluationRequest);

    /**
     * 用户取消
     * @param userId
     * @param orderNo
     */
    void cancelByCustomer(String userId,String orderNo);

    /**
     * 推拿师取消
     * @param userId
     * @param orderNo
     */
    void cancelByProvider(String userId,String orderNo);

    /**
     * 推拿师确认
     * @param userId
     * @param orderNo
     */
    void providerConfirm(String userId,String orderNo);

    /**
     * 推拿师拒绝
     * @param userId
     * @param orderNo
     */
    void providerDeny(String userId,String orderNo);

    /**
     * 推拿师出发
     * @param userId
     * @param orderNo
     */
    void providerDepart(String userId,String orderNo);

    /**
     * 服务开始
     * @param userId
     * @param orderNo
     */
    void providerServiceStart(String userId,String orderNo);

    /**
     * 服务结束
     * @param userId
     * @param orderNo
     */
    void providerServiceFinish(String userId,String orderNo);

    /**
     * 支付通知
     * @param notifyEntity
     */
    void paymentNotify(WxPayResultNotifyEntity notifyEntity);

    void paymentTimeout(String orderNo);

    void providerConfirmTimeout(String orderNo);

    void changeOrderBookTime(String userId, OrderChangeBookTimeRequest changeBookTimeRequest);

    List<TimeRangeVo> getProgram(String providerUserId, Date start, Date end);

    ProviderProgramOrderInfo getProviderProgramOrderInfo(String orderNo);
}
