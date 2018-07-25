package com.o2o.nm.mapper;

import com.o2o.nm.bo.*;
import com.o2o.nm.vo.TimeRangeVo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface OrderInfoOwnerMapper {

    OrderDetail getOrderDetailOrderNo(String orderNo);

    List<OrderListItem> getCustomerOrders(@Param("userId") String userId, @Param("orderStatus") List<Byte> orderStatus, @Param("page") int page, @Param("size") int size);

    OrderEvaluation getOrderDetailOfEvaluation(String orderNo);

    List<ProviderOrderListItem> getProviderOrders(@Param("userId") String userId, @Param("orderStatus") List<Byte> orderStatus, @Param("page") int page, @Param("size") int size);

    OrderDetail getProviderOrderDetail(String orderNo);

    List<String> qryTimeoutOrdersByStatus(@Param("orderStatus") int orderStatus);

    List<TimeRangeVo> getOrders(@Param("providerUserId") String providerUserId,
                                @Param("status") int status,
                                @Param("begin") Date begin, @Param("end") Date end);

    ProviderProgramOrderInfo getProviderProgramOrderInfo(String orderNo);
}
