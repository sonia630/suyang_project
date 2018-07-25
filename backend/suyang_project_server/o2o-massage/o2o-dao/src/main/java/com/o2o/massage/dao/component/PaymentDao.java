package com.o2o.massage.dao.component;

import com.o2o.massage.core.constants.EnumPayWay;
import com.o2o.massage.dao.entity.*;
import com.o2o.massage.dao.mapper.PaymentOrderMapper;
import com.o2o.massage.dao.mapper.PaymentRecordMapper;
import com.o2o.massage.dao.mapper.RefundRecordMapper;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: zhongli
 * @Date: 2018/3/2 14:04
 * @Description:
 */
@Component
public class PaymentDao {

    @Autowired
    private PaymentOrderMapper paymentOrderMapper;

    @Autowired
    private PaymentRecordMapper paymentRecordMapper;

    @Autowired
    private RefundRecordMapper refundRecordMapper;

    public int insertPaymentOrder(PaymentOrder record) {
        return paymentOrderMapper.insert(record);
    }

    /**
     * 乐观锁更新
     *
     * @param paymentOrderId
     * @param record
     * @param currentVersion
     * @return
     */
    public int updatePaymentOrder(long paymentOrderId, PaymentOrder record, int currentVersion) {
        PaymentOrderExample example = new PaymentOrderExample();
        example.createCriteria().andIdEqualTo(paymentOrderId).
                andVersionEqualTo(currentVersion);
        return paymentOrderMapper.updateByExampleSelective(record, example);
    }

    public PaymentOrder queryOnePaymentOrderByBizOrderNo(String orderNo) {
        List<PaymentOrder> list = queryPaymentOrderListByBizOrderNo(orderNo);
        return CollectionUtils.isEmpty(list) ? null : list.get(0);
    }

    public List<PaymentOrder> queryPaymentOrderListByBizOrderNo(String orderNo) {
        PaymentOrderExample example = new PaymentOrderExample();
        example.createCriteria().andBizOrderNoEqualTo(orderNo);
        List<PaymentOrder> orderInfoList = paymentOrderMapper.selectByExample(example);
        return orderInfoList;
    }

    public PaymentOrder queryByPayToken(String token) {
        PaymentOrderExample example = new PaymentOrderExample();
        example.createCriteria().andTradeTokenEqualTo(token);
        List<PaymentOrder> orderInfoList = paymentOrderMapper.selectByExample(example);
        return CollectionUtils.isEmpty(orderInfoList) ? null : orderInfoList.get(0);
    }

    public PaymentOrder queryByPayTradeNo(String payTradeNo) {
        PaymentOrderExample example = new PaymentOrderExample();
        example.createCriteria().andPayTradeNoEqualTo(payTradeNo);
        List<PaymentOrder> orderInfoList = paymentOrderMapper.selectByExample(example);
        return CollectionUtils.isEmpty(orderInfoList) ? null : orderInfoList.get(0);
    }


    public int insertPaymentRecord(PaymentRecord paymentRecord) {
        return paymentRecordMapper.insertSelective(paymentRecord);
    }

    public int updatePayRecord(long paymentRecordId, PaymentRecord record, int currentVersion) {
        PaymentRecordExample example = new PaymentRecordExample();
        example.createCriteria().andIdEqualTo(paymentRecordId).
                andVersionEqualTo(currentVersion);
        return paymentRecordMapper.updateByExampleSelective(record, example);
    }

    public List<PaymentRecord> queryPaymentRecordList(String payTradeNo) {
        PaymentRecordExample example = new PaymentRecordExample();
        example.createCriteria().andPayTradeNoEqualTo(payTradeNo);
        List<PaymentRecord> orderInfoList = paymentRecordMapper.selectByExample(example);
        return orderInfoList;
    }

    public PaymentRecord queryPaymentRecordList(String payTradeNo, EnumPayWay payWay) {
        PaymentRecordExample example = new PaymentRecordExample();
        example.createCriteria().andPayTradeNoEqualTo(payTradeNo).andPayWayNoEqualTo(payWay.getCode().byteValue());
        List<PaymentRecord> orderInfoList = paymentRecordMapper.selectByExample(example);
        return CollectionUtils.isEmpty(orderInfoList) ? null : orderInfoList.get(0);
    }

    public int insertRefundRecord(RefundRecord refundRecord) {
        return refundRecordMapper.insertSelective(refundRecord);
    }

    public int updateRefundRecord(long refundRecordId, RefundRecord refundRecord, int currentVersion) {
        RefundRecordExample example = new RefundRecordExample();
        example.createCriteria().andIdEqualTo(refundRecordId).
                andVersionEqualTo(currentVersion);
        return refundRecordMapper.updateByExampleSelective(refundRecord, example);
    }
}
