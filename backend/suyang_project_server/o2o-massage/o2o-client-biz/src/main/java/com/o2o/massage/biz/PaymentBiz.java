package com.o2o.massage.biz;

import com.o2o.massage.model.request.PaymentDataRequest;
import com.o2o.massage.model.request.PaymentPrepayRequest;
import com.o2o.massage.model.response.PaymentDataResult;
import com.o2o.massage.model.response.PaymentPrepayResult;

/**
 * @Author: zhongli
 * @Date: 2018/3/20 16:09
 * @Description:
 */
public interface PaymentBiz {
    /**
     * 收银台获取支付信息
     * @param fromUid
     * @param request
     * @return
     */
    PaymentDataResult getPaymentData(String fromUid,PaymentDataRequest request);

    /**
     * 微信支付统一下单
     * @param prepayRequest
     * @return
     */
    PaymentPrepayResult weChatPrepay(PaymentPrepayRequest prepayRequest);
}
