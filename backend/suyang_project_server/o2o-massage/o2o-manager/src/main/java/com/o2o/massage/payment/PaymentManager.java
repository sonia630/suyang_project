package com.o2o.massage.payment;

import com.o2o.massage.core.exception.DataVersionChangedException;
import com.o2o.massage.dao.component.PaymentDao;
import com.o2o.massage.dao.entity.PaymentOrder;
import com.o2o.massage.dao.entity.PaymentRecord;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author: zhongli
 * @Date: 2018/3/20 16:06
 * @Description:
 */
@Component
public class PaymentManager {

    @Resource
    private PaymentDao paymentDao;

    public int updatePayRecord(long paymentRecordId, PaymentRecord record,
                               int currentVersion, boolean throwException) {
        int updateCount = paymentDao.updatePayRecord(paymentRecordId, record, currentVersion);
        if (updateCount <= 0 && throwException) {
            throw new DataVersionChangedException();
        }
        return updateCount;
    }

    public int updatePaymentOrder(long paymentOrderId, PaymentOrder record,
                                  int currentVersion, boolean throwException) {
        int updateCount = paymentDao.updatePaymentOrder(paymentOrderId, record, currentVersion);
        if (updateCount <= 0 && throwException) {
            throw new DataVersionChangedException();
        }
        return updateCount;
    }
}
