package com.o2o.massage.web.client.controllers;


import com.o2o.massage.biz.ClientUserBiz;
import com.o2o.massage.biz.ProviderBiz;
import com.o2o.massage.core.common.ApiRequestContext;
import com.o2o.massage.core.common.EnumApiRequestSide;
import com.o2o.massage.dao.entity.CustomerAddress;
import com.o2o.massage.web.Constants;
import com.o2o.massage.web.client.aop.ClientUserIdentify;
import com.o2o.massage.web.client.aop.EnumRequiredUserType;
import com.o2o.massage.web.common.context.ApiRequestMethod;
import com.o2o.massage.web.common.context.ClientRequestContext;
import com.o2o.nm.vo.ProviderScheduleVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@Api(description = "通用服务接口")
@RequestMapping("common")
public class CommonController {

    private Logger logger = LoggerFactory.getLogger(getClass());
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
    public static final int span = 10000;//查询方圆10km

    @Autowired
    private ClientUserBiz clientUserBiz;
    @Autowired
    private ProviderBiz providerBiz;

    @RequestMapping("location")
    @ApiOperation(value = "获取地址信息")
    @ApiRequestMethod(requestSide = EnumApiRequestSide.CLIENT)
    @ClientUserIdentify(mustLogin = true, requiredClientUserType = EnumRequiredUserType.COMMON_USER)
    public String location(Model model, String serviceId, String from) {
        String userId = ApiRequestContext.getCurrent().getUserId();
        model.addAttribute("locations", clientUserBiz.getUserLocations(userId));
        model.addAttribute("serviceId", serviceId);
        model.addAttribute("from", from);
        return "selectlocation";
    }

    @RequestMapping("time")
    @ApiOperation(value = "获取时间安排")
    @ApiRequestMethod(requestSide = EnumApiRequestSide.CLIENT)
    @ClientUserIdentify(mustLogin = true, requiredClientUserType = EnumRequiredUserType.COMMON_USER)
    public String time(Model model, String serviceId, String from, BigDecimal lng, BigDecimal lat) {
        model.addAttribute("serviceId", serviceId);
        model.addAttribute("from", from);
        model.addAttribute("time", simpleDateFormat.format(new Date()));
        model.addAttribute("lng", lng);
        model.addAttribute("lat", lat);
        model.addAttribute("span", span);
        return "selecttime";
    }

    @RequestMapping("schedule")
    @ApiOperation(value = "获取某天安排")
    @ApiRequestMethod(requestSide = EnumApiRequestSide.CLIENT)
    @ClientUserIdentify(mustLogin = true, requiredClientUserType = EnumRequiredUserType.COMMON_USER)
    @ResponseBody
    public Map<Integer, List<String>> schedule(ProviderScheduleVo providerScheduleVo) {
        return providerBiz.getProviderSchedulesByLongLat(providerScheduleVo);
    }

    @RequestMapping("save_defaultLocation")
    @ApiOperation(value = "保存默认地址信息")
    @ApiRequestMethod(requestSide = EnumApiRequestSide.CLIENT)
    @ClientUserIdentify(mustLogin = true, requiredClientUserType = EnumRequiredUserType.COMMON_USER)
    @ResponseBody
    public CommonResult save_defaultLocation(CustomerAddress customerAddress) {
        CommonResult result = new CommonResult();
        try {
            customerAddress.setDefaultAddress((byte) 1);
            clientUserBiz.saveUserDefaultLocation(customerAddress, ClientRequestContext.getCurrent().getUserId());
            result.setMessage("保存成功");
            result.setStatus(Constants.SUCCESS);
        } catch (Exception e) {
            logger.error("{}", e);
            result.setMessage("保存失败");
            result.setStatus(Constants.FAILURE);
        }
        return result;
    }

    @RequestMapping("my_name")
    @ApiOperation(value = "显示修改用户姓名页面")
    @ApiRequestMethod(requestSide = EnumApiRequestSide.CLIENT)
    @ClientUserIdentify(mustLogin = true, requiredClientUserType = EnumRequiredUserType.SERVICE_PROVIDER)
    public String providerMyName(Model model, String name, String from) {
        model.addAttribute("name", name);
        model.addAttribute("from", from);
        return "myName";
    }

    @RequestMapping("update_realName")
    @ResponseBody
    @ApiOperation(value = "修改用户真实姓名")
    @ApiRequestMethod(requestSide = EnumApiRequestSide.CLIENT)
    @ClientUserIdentify(mustLogin = true, requiredClientUserType = EnumRequiredUserType.SERVICE_PROVIDER)
    public CommonResult saveRealName(String name) {
        CommonResult result = new CommonResult();
        try {
            String userId = ApiRequestContext.getCurrent().getUserId();
            clientUserBiz.updateUserRealName(userId, name);
            result.setMessage("修改成功");
            result.setStatus(Constants.SUCCESS);
        } catch (Exception e) {
            logger.error("{}", e);
            result.setMessage("修改失败");
            result.setStatus(Constants.FAILURE);
        }
        return result;
    }

    @RequestMapping("common_address")
    @ApiOperation(value = "显示修改顾问常用地址页")
    @ApiRequestMethod(requestSide = EnumApiRequestSide.CLIENT)
    @ClientUserIdentify(mustLogin = true, requiredClientUserType = EnumRequiredUserType.SERVICE_PROVIDER)
    public String providerAddress(Model model, String address) {
        model.addAttribute("address", address);
        return "provider/provider_address";
    }

    @RequestMapping("customer_address")
    @ApiOperation(value = "显示修改用户常用地址页")
    @ApiRequestMethod(requestSide = EnumApiRequestSide.CLIENT)
    @ClientUserIdentify(mustLogin = true, requiredClientUserType = EnumRequiredUserType.COMMON_USER)
    public String customerAddress(Model model, String address) {
        String userId = ApiRequestContext.getCurrent().getUserId();
        model.addAttribute("address", address);
        model.addAttribute("locations", clientUserBiz.getUserLocations(userId));
        return "client/customer_address";
    }

    @RequestMapping("save_commonAddress")
    @ApiOperation(value = "修改顾问常用地址")
    @ApiRequestMethod(requestSide = EnumApiRequestSide.CLIENT)
    @ClientUserIdentify(mustLogin = true, requiredClientUserType = EnumRequiredUserType.SERVICE_PROVIDER)
    @ResponseBody
    public CommonResult saveCommonAddress(String address, BigDecimal longitude, BigDecimal latitude) {
        CommonResult result = new CommonResult();
        try {
            String userId = ApiRequestContext.getCurrent().getUserId();
            providerBiz.updateCommonAddress(userId, address, longitude, latitude);
            result.setMessage("修改成功");
            result.setStatus(Constants.SUCCESS);
        } catch (Exception e) {
            logger.error("{}", e);
            result.setMessage("修改失败");
            result.setStatus(Constants.FAILURE);
        }
        return result;

    }

    @Getter
    @Setter
    public static class CommonResult {
        String message;
        String status;
    }
}
