package com.o2o.massage.dao.component;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.o2o.massage.core.common.ShortDate;
import com.o2o.massage.core.constants.OrderStatus;
import com.o2o.massage.core.utils.DateUtils;
import com.o2o.massage.dao.entity.OrderInfo;
import com.o2o.massage.dao.entity.OrderInfoExample;
import com.o2o.massage.dao.mapper.OrderInfoMapper;
import com.o2o.nm.bo.*;
import com.o2o.nm.mapper.OrderInfoOwnerMapper;
import com.o2o.nm.vo.TimeRangeVo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @Author: zhongli
 * @Date: 2018/2/25 22:17
 * @Description:
 */
@Component
public class OrderDao {

    @Autowired
    private OrderInfoMapper orderInfoMapper;
    @Autowired
    private OrderInfoOwnerMapper orderInfoOwnerMapper;

    public int insertOrder(OrderInfo record) {
        return orderInfoMapper.insertSelective(record);
    }

    /**
     * 乐观锁更新
     */
    public int updateOrder(long orderId, OrderInfo record, int currentVersion) {
        OrderInfoExample example = new OrderInfoExample();
        example.createCriteria().andOrderIdEqualTo(orderId).
                andVersionEqualTo(currentVersion);
        int updateCount = orderInfoMapper.updateByExampleSelective(record, example);
        return updateCount;
    }

    public OrderInfo queryByOrderNo(String orderNo) {
        OrderInfoExample example = new OrderInfoExample();
        example.createCriteria().andOrderNoEqualTo(orderNo);
        List<OrderInfo> orderInfoList = orderInfoMapper.selectByExample(example);
        return CollectionUtils.isEmpty(orderInfoList) ? null : orderInfoList.get(0);
    }

    public OrderDetail getOrderDetailOrderNo(String orderNo) {
        return orderInfoOwnerMapper.getOrderDetailOrderNo(orderNo);
    }

    public OrderDetail getProviderOrderDetail(String orderNo) {
        return orderInfoOwnerMapper.getProviderOrderDetail(orderNo);
    }

    /**
     * 根据条件查询订单列表
     */
    public PageInfo<OrderInfo> queryOrderListByPage(OrderInfoExample example, int pageNo, int pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        List<OrderInfo> orderInfoList = orderInfoMapper.selectByExample(example);
        return new PageInfo<>(orderInfoList);
    }

    public List<OrderInfo> queryFutureOrdersByProviderId(String userId, int days) {
        Date startDate = new ShortDate(new Date()).toDate();
        Date endDate = DateUtils.getDateAfter(startDate, days);

        OrderInfoExample example = new OrderInfoExample();
        example.createCriteria().andProviderUserIdEqualTo(userId)
                .andOrderStatusNotEqualTo((byte) OrderStatus.CANCELLED.code())
                .andBookStartTimeGreaterThanOrEqualTo(startDate).andBookEndTimeLessThan(endDate);
        List<OrderInfo> orderInfos = orderInfoMapper.selectByExample(example);
        return orderInfos;
    }

    public List<OrderInfo> queryExistOrdersByProviderIdWithTime(String userId, Date startTime, Date endTime) {
        OrderInfoExample example = new OrderInfoExample();
        OrderInfoExample.Criteria criteria = example.createCriteria().andProviderUserIdEqualTo(userId)
                .andOrderStatusNotEqualTo((byte) OrderStatus.CANCELLED.code()).
                        andBookStartTimeLessThan(endTime).andBookEndTimeGreaterThan(startTime);
        List<OrderInfo> orderInfos = orderInfoMapper.selectByExample(example);
        return orderInfos;
    }


    public List<OrderListItem> getCustomerOrders(String userId, List<Byte> orderStatus, int page, int size) {
        return orderInfoOwnerMapper.getCustomerOrders(userId, orderStatus, page, size);
    }

    public long getCustomerOrderCount(String userId, List<Byte> orderStatus) {
        OrderInfoExample orderInfoExample = new OrderInfoExample();
        orderInfoExample.createCriteria().andCustomerUserIdEqualTo(userId)
                .andOrderStatusIn(orderStatus);
        return orderInfoMapper.countByExample(orderInfoExample);
    }

    public OrderEvaluation getOrderDetailOfEvaluation(String orderNo) {
        return orderInfoOwnerMapper.getOrderDetailOfEvaluation(orderNo);
    }

    public List<ProviderOrderListItem> getProviderOrders(String userId, List<Byte> orderStatus, int page, int size) {
        return orderInfoOwnerMapper.getProviderOrders(userId, orderStatus, page, size);
    }

    public long getProviderOrderCount(String userId, List<Byte> orderStatus) {
        OrderInfoExample orderInfoExample = new OrderInfoExample();
        orderInfoExample.createCriteria().andProviderUserIdEqualTo(userId)
                .andOrderStatusIn(orderStatus);
        return orderInfoMapper.countByExample(orderInfoExample);

    }

    public List<String> qryTimeoutOrdersByStatus(int orderStatus) {
        return orderInfoOwnerMapper.qryTimeoutOrdersByStatus(orderStatus);
    }

    public List<TimeRangeVo> getOrders(String providerUserId, int status, Date begin, Date end) {
        return orderInfoOwnerMapper.getOrders(providerUserId, status, begin, end);
    }

    public ProviderProgramOrderInfo getProviderProgramOrderInfo(String orderNo) {
        return orderInfoOwnerMapper.getProviderProgramOrderInfo(orderNo);
    }
}
