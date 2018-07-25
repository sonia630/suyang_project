package com.o2o.massage.wechat;

import com.o2o.massage.core.utils.HttpClientHelper;
import com.o2o.massage.core.utils.XStreamUtils;
import com.o2o.massage.wechat.entity.WxRefundRequestData;
import com.o2o.massage.wechat.entity.WxRefundResult;
import com.o2o.massage.wechat.entity.WxUnifiedOrderData;
import com.o2o.massage.wechat.entity.WxUnifiedResult;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Author: zhongli
 * @Date: 2018/3/21 19:47
 * @Description:
 */
@Component
public class WeChatPaymentManager {
    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_1
     *
     * @param unifiedOrderData
     * @return
     */
    public WxUnifiedResult wxUnifiedOrder(WxUnifiedOrderData unifiedOrderData) {
        WxUnifiedResult wxUnifiedResult = null;
        //获取网页授权凭证
        try {
            String body = XStreamUtils.toXml(unifiedOrderData);
            String result = HttpClientHelper.doPost(WeChatConstants.PAY_UNIFIEDORDER_URL, body);
            if (StringUtils.isNotBlank(result)) {
                wxUnifiedResult = XStreamUtils.parse(result, WxUnifiedResult.class);
            }
        } catch (Exception e) {
            logger.error("wxUnifiedOrder error:", e);
        }
        return wxUnifiedResult;
    }

    public WxRefundResult wxRefund(WxRefundRequestData refundRequestData) {
        WxRefundResult wxRefundResult = null;
        //获取网页授权凭证
        try {
            String body = XStreamUtils.toXml(refundRequestData);
            String result = HttpClientHelper.doPost(WeChatConstants.PAY_UNIFIEDORDER_URL, body);
            if (StringUtils.isNotBlank(result)) {
                wxRefundResult = XStreamUtils.parse(result, WxRefundResult.class);
            }
        } catch (Exception e) {
            logger.error("wxRefund error:", e);
        }
        return wxRefundResult;
    }

}
