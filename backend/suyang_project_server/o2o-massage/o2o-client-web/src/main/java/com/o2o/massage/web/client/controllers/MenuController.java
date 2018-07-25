package com.o2o.massage.web.client.controllers;

import com.o2o.massage.biz.ClientUserBiz;
import com.o2o.massage.biz.ServiceBiz;
import com.o2o.massage.core.common.ApiRequestContext;
import com.o2o.massage.core.common.EnumApiRequestSide;
import com.o2o.massage.model.UserBaseInfoVO;
import com.o2o.massage.web.Constants;
import com.o2o.massage.web.client.aop.ClientUserIdentify;
import com.o2o.massage.web.client.aop.EnumRequiredUserType;
import com.o2o.massage.web.common.context.ApiRequestMethod;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@Controller
@Api(description = "导航菜单接口")
public class MenuController {

    @Autowired
    ServiceBiz serviceBiz;
    @Autowired
    ClientUserBiz clientUserBiz;

    public static final long expire_time = 30 * 24 * 60 * 60 * 1000L;
    public static final int span = 10000;//查询方圆10km

    @RequestMapping(value = {"/index"})
    public String index(Model model) {
        model.addAttribute("service", serviceBiz.getServices());
        model.addAttribute("cats", serviceBiz.getCats());
        return "client/home";
    }

    @RequestMapping(value = {"/provider"})
    public String provider(Model model) {
        model.addAttribute("cats", serviceBiz.getCats());
        model.addAttribute("span", span);
        return "client/provider";
    }

    @RequestMapping("/home/complete")
    @ApiRequestMethod(requestSide = EnumApiRequestSide.CLIENT)
    @ClientUserIdentify(mustLogin = true, requiredClientUserType = EnumRequiredUserType.COMMON_USER)
    @ResponseBody
    public boolean needCompletion() {
        String userId = ApiRequestContext.getCurrent().getUserId();
        UserBaseInfoVO userInfo = clientUserBiz.getBaseUserInfo(userId);
        if (StringUtils.isEmpty(userInfo.getIdNum())) {
            Date loginDate = userInfo.getLoginDate();
            if (loginDate == null) {
                return true;
            } else {
                if ((System.currentTimeMillis() - loginDate.getTime()) > expire_time) {
                    return true;
                }
            }
            return false;
        } else {
            return false;
        }
    }

    @RequestMapping("/user")
    @ApiRequestMethod(requestSide = EnumApiRequestSide.CLIENT)
    @ClientUserIdentify(mustLogin = true, requiredClientUserType = EnumRequiredUserType.COMMON_USER)
    public String user() {
        return "client/my";
    }

    @RequestMapping("/menu/detail")
    public String detail(Model model, @RequestParam("serviceId") String serviceId) {
        model.addAttribute("service", serviceBiz.getDefaultService(serviceId));
        model.addAttribute("serviceId", serviceId);
        return "client/detail";
    }

    @RequestMapping("/service_order")
    @ApiRequestMethod(requestSide = EnumApiRequestSide.CLIENT)
    @ClientUserIdentify(mustLogin = true, requiredClientUserType = EnumRequiredUserType.COMMON_USER)
    public String subscribe(Model model, @RequestParam("serviceId") String serviceId, String providerId) {
        model.addAttribute("service", serviceBiz.getDefaultService(serviceId));
        String userId = ApiRequestContext.getCurrent().getUserId();
        model.addAttribute("user", clientUserBiz.getBaseUserInfo(userId));
        model.addAttribute("providerId", providerId);
        return "client/serviceOrder";
    }

    @RequestMapping(Constants.provider_login_url)
    @ApiRequestMethod(requestSide = EnumApiRequestSide.CLIENT)
    public String provider_login(Model model, String from) {
        model.addAttribute("form", getFrom(from));
        return "provider/login";
    }


    @RequestMapping(Constants.client_login_url)
    @ApiRequestMethod(requestSide = EnumApiRequestSide.CLIENT)
    public String customer_login(Model model, String from) {
        model.addAttribute("form", getFrom(from));
        return "client/login";
    }

    private String getFrom(String from) {
        if (StringUtils.isNoneBlank(from) && from.contains("$$$")) {
            return from.replaceAll("\\$\\$\\$", "&");
        }
        return from;
    }
}
