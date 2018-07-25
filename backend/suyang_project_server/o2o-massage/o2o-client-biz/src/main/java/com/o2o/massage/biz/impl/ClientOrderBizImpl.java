package com.o2o.massage.biz.impl;

import com.google.common.base.Preconditions;
import com.o2o.massage.biz.ClientOrderBiz;
import com.o2o.massage.biz.ClientUserBiz;
import com.o2o.massage.biz.ProviderBiz;
import com.o2o.massage.builder.OrderBuilder;
import com.o2o.massage.builder.OrderInfoDataBuilder;
import com.o2o.massage.builder.SrvTimeBuilder;
import com.o2o.massage.core.common.ApiRequestContext;
import com.o2o.massage.core.constants.OrderStatus;
import com.o2o.massage.core.exception.BizException;
import com.o2o.massage.core.exception.OperationNotAllowedException;
import com.o2o.massage.core.exception.OrderNotFoundException;
import com.o2o.massage.core.utils.SnowFlake;
import com.o2o.massage.dao.component.OrderDao;
import com.o2o.massage.dao.component.ServiceDao;
import com.o2o.massage.dao.entity.*;
import com.o2o.massage.dao.entity.type.DataStatus;
import com.o2o.massage.model.OrderAction;
import com.o2o.massage.model.ProviderDayHourInfo;
import com.o2o.massage.model.ProviderSimpleInfoVO;
import com.o2o.massage.model.ServiceInfoVO;
import com.o2o.massage.model.request.EvaluationRequest;
import com.o2o.massage.model.request.OrderChangeBookTimeRequest;
import com.o2o.massage.model.request.OrderFormRequest;
import com.o2o.massage.model.request.OrderSubmitRequest;
import com.o2o.massage.model.response.CustomerOrderDetailResult;
import com.o2o.massage.model.response.OrderFormResult;
import com.o2o.massage.model.response.OrderSubmitResult;
import com.o2o.massage.model.response.ProviderOrderDetailResult;
import com.o2o.massage.order.EnumOrderAction;
import com.o2o.massage.order.OrderManager;
import com.o2o.massage.order.statemachine.IOrderState;
import com.o2o.massage.order.statemachine.OrderStateManager;
import com.o2o.massage.srv.QServiceInfoWithPrice;
import com.o2o.massage.srv.ServiceManager;
import com.o2o.massage.user.CustomerEntityWrapper;
import com.o2o.massage.user.ProviderEntityWrapper;
import com.o2o.massage.user.UserManager;
import com.o2o.massage.wechat.entity.WxPayResultNotifyEntity;
import com.o2o.nm.bo.*;
import com.o2o.nm.vo.TimeRangeVo;
import de.alpharogroup.jgeohash.distance.DistanceCalculator;
import de.alpharogroup.jgeohash.distance.MeasuringUnit;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author: zhongli
 * @Date: 2018/3/6 18:40
 * @Description:
 */
@Service
public class ClientOrderBizImpl implements ClientOrderBiz {

    @Resource
    private OrderManager orderManager;

    @Resource
    private OrderDao orderDao;

    @Resource
    private ServiceDao serviceDao;

    @Resource
    private UserManager userManager;

    @Resource
    private ServiceManager serviceManager;

    @Autowired
    private ProviderBiz providerBiz;
    @Autowired
    private ClientUserBiz clientUserBiz;

    @Override
    public OrderFormResult orderForm(OrderFormRequest request) {
        OrderFormResult orderFormResult = new OrderFormResult();

        BigDecimal servicePrice = null;
        BigDecimal journeyFee = new BigDecimal(10);//默认10元

        QServiceInfoWithPrice qServiceInfoWithPrice = serviceManager.qryServiceInfoWithDefaultPrice(request.getServiceId());
        ServiceInfo serviceInfo = qServiceInfoWithPrice;
        if (serviceInfo == null || DataStatus.DELETE.getValue() == serviceInfo.getStatus()) {
            throw new BizException("对应服务不存在");
        }

        List<ProviderDayHourInfo> dayFreeHoursList = null;

        servicePrice = qServiceInfoWithPrice.getPrice();
        if (StringUtils.isNotBlank(request.getProviderUserId())) {
            ProviderEntityWrapper providerEntityWrapper = userManager.qryProviderByUserId(request.getProviderUserId());
            if (providerEntityWrapper == null) {
                throw new BizException("对应技师不存在");
            }
            SrvProviderSrvRel srvRel = serviceDao.qrySrvRelByUserIdAndServiceId(request.getProviderUserId(), request.getServiceId());
            if (srvRel == null) {
                throw new BizException("该技师不提供该项服务");
            }
            if (srvRel.getPrice() != null && srvRel.getPrice().doubleValue() > 0) {
                servicePrice = srvRel.getPrice();
            }
            ProviderInfo providerInfo = providerEntityWrapper.getProviderInfo();
            double distanceMove = DistanceCalculator.distance(request.getLatitude(), request.getLongitude(),
                    providerInfo.getLatitude().doubleValue(), providerInfo.getLongitude().doubleValue(), MeasuringUnit.METER);
            journeyFee = new BigDecimal(distanceMove / 1000 * 2).setScale(0, BigDecimal.ROUND_HALF_DOWN);
            ProviderSimpleInfoVO providerSimpleInfoVO = OrderBuilder.buildProviderSimpleInfoVO(providerEntityWrapper);
            orderFormResult.setProvider(providerSimpleInfoVO);
            dayFreeHoursList = orderManager.queryProviderDayHourInfo(request.getProviderUserId(), 7);
        } else {
            dayFreeHoursList = SrvTimeBuilder.getInstance().buildDefaultDayHourInfo(7);
        }

        int serviceCount = 1;
        if (request.getServiceCount() != null && request.getServiceCount() > 1) {
            serviceCount = request.getServiceCount();
        }
        BigDecimal totalFee = servicePrice.multiply(new BigDecimal(serviceCount)).add(journeyFee);

        //orderFormResult.setServicePrice(servicePrice);
        orderFormResult.setJourneyFee(journeyFee);
        orderFormResult.setTotalFee(totalFee);
        ServiceInfoVO serviceInfoVO = OrderBuilder.buildServiceInfoVO(serviceInfo);
        serviceInfoVO.setServicePrice(servicePrice);
        orderFormResult.setServiceInfo(serviceInfoVO);
        orderFormResult.setDayFreeHoursList(dayFreeHoursList);
        return orderFormResult;
    }

    @Override
    @Transactional
    public OrderSubmitResult orderSubmit(OrderSubmitRequest request, ApiRequestContext requestContext) {
        Preconditions.checkArgument(StringUtils.isNotBlank(request.getProviderUserId()), "请选择推拿师");

        int serviceCount = 1;
        if (request.getServiceCount() != null && request.getServiceCount() > 1) {
            serviceCount = request.getServiceCount();
        }

        Date serviceStartTime = request.getServiceStartTime();

        QServiceInfoWithPrice qServiceInfoWithPrice = serviceManager.qryServiceInfoWithDefaultPrice(request.getServiceId());
        ServiceInfo serviceInfo = qServiceInfoWithPrice;
        if (serviceInfo == null || DataStatus.DELETE.getValue() == serviceInfo.getStatus()) {
            throw new BizException("对应服务不存在");
        }

        BigDecimal servicePrice = qServiceInfoWithPrice.getPrice();
        //指定技师下单
        ProviderEntityWrapper providerEntityWrapper = userManager.qryProviderByUserId(request.getProviderUserId());
        if (providerEntityWrapper == null) {
            throw new BizException("对应技师不存在");
        }
        SrvProviderSrvRel srvRel = serviceDao.qrySrvRelByUserIdAndServiceId(request.getProviderUserId(), request.getServiceId());
        if (srvRel == null) {
            throw new BizException("该技师不提供该项服务");
        }
        if (srvRel.getPrice() != null && srvRel.getPrice().doubleValue() > 0) {
            servicePrice = srvRel.getPrice();
        }
        Date serviceEndTime = DateUtils.addMinutes(serviceStartTime, srvRel.getEstimatedTime() * serviceCount);
        boolean hasTime = orderManager.checkProviderHasTimeBetween(request.getProviderUserId(), serviceStartTime, serviceEndTime);
        if (!hasTime) {
            throw new BizException("该技师该时间点无法接单");
        }

//        if (!servicePrice.equals(request.getServicePrice())) {
//            throw new BizException("服务价格与实际定价不一致,无法下单");
//        }

        ProviderInfo providerInfo = providerEntityWrapper.getProviderInfo();
        BigDecimal journeyFee = BigDecimal.ZERO;
        if (providerInfo.getLongitude() != null) {
            double distanceByKm = DistanceCalculator.distance(request.getLatitude(), request.getLongitude(),
                    providerInfo.getLatitude().doubleValue(), providerInfo.getLongitude().doubleValue(), MeasuringUnit.KILOMETER);
            journeyFee = new BigDecimal((int) (distanceByKm * 2));
        }


        BigDecimal totalFee = servicePrice.multiply(new BigDecimal(serviceCount)).add(journeyFee);

        SnowFlake snowFlake = new SnowFlake(0, 0);
        String orderNo = String.valueOf(snowFlake.nextId());
        OrderInfoDataBuilder builder = new OrderInfoDataBuilder();
        //地址信息
        builder.address(request.getAddress()).latitude(new BigDecimal(request.getLatitude()))
                .longitude(new BigDecimal(request.getLongitude()));
        //.cityId(request.getCityId().shortValue());
        //联系人信息
        builder.contactMan(request.getContactMan()).contactPhone(request.getContactPhone())
                .customerUserId(requestContext.getUserId()).customerName(requestContext.getUserName());
        //服务提供者信息
        builder.providerUserId(request.getProviderUserId()).
                providerName(providerEntityWrapper.getBaseInfo().getRealName());
        //产品服务信息
        builder.serviceCount(serviceCount).serviceName(serviceInfo.getServiceName())
                .bookStartTime(serviceStartTime).bookEndTime(serviceEndTime);
        //资费信息
        builder.journeyFee(journeyFee).servicePrice(servicePrice).totalAmount(totalFee);
        OrderInfo orderInfo = builder.orderNo(orderNo)
                .orderStatus((byte) OrderStatus.WAIT_PROVIDER_CONFIRM.code())
                .stateExpireTime(DateUtils.addMinutes(new Date(), 30))
                .serviceId(request.getServiceId()).createTime(new Date()).createBy(requestContext.getUserId())
                .build();
        orderDao.insertOrder(orderInfo);
        providerBiz.updateProviderSchedule(request.getProviderUserId(), requestContext.getUserId(), serviceStartTime, serviceEndTime, orderNo);
        clientUserBiz.updateMemberSympton(orderNo, requestContext.getUserId(), request.getMemberId());
        //TODO provider 乐观锁
        return new OrderSubmitResult(orderNo);
    }

    @Override
    @Transactional
    public OrderSubmitResult orderSubmitNoProvider(OrderSubmitRequest request, ApiRequestContext requestContext) {
        Date serviceStartTime = request.getServiceStartTime();
        BigDecimal journeyFee = new BigDecimal(10);//默认10元

        QServiceInfoWithPrice qServiceInfoWithPrice = serviceManager.qryServiceInfoWithDefaultPrice(request.getServiceId());
        ServiceInfo serviceInfo = qServiceInfoWithPrice;
        if (serviceInfo == null || DataStatus.DELETE.getValue() == serviceInfo.getStatus()) {
            throw new BizException("对应服务不存在");
        }

        int serviceCount = 1;
        if (request.getServiceCount() != null && request.getServiceCount() > 1) {
            serviceCount = request.getServiceCount();
        }

        BigDecimal servicePrice = qServiceInfoWithPrice.getPrice();
        Date serviceEndTime = DateUtils.addMinutes(serviceStartTime, qServiceInfoWithPrice.getEstimateTime() * serviceCount);

//        if (!servicePrice .equals(request.getServicePrice())) {
//            throw new BizException("服务价格与实际定价不一致,无法下单");
//        }

        BigDecimal totalFee = servicePrice.multiply(new BigDecimal(serviceCount)).add(journeyFee);

        SnowFlake snowFlake = new SnowFlake(0, 0);
        String orderNo = String.valueOf(snowFlake.nextId());
        OrderInfoDataBuilder builder = new OrderInfoDataBuilder();
        //地址信息
        builder.address(request.getAddress()).latitude(new BigDecimal(request.getLatitude()))
                .longitude(new BigDecimal(request.getLongitude()));//.cityId(request.getCityId().shortValue());
        //联系人信息
        builder.contactMan(request.getContactMan()).contactPhone(request.getContactPhone())
                .customerUserId(requestContext.getUserId()).customerName(requestContext.getUserName());
        //产品服务信息
        builder.serviceCount(serviceCount).serviceName(serviceInfo.getServiceName())
                .bookStartTime(serviceStartTime).bookEndTime(serviceEndTime);
        //资费信息
        builder.journeyFee(journeyFee).servicePrice(servicePrice).totalAmount(totalFee);
        OrderInfo orderInfo = builder.orderNo(orderNo).orderStatus((byte) OrderStatus.WAIT_GRAB.code())
                .build();
        orderDao.insertOrder(orderInfo);

        return new OrderSubmitResult(orderNo);
    }

    SimpleDateFormat date = new SimpleDateFormat("yyyy年MM月dd日");
    SimpleDateFormat time = new SimpleDateFormat("HH:mm");

    @Override
    public CustomerOrderDetailResult getOrderDetailOrderNo(String orderNo) {
        OrderDetail orderDetail = orderDao.getOrderDetailOrderNo(orderNo);
        String dateTime = date.format(orderDetail.getBookStartTime()) + " " + com.o2o.massage.core.utils.DateUtils.getWeekOfDate(orderDetail.getBookStartTime())
                + " " + time.format(orderDetail.getBookStartTime());
        long start = orderDetail.getBookStartTime().getTime();
        long end = orderDetail.getBookEndTime().getTime();
        long min = (end - start) / (60 * 1000);

        OrderInfo orderInfo = orderDao.queryByOrderNo(orderNo);
        IOrderState orderState = OrderStateManager.getOrderState(orderDetail.getOrderStatus());
        List<EnumOrderAction> enumOrderActions = orderState.customerActions(orderInfo);
        List<OrderAction> orderActions = transToOrderActions(enumOrderActions);
        return CustomerOrderDetailResult.builder().address(orderDetail.getAddress()).contact_phone(orderDetail.getContactPhone())
                .head_pic(orderDetail.getHeadPic()).journey_fee(orderDetail.getJourneyFee())
                .orderStatusCode(orderDetail.getOrderStatus()).evaluationId(orderDetail.getEvaluationId())
                .provider_name(orderDetail.getProviderName()).service_name(orderDetail.getServiceName())
                .service_price(orderDetail.getServicePrice()).total_amount(orderDetail.getTotalAmount())
                .order_status(OrderStatus.getTitle(orderDetail.getOrderStatus())).serviceId(orderDetail.getServiceId())
                .date(dateTime).est(min).orderActions(orderActions).build();
    }

    @Override
    public ProviderOrderDetailResult getProviderOrderDetail(String orderNo) {
        OrderDetail orderDetail = orderDao.getProviderOrderDetail(orderNo);
        String dateTime = date.format(orderDetail.getBookStartTime()) + " " + com.o2o.massage.core.utils.DateUtils.getWeekOfDate(orderDetail.getBookStartTime())
                + " " + time.format(orderDetail.getBookStartTime());
        long start = orderDetail.getBookStartTime().getTime();
        long end = orderDetail.getBookEndTime().getTime();
        long min = (end - start) / (60 * 1000);
        OrderInfo orderInfo = orderDao.queryByOrderNo(orderNo);
        IOrderState orderState = OrderStateManager.getOrderState(orderDetail.getOrderStatus());
        List<EnumOrderAction> enumOrderActions = orderState.customerActions(orderInfo);
        List<OrderAction> orderActions = transToOrderActions(enumOrderActions);
        return ProviderOrderDetailResult.builder().address(orderDetail.getAddress()).contact_phone(orderDetail.getContactPhone())
                .journey_fee(orderDetail.getJourneyFee())
                .orderStatusCode(orderDetail.getOrderStatus()).memberName(orderDetail.getMemberName())
                .service_name(orderDetail.getServiceName())
                .service_price(orderDetail.getServicePrice()).total_amount(orderDetail.getTotalAmount())
                .order_status(OrderStatus.getTitle(orderDetail.getOrderStatus())).serviceId(orderDetail.getServiceId())
                .date(dateTime).est(min).orderActions(orderActions).build();
    }

    private List<OrderAction> transToOrderActions(List<EnumOrderAction> enumOrderActions) {
        if (enumOrderActions == null) {
            return null;
        }
        List<OrderAction> orderActions = new ArrayList<>();
        for (EnumOrderAction enumOrderAction : enumOrderActions) {
            OrderAction orderAction = new OrderAction();
            orderAction.setCode(enumOrderAction.getCode());
            orderAction.setDesc(enumOrderAction.getDesc());
            orderActions.add(orderAction);
        }
        return orderActions;
    }

    @Override
    public OrderEvaluation getOrderDetailOfEvaluation(String orderNo) {
        return orderDao.getOrderDetailOfEvaluation(orderNo);
    }

    @Override
    public List<OrderListItem> getCustomerOrders(String userId, Byte[] orderStatus, int page, int count) {
        return orderDao.getCustomerOrders(userId, Arrays.asList(orderStatus), page, count);
    }

    @Override
    public long getCustomerOrderCount(String userId, Byte[] orderStatus) {
        return orderDao.getCustomerOrderCount(userId, Arrays.asList(orderStatus));
    }

    @Override
    public List<ProviderOrderListItem> getProviderOrders(String userId, Byte[] orderStatus, int page, int count) {
        return orderDao.getProviderOrders(userId, Arrays.asList(orderStatus), page, count);
    }

    @Override
    public long getProviderOrderCount(String userId, Byte[] orderStatus) {
        return orderDao.getProviderOrderCount(userId, Arrays.asList(orderStatus));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveEvaluation(EvaluationRequest evaluationRequest) {
        EvaluationInfo evaluationInfo = new EvaluationInfo();
        evaluationInfo.setCreateTime(new Date());
        evaluationInfo.setUpdateTime(new Date());
        evaluationInfo.setCreateBy(evaluationRequest.getCreateBy());
        evaluationInfo.setOrderNo(evaluationRequest.getOrderNo());
        evaluationInfo.setProviderUserId(evaluationRequest.getProviderUserId());
        evaluationInfo.setEvalucationDesc(evaluationRequest.getDesc());
        evaluationInfo.setEvaluationScore(new BigDecimal(evaluationRequest.getScore()));
        providerBiz.saveEvaluation(evaluationInfo);
        providerBiz.updateServiceStatic(evaluationRequest.getProviderUserId(), evaluationRequest.getServiceId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancelByCustomer(String userId, String orderNo) {
        OrderInfo orderInfo = orderDao.queryByOrderNo(orderNo);
        if (orderInfo == null) {
            throw new OrderNotFoundException(orderNo);
        }
        if (!StringUtils.equals(userId, orderInfo.getCustomerUserId())) {
            throw new OperationNotAllowedException(userId, orderNo);
        }
        IOrderState orderState = OrderStateManager.getOrderState(orderInfo);
        CustomerEntityWrapper customerEntityWrapper = userManager.qryCustomerByUserId(userId);
        orderState.onCustomerCancel(orderInfo, customerEntityWrapper);
        providerBiz.cancelProviderSchedule(userId, orderNo);
    }

    @Override
    public void cancelByProvider(String userId, String orderNo) {
        OrderInfo orderInfo = orderDao.queryByOrderNo(orderNo);
        if (orderInfo == null) {
            throw new OrderNotFoundException(orderNo);
        }
        if (!StringUtils.equals(userId, orderInfo.getProviderUserId())) {
            throw new OperationNotAllowedException(userId, orderNo);
        }
        IOrderState orderState = OrderStateManager.getOrderState(orderInfo);
        ProviderEntityWrapper providerEntityWrapper = userManager.qryProviderByUserId(userId);
        orderState.onProviderCancel(orderInfo, providerEntityWrapper);
        providerBiz.cancelProviderSchedule(userId, orderNo);
    }

    @Override
    public void providerConfirm(String userId, String orderNo) {
        OrderInfo orderInfo = orderDao.queryByOrderNo(orderNo);
        if (orderInfo == null) {
            throw new OrderNotFoundException(orderNo);
        }
        if (!StringUtils.equals(userId, orderInfo.getProviderUserId())) {
            throw new OperationNotAllowedException(userId, orderNo);
        }
        IOrderState orderState = OrderStateManager.getOrderState(orderInfo);
        ProviderEntityWrapper providerEntityWrapper = userManager.qryProviderByUserId(userId);
        orderState.onProviderConfirm(orderInfo, providerEntityWrapper);
    }

    @Override
    public void providerDeny(String userId, String orderNo) {
        OrderInfo orderInfo = orderDao.queryByOrderNo(orderNo);
        if (orderInfo == null) {
            throw new OrderNotFoundException(orderNo);
        }
        if (!StringUtils.equals(userId, orderInfo.getProviderUserId())) {
            throw new OperationNotAllowedException(userId, orderNo);
        }
        IOrderState orderState = OrderStateManager.getOrderState(orderInfo);
        ProviderEntityWrapper providerEntityWrapper = userManager.qryProviderByUserId(userId);
        orderState.onProviderDeny(orderInfo, providerEntityWrapper);
        providerBiz.cancelProviderSchedule(userId, orderNo);
    }

    @Override
    public void providerDepart(String userId, String orderNo) {
        OrderInfo orderInfo = orderDao.queryByOrderNo(orderNo);
        if (orderInfo == null) {
            throw new OrderNotFoundException(orderNo);
        }
        if (StringUtils.equals(userId, orderInfo.getProviderUserId())) {
            throw new OperationNotAllowedException(userId, orderNo);
        }
        IOrderState orderState = OrderStateManager.getOrderState(orderInfo);
        orderState.onProviderDepart(orderInfo);
    }

    @Override
    public void providerServiceStart(String userId, String orderNo) {
        OrderInfo orderInfo = orderDao.queryByOrderNo(orderNo);
        if (orderInfo == null) {
            throw new OrderNotFoundException(orderNo);
        }
        if (!StringUtils.equals(userId, orderInfo.getProviderUserId())) {
            throw new OperationNotAllowedException(userId, orderNo);
        }
        IOrderState orderState = OrderStateManager.getOrderState(orderInfo);
        ProviderEntityWrapper providerEntityWrapper = userManager.qryProviderByUserId(userId);
        orderState.onProviderServiceStart(orderInfo, providerEntityWrapper);
    }

    @Override
    public void providerServiceFinish(String userId, String orderNo) {
        OrderInfo orderInfo = orderDao.queryByOrderNo(orderNo);
        if (orderInfo == null) {
            throw new OrderNotFoundException(orderNo);
        }
        if (!StringUtils.equals(userId, orderInfo.getProviderUserId())) {
            throw new OperationNotAllowedException(userId, orderNo);
        }
        IOrderState orderState = OrderStateManager.getOrderState(orderInfo);
        ProviderEntityWrapper providerEntityWrapper = userManager.qryProviderByUserId(userId);
        orderState.onProviderServiceFinish(orderInfo, providerEntityWrapper);
    }

    @Override
    public void paymentNotify(WxPayResultNotifyEntity notifyEntity) {

    }

    @Override
    public void paymentTimeout(String orderNo) {
        OrderInfo orderInfo = orderDao.queryByOrderNo(orderNo);
        if (orderInfo == null) {
            throw new OrderNotFoundException(orderNo);
        }

        IOrderState orderState = OrderStateManager.getOrderState(orderInfo);
        orderState.onCustomerPayTimeout(orderInfo);
    }

    @Override
    public void providerConfirmTimeout(String orderNo) {
        OrderInfo orderInfo = orderDao.queryByOrderNo(orderNo);
        if (orderInfo == null) {
            throw new OrderNotFoundException(orderNo);
        }

        IOrderState orderState = OrderStateManager.getOrderState(orderInfo);
        orderState.onProviderConfirmTimeout(orderInfo);
    }

    @Override
    public void changeOrderBookTime(String userId, OrderChangeBookTimeRequest changeBookTimeRequest) {
        OrderInfo orderInfo = orderDao.queryByOrderNo(changeBookTimeRequest.getOrderNo());
        if (orderInfo == null) {
            throw new OrderNotFoundException(changeBookTimeRequest.getOrderNo());
        }
        if (StringUtils.equals(userId, orderInfo.getCustomerUserId())) {
            throw new OperationNotAllowedException(userId, changeBookTimeRequest.getOrderNo());
        }
        Date newServiceStartTime = changeBookTimeRequest.getServiceStartTime();

        int millSeconds = (int) (orderInfo.getBookEndTime().getTime() - orderInfo.getBookStartTime().getTime());
        Date newServiceEndTime = DateUtils.addMilliseconds(changeBookTimeRequest.getServiceStartTime(), millSeconds);
        boolean hasTime = orderManager.checkProviderHasTimeBetween(orderInfo.getProviderUserId(), newServiceStartTime, newServiceEndTime);
        if (!hasTime) {
            throw new BizException("该技师该时间点无法接单");
        }
        IOrderState orderState = OrderStateManager.getOrderState(orderInfo);
        orderState.onChangeOrderBookTime(orderInfo, newServiceStartTime, newServiceEndTime, orderInfo.getUpdateBy());
    }

    @Override
    public List<TimeRangeVo> getProgram(String providerUserId, Date start, Date end) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(end);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        return orderDao.getOrders(providerUserId, OrderStatus.WAIT_SERVICE.code(), start, calendar.getTime());
    }

    @Override
    public ProviderProgramOrderInfo getProviderProgramOrderInfo(String orderNo) {
        return orderDao.getProviderProgramOrderInfo(orderNo);
    }
}
