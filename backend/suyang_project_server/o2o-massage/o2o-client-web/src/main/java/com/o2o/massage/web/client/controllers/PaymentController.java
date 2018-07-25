package com.o2o.massage.web.client.controllers;

import com.o2o.massage.biz.PaymentBiz;
import com.o2o.massage.core.common.WebRespResult;
import com.o2o.massage.core.exception.BizException;
import com.o2o.massage.model.request.PaymentDataRequest;
import com.o2o.massage.model.request.PaymentPrepayRequest;
import com.o2o.massage.model.response.PaymentDataResult;
import com.o2o.massage.model.response.PaymentPrepayResult;
import com.o2o.massage.web.client.aop.ClientUserIdentify;
import com.o2o.massage.web.client.aop.EnumRequiredUserType;
import com.o2o.massage.web.common.context.ApiRequestMethod;
import com.o2o.massage.web.common.context.ClientRequestContext;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @Author: zhongli
 * @Date: 2018/3/13 0:34
 * @Description:
 */
@Controller
@RequestMapping("/payment")
@Api(description = "支付相关接口")
public class PaymentController {
    @Resource
    private PaymentBiz paymentBiz;

    @RequestMapping(value = "/paymentdata", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "获取支付相关信息", httpMethod = "POST")
    @ApiRequestMethod
    @ClientUserIdentify(mustLogin = true, requiredClientUserType = EnumRequiredUserType.COMMON_USER)
    public WebRespResult<PaymentDataResult> paymentData(HttpServletRequest request,
                                                        PaymentDataRequest paymentDataRequest) throws BizException {
        WebRespResult retJson = new WebRespResult();
        String fromUid = ClientRequestContext.getCurrent().getUserId();
        PaymentDataResult result = paymentBiz.getPaymentData(fromUid, paymentDataRequest);
        retJson.setData(result);
        return retJson;
    }

    @RequestMapping(value = "/wechat/jsPrepay", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "微信公众号支付-Prepay", httpMethod = "POST")
    @ApiRequestMethod
    @ClientUserIdentify(mustLogin = true, requiredClientUserType = EnumRequiredUserType.COMMON_USER)
    public WebRespResult<PaymentPrepayResult> wechatJsPrepay(HttpServletRequest request,
                                                             PaymentPrepayRequest prepayRequest) throws BizException {
        WebRespResult retJson = new WebRespResult();
        String fromUid = ClientRequestContext.getCurrent().getUserId();
        String userIp = ClientRequestContext.getCurrent().getUserIP();
        PaymentPrepayResult result = paymentBiz.weChatPrepay(prepayRequest);
        retJson.setData(result);
        return retJson;
    }
}
