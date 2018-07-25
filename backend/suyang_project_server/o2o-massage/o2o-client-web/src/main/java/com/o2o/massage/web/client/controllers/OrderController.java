package com.o2o.massage.web.client.controllers;

import com.google.common.collect.Maps;
import com.o2o.massage.biz.ClientOrderBiz;
import com.o2o.massage.biz.ProviderBiz;
import com.o2o.massage.core.common.ApiRequestContext;
import com.o2o.massage.core.common.EnumApiRequestSide;
import com.o2o.massage.core.common.WebRespResult;
import com.o2o.massage.core.constants.OrderStatus;
import com.o2o.massage.core.exception.BizException;
import com.o2o.massage.model.request.*;
import com.o2o.massage.model.response.CustomerOrderDetailResult;
import com.o2o.massage.model.response.OrderFormResult;
import com.o2o.massage.model.response.OrderSubmitResult;
import com.o2o.massage.model.response.ProviderOrderDetailResult;
import com.o2o.massage.web.client.aop.ClientUserIdentify;
import com.o2o.massage.web.client.aop.EnumRequiredUserType;
import com.o2o.massage.web.common.context.ApiRequestMethod;
import com.o2o.massage.web.common.context.ClientRequestContext;
import com.o2o.nm.bo.OrderEvaluation;
import com.o2o.nm.bo.ProviderProgramOrderInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Author: zhongli
 * @Date: 2018/3/4 15:30
 * @Description:
 */
@Controller
@RequestMapping("/order")
@Api(description = "订单相关接口")
public class OrderController {

    @Resource
    private ClientOrderBiz clientOrderBiz;
    @Autowired
    private ProviderBiz providerBiz;

    @Value("${order.page.count}")
    public static final String size = "10";

    @RequestMapping(value = "/form", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "订单填单页接口", httpMethod = "POST")
    @ClientUserIdentify(mustLogin = true, requiredClientUserType = EnumRequiredUserType.COMMON_USER)
    public WebRespResult<OrderFormResult> orderForm(HttpServletRequest request,
                                                    OrderFormRequest orderFormRequest) throws BizException {
        WebRespResult retJson = new WebRespResult();
        String fromUid = ClientRequestContext.getCurrent().getUserId();
        OrderFormResult result = clientOrderBiz.orderForm(orderFormRequest);
        retJson.setData(result);
        return retJson;
    }

    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "订单提交接口", httpMethod = "POST")
    @ApiRequestMethod(requestSide = EnumApiRequestSide.CLIENT)
    @ClientUserIdentify(mustLogin = true, requiredClientUserType = EnumRequiredUserType.COMMON_USER)
    public WebRespResult<OrderSubmitResult> orderSubmit(OrderSubmitRequest orderSubmitRequest) throws BizException {
        WebRespResult retJson = new WebRespResult();
        OrderSubmitResult result = clientOrderBiz.orderSubmit(orderSubmitRequest, ClientRequestContext.getCurrent());
        retJson.setData(result);
        return retJson;
    }

    @RequestMapping(value = "/customer_orders", method = RequestMethod.GET)
    @ApiOperation(value = "显示订单列表", httpMethod = "GET")
    @ApiRequestMethod(requestSide = EnumApiRequestSide.CLIENT)
    @ClientUserIdentify(mustLogin = true, requiredClientUserType = EnumRequiredUserType.COMMON_USER)
    public String customerOrders(Model model, @RequestParam(value = "orderStatus", required = false, defaultValue = "1") int orderStatus) throws BizException {
        model.addAttribute("orderStatus", orderStatus);
        return "client/customer_orders";
    }

    @RequestMapping(value = "/jsonOrders", method = RequestMethod.GET)
    @ApiOperation(value = "客户订单列表", httpMethod = "GET")
    @ApiRequestMethod(requestSide = EnumApiRequestSide.CLIENT)
    @ClientUserIdentify(mustLogin = true, requiredClientUserType = EnumRequiredUserType.COMMON_USER)
    @ResponseBody
    public Map<String, Object> jsonOrders(Byte[] orderStatus, int start, @RequestParam(value = "count", required = false, defaultValue = size) int count) throws BizException {
        String userId = ClientRequestContext.getCurrent().getUserId();
        Map<String, Object> result = Maps.newHashMap();
        result.put("data", clientOrderBiz.getCustomerOrders(userId, orderStatus, start, count));
        result.put("total", clientOrderBiz.getCustomerOrderCount(userId, orderStatus));
        return result;
    }


    @RequestMapping(value = "order_appointment_ok", method = RequestMethod.GET)
    @ApiOperation(value = "预约成功页面", httpMethod = "GET")
    @ApiRequestMethod(requestSide = EnumApiRequestSide.CLIENT)
    @ClientUserIdentify(mustLogin = true, requiredClientUserType = EnumRequiredUserType.COMMON_USER)
    public String appointmentOk(Model model, String orderNo) {
        CustomerOrderDetailResult result = clientOrderBiz.getOrderDetailOrderNo(orderNo);
        model.addAttribute("order", result);
        return "client/order_appointment_ok";
    }

    @RequestMapping(value = "order_detail", method = RequestMethod.GET)
    @ApiOperation(value = "客户订单详情页", httpMethod = "GET")
    @ApiRequestMethod(requestSide = EnumApiRequestSide.CLIENT)
    @ClientUserIdentify(mustLogin = true, requiredClientUserType = EnumRequiredUserType.COMMON_USER)
    public String orderDetail(Model model, String orderNo) {
        CustomerOrderDetailResult result = clientOrderBiz.getOrderDetailOrderNo(orderNo);
        model.addAttribute("order", result);
        model.addAttribute("orderNo", orderNo);
        return "client/order_detail";
    }

    @RequestMapping(value = "order_evaluation", method = RequestMethod.GET)
    @ApiOperation(value = "显示订单详评价页面", httpMethod = "GET")
    @ApiRequestMethod(requestSide = EnumApiRequestSide.CLIENT)
    @ClientUserIdentify(mustLogin = true, requiredClientUserType = EnumRequiredUserType.COMMON_USER)
    public String orderEvaluation(Model model, String orderNo) {
        OrderEvaluation result = clientOrderBiz.getOrderDetailOfEvaluation(orderNo);
        model.addAttribute("order", result);
        model.addAttribute("count", providerBiz.getServiceCount(result.getProviderUserId()));
        return "client/evaluation";
    }

    @RequestMapping(value = "evaluation", method = RequestMethod.POST)
    @ApiOperation(value = "保存订单评价", httpMethod = "POST")
    @ApiRequestMethod(requestSide = EnumApiRequestSide.CLIENT)
    @ClientUserIdentify(mustLogin = true, requiredClientUserType = EnumRequiredUserType.COMMON_USER)
    @ResponseBody
    public String evaluation(EvaluationRequest evaluationRequest) {
        evaluationRequest.setCreateBy(ClientRequestContext.getCurrent().getUserId());
        clientOrderBiz.saveEvaluation(evaluationRequest);
        return String.valueOf(OrderStatus.DONE.code());
    }

    @RequestMapping(value = "/provider_orders", method = RequestMethod.GET)
    @ApiOperation(value = "显示顾问订单列表", httpMethod = "GET")
    @ApiRequestMethod(requestSide = EnumApiRequestSide.CLIENT)
    @ClientUserIdentify(mustLogin = true, requiredClientUserType = EnumRequiredUserType.SERVICE_PROVIDER)
    public String providerOrders(Model model, @RequestParam(value = "orderStatus", required = false, defaultValue = "1") int orderStatus) throws BizException {
        model.addAttribute("orderStatus", orderStatus);
        return "provider/my_orders";
    }

    @RequestMapping(value = "/provider_json_orders", method = RequestMethod.GET)
    @ApiOperation(value = "顾问订单列表", httpMethod = "GET")
    @ApiRequestMethod(requestSide = EnumApiRequestSide.CLIENT)
    @ClientUserIdentify(mustLogin = true, requiredClientUserType = EnumRequiredUserType.SERVICE_PROVIDER)
    @ResponseBody
    public Map<String, Object> providerJsonOrders(Byte[] orderStatus, int start, @RequestParam(value = "count", required = false, defaultValue = size) int count) throws BizException {
        String userId = ClientRequestContext.getCurrent().getUserId();
        Map<String, Object> result = Maps.newHashMap();
        result.put("data", clientOrderBiz.getProviderOrders(userId, orderStatus, start, count));
        result.put("total", clientOrderBiz.getProviderOrderCount(userId, orderStatus));
        return result;
    }

    @RequestMapping(value = "/provider/program_order_info", method = RequestMethod.GET)
    @ApiOperation(value = "顾问排期订单详情", httpMethod = "GET")
    @ApiRequestMethod(requestSide = EnumApiRequestSide.CLIENT)
    @ClientUserIdentify(mustLogin = true, requiredClientUserType = EnumRequiredUserType.SERVICE_PROVIDER)
    @ResponseBody
    public WebRespResult<ProviderProgramOrderInfo> providerProgramOrderInfo(String orderNo) throws BizException {
        WebRespResult retJson = new WebRespResult();
        retJson.setData(clientOrderBiz.getProviderProgramOrderInfo(orderNo));
        return retJson;
    }

    @RequestMapping(value = "provider_order_detail", method = RequestMethod.GET)
    @ApiOperation(value = "顾问订单详情页", httpMethod = "GET")
    @ApiRequestMethod(requestSide = EnumApiRequestSide.CLIENT)
    @ClientUserIdentify(mustLogin = true, requiredClientUserType = EnumRequiredUserType.SERVICE_PROVIDER)
    public String providerOrderDetail(Model model, String orderNo, @RequestParam(value = "orderStatus", required = false, defaultValue = "1") String orderStatus) {
        ProviderOrderDetailResult result = clientOrderBiz.getProviderOrderDetail(orderNo);
        model.addAttribute("order", result);
        model.addAttribute("orderNo", orderNo);
        model.addAttribute("orderStatus", orderStatus);
        return "provider/order_detail";
    }


    @RequestMapping(value = "/customer/cancel", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "订单取消-客户", httpMethod = "POST")
    @ApiRequestMethod(requestSide = EnumApiRequestSide.CLIENT)
    @ClientUserIdentify(mustLogin = true, requiredClientUserType = EnumRequiredUserType.COMMON_USER)
    public WebRespResult orderCustomerCancel(OrderNoRequest orderNoRequest) throws BizException {
        WebRespResult retJson = new WebRespResult();
        ApiRequestContext apiRequestContext = ApiRequestContext.getCurrent();
        clientOrderBiz.cancelByCustomer(apiRequestContext.getUserId(), orderNoRequest.getOrderNo());
        retJson.setDesc("订单取消成功");
        return retJson;
    }

    @RequestMapping(value = "/provider/cancel", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "订单取消-推拿师", httpMethod = "POST")
    @ApiRequestMethod(requestSide = EnumApiRequestSide.CLIENT)
    @ClientUserIdentify(mustLogin = true, requiredClientUserType = EnumRequiredUserType.SERVICE_PROVIDER)
    public WebRespResult orderProviderCancel(OrderNoRequest orderNoRequest) throws BizException {
        WebRespResult retJson = new WebRespResult();
        ApiRequestContext apiRequestContext = ApiRequestContext.getCurrent();
        clientOrderBiz.cancelByProvider(apiRequestContext.getUserId()
                , orderNoRequest.getOrderNo());
        retJson.setDesc("订单取消成功");
        return retJson;
    }

    @RequestMapping(value = "/provider/confirm", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "订单确认-推拿师", httpMethod = "POST")
    @ApiRequestMethod(requestSide = EnumApiRequestSide.CLIENT)
    @ClientUserIdentify(mustLogin = true, requiredClientUserType = EnumRequiredUserType.SERVICE_PROVIDER)
    public WebRespResult orderProviderConfirm(OrderNoRequest orderNoRequest) throws BizException {
        WebRespResult retJson = new WebRespResult();
        ApiRequestContext apiRequestContext = ApiRequestContext.getCurrent();
        clientOrderBiz.providerConfirm(apiRequestContext.getUserId()
                , orderNoRequest.getOrderNo());
        retJson.setDesc("推拿师确认成功");
        return retJson;
    }

    @RequestMapping(value = "/provider/deny", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "订单拒绝-推拿师", httpMethod = "POST")
    @ApiRequestMethod(requestSide = EnumApiRequestSide.CLIENT)
    @ClientUserIdentify(mustLogin = true, requiredClientUserType = EnumRequiredUserType.SERVICE_PROVIDER)
    public WebRespResult orderProviderDeny(OrderNoRequest orderNoRequest) throws BizException {
        WebRespResult retJson = new WebRespResult();
        ApiRequestContext apiRequestContext = ApiRequestContext.getCurrent();
        clientOrderBiz.providerDeny(apiRequestContext.getUserId()
                , orderNoRequest.getOrderNo());
        retJson.setDesc("推拿师拒绝成功");
        return retJson;
    }

    @RequestMapping(value = "/provider/depart", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "推拿师出发", httpMethod = "POST")
    @ApiRequestMethod(requestSide = EnumApiRequestSide.CLIENT)
    @ClientUserIdentify(mustLogin = true, requiredClientUserType = EnumRequiredUserType.SERVICE_PROVIDER)
    public WebRespResult orderProviderDepart(OrderNoRequest orderNoRequest) throws BizException {
        WebRespResult retJson = new WebRespResult();
        ApiRequestContext apiRequestContext = ApiRequestContext.getCurrent();
        clientOrderBiz.providerDepart(apiRequestContext.getUserId()
                , orderNoRequest.getOrderNo());
        retJson.setDesc("推拿师出发成功");
        return retJson;
    }

    @RequestMapping(value = "/provider/servicestart", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "推拿师服务开始", httpMethod = "POST")
    @ApiRequestMethod(requestSide = EnumApiRequestSide.CLIENT)
    @ClientUserIdentify(mustLogin = true, requiredClientUserType = EnumRequiredUserType.SERVICE_PROVIDER)
    public WebRespResult orderProviderSrvStart(OrderNoRequest orderNoRequest) throws BizException {
        WebRespResult retJson = new WebRespResult();
        ApiRequestContext apiRequestContext = ApiRequestContext.getCurrent();
        clientOrderBiz.providerServiceStart(apiRequestContext.getUserId()
                , orderNoRequest.getOrderNo());
        retJson.setDesc("推拿师服务开始");
        return retJson;
    }

    @RequestMapping(value = "/provider/servicefinish", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "推拿师服务结束", httpMethod = "POST")
    @ApiRequestMethod(requestSide = EnumApiRequestSide.CLIENT)
    @ClientUserIdentify(mustLogin = true, requiredClientUserType = EnumRequiredUserType.SERVICE_PROVIDER)
    public WebRespResult orderProviderSrvFinish(OrderNoRequest orderNoRequest) throws BizException {
        WebRespResult retJson = new WebRespResult();
        ApiRequestContext apiRequestContext = ApiRequestContext.getCurrent();
        clientOrderBiz.providerServiceFinish(apiRequestContext.getUserId()
                , orderNoRequest.getOrderNo());
        retJson.setDesc("推拿师服务结束");
        return retJson;
    }

    @RequestMapping(value = "/booktime/change", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "订单更改预定时间-客户", httpMethod = "POST")
    @ApiRequestMethod(requestSide = EnumApiRequestSide.CLIENT)
    @ClientUserIdentify(mustLogin = true, requiredClientUserType = EnumRequiredUserType.COMMON_USER)
    public WebRespResult orderBookTimeChange(OrderChangeBookTimeRequest orderNoRequest) throws BizException {
        WebRespResult retJson = new WebRespResult();
        ApiRequestContext apiRequestContext = ApiRequestContext.getCurrent();
        clientOrderBiz.cancelByCustomer(apiRequestContext.getUserId()
                , orderNoRequest.getOrderNo());
        retJson.setDesc("订单取消成功");
        return retJson;
    }


}
