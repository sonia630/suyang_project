package com.o2o.massage.biz.task;

import com.o2o.massage.biz.ClientOrderBiz;
import com.o2o.massage.core.constants.OrderStatus;
import com.o2o.massage.dao.component.OrderDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: zhongli
 * @Date: 2018/4/16 16:21
 * @Description:
 */
public class PayTimeOutTask extends AbstractTask {
    private Logger logger= LoggerFactory.getLogger(getClass());

    @Resource
    private OrderDao orderDao;

    @Resource
    private ClientOrderBiz orderBiz;

    @Override
    public String taskName() {
        return getClass().getSimpleName();
    }

    @Override
    protected void run() throws Exception {
        List<String> orderNos = orderDao.qryTimeoutOrdersByStatus(OrderStatus.WAIT_PAY.code());
        for(String orderNo: orderNos){
            try {
                orderBiz.paymentTimeout(orderNo);
                logger.info("process orderNo:{} done",orderNo);
            } catch (Exception ex) {
                logger.error(String.format("process orderNo:%s error",orderNo),ex);
            }
        }
    }
}
