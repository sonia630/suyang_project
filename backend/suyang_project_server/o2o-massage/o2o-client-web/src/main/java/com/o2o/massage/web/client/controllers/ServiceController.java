package com.o2o.massage.web.client.controllers;

import com.o2o.massage.biz.ServiceBiz;
import com.o2o.massage.core.exception.BizException;
import com.o2o.massage.model.request.ServiceIdRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Author: zhongli
 * @Date: 2018/3/13 23:33
 * @Description:
 */
@Controller
@RequestMapping("/service")
@Api(description = "服务相关接口")
public class ServiceController {

    @Autowired
    ServiceBiz serviceBiz;

    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    @ApiOperation(value = "服务详情", httpMethod = "GET")
    public String orderForm(Model model, ServiceIdRequest serviceIdRequest) throws BizException {
        model.addAttribute("service", serviceBiz.getDefaultService(serviceIdRequest.getServiceId()));
        return "client/detail";

    }
}
