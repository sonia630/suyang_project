package com.o2o.massage.web.client.controllers;

import com.google.common.base.Preconditions;
import com.o2o.massage.biz.ClientLoginBiz;
import com.o2o.massage.biz.ClientUserBiz;
import com.o2o.massage.biz.ServiceBiz;
import com.o2o.massage.core.common.DeviceInfo;
import com.o2o.massage.core.common.EnumApiRequestSide;
import com.o2o.massage.core.common.WebRespResult;
import com.o2o.massage.core.exception.BizException;
import com.o2o.massage.dao.entity.FamilyMember;
import com.o2o.massage.dao.entity.MemberSympton;
import com.o2o.massage.dao.entity.type.EnumUserType;
import com.o2o.massage.model.CustomerInfoVO;
import com.o2o.massage.model.request.CustomerProfileUpdateRequest;
import com.o2o.massage.model.request.LoginByPhoneRequest;
import com.o2o.massage.model.response.LoginResult;
import com.o2o.massage.web.client.aop.ClientUserIdentify;
import com.o2o.massage.web.client.aop.EnumRequiredUserType;
import com.o2o.massage.web.common.context.ApiRequestMethod;
import com.o2o.massage.web.common.context.ClientRequestContext;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: zhongli
 * @Date: 2018/3/13 23:13
 * @Description:
 */
@Controller
@RequestMapping("/customer")
@Api(description = "客户相关接口")
public class CustomerController {

    @Resource
    private ClientLoginBiz loginBiz;
    @Resource
    private ClientUserBiz userBiz;
    @Resource
    private ServiceBiz serviceBiz;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @RequestMapping(value = "/login/do", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "登录")
    @ApiRequestMethod(requestSide = EnumApiRequestSide.CLIENT)
    public WebRespResult<LoginResult> login(LoginByPhoneRequest loginRequest) throws BizException {
        WebRespResult retJson = new WebRespResult();
        DeviceInfo deviceInfo = ClientRequestContext.getCurrent().getDeviceInfo();
        LoginResult loginResult = loginBiz.login(loginRequest, EnumUserType.CUSTOMER, deviceInfo);
        retJson.setData(loginResult);
        retJson.setDesc("登录成功");
        return retJson;
    }

    @RequestMapping(value = "/self/profile", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "Customer个人信息")
    @ApiRequestMethod(requestSide = EnumApiRequestSide.CLIENT)
    @ClientUserIdentify(mustLogin = true, requiredClientUserType = EnumRequiredUserType.COMMON_USER)
    public WebRespResult<CustomerInfoVO> myProfile() throws BizException {
        WebRespResult retJson = new WebRespResult();
        String userId = ClientRequestContext.getCurrent().getUserId();
        CustomerInfoVO customerInfoVO = userBiz.customerSelfProfile(userId);
        retJson.setData(customerInfoVO);
        retJson.setDesc("查询成功");
        return retJson;
    }


    @RequestMapping(value = "/completeInfo", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "补充客户信息")
    @ApiRequestMethod(requestSide = EnumApiRequestSide.CLIENT)
    @ClientUserIdentify(mustLogin = true, requiredClientUserType = EnumRequiredUserType.COMMON_USER)
    public WebRespResult completeInfo(HttpServletRequest request,
                                      @RequestBody CustomerProfileUpdateRequest updateRequest) throws BizException {
        WebRespResult retJson = new WebRespResult();
        String fromUid = ClientRequestContext.getCurrent().getUserId();
        userBiz.completeCustomerInfo(fromUid, updateRequest);
        retJson.setDesc("补充信息成功");
        return retJson;
    }

    @RequestMapping(value = "/selectMemberPage", method = RequestMethod.GET)
    @ApiOperation(value = "显示被服务人员页面")
    @ApiRequestMethod(requestSide = EnumApiRequestSide.CLIENT)
    @ClientUserIdentify(mustLogin = true, requiredClientUserType = EnumRequiredUserType.COMMON_USER)
    public String selectMemberPage(Model model, String from, String serviceId) throws BizException {
        String fromUid = ClientRequestContext.getCurrent().getUserId();
        model.addAttribute("members", userBiz.getFamilyMembers(fromUid));
        model.addAttribute("serviceId", serviceId);
        model.addAttribute("from", from);
        return "client/selectMember";
    }

    @RequestMapping(value = "/addMemberPage", method = RequestMethod.GET)
    @ApiOperation(value = "显示被服务人员页面")
    @ApiRequestMethod(requestSide = EnumApiRequestSide.CLIENT)
    @ClientUserIdentify(mustLogin = true, requiredClientUserType = EnumRequiredUserType.COMMON_USER)
    public String addMemberPage(Model model, String from, String serviceId) throws BizException {
        model.addAttribute("from", from);
        model.addAttribute("serviceId", serviceId);
        return "client/addMember";
    }

    @RequestMapping(value = "/addSympton", method = RequestMethod.GET)
    @ApiOperation(value = "保存被服务人员页面")
    @ApiRequestMethod(requestSide = EnumApiRequestSide.CLIENT)
    @ClientUserIdentify(mustLogin = true, requiredClientUserType = EnumRequiredUserType.COMMON_USER)
    public String addSympton(Model model, String memberId, String serviceId, String from) throws BizException {
        model.addAttribute("member", userBiz.getFamilyMember(memberId));
        model.addAttribute("from", from);
        model.addAttribute("serviceId", serviceId);
        model.addAttribute("memberId", memberId);
        return "client/addSympton";
    }

    @RequestMapping(value = "/saveMember", method = RequestMethod.POST)
    @ApiOperation(value = "保存被服务人员页面")
    @ResponseBody
    @ApiRequestMethod(requestSide = EnumApiRequestSide.CLIENT)
    @ClientUserIdentify(mustLogin = true, requiredClientUserType = EnumRequiredUserType.COMMON_USER)
    public WebRespResult saveMemberPage(FamilyMember familyMember) throws BizException {
        Preconditions.checkArgument(StringUtils.isNotBlank(familyMember.getMemberName())
                , "姓名不能为空");
        Preconditions.checkArgument(familyMember.getMemberBirthday() != null
                , "出生日期不能为空");
        userBiz.saveMember(familyMember, ClientRequestContext.getCurrent().getUserId());
        WebRespResult retJson = new WebRespResult();
        retJson.setDesc("保存被服务人员信息成功");
        return retJson;
    }

    @RequestMapping(value = "/saveSympton", method = RequestMethod.POST)
    @ApiOperation(value = "保存被服务人员症状")
    @ResponseBody
    @ApiRequestMethod(requestSide = EnumApiRequestSide.CLIENT)
    @ClientUserIdentify(mustLogin = true, requiredClientUserType = EnumRequiredUserType.COMMON_USER)
    public WebRespResult saveSympton(MemberSympton memberSympton) throws BizException {
        userBiz.saveSympton(memberSympton, ClientRequestContext.getCurrent().getUserId());
        WebRespResult retJson = new WebRespResult();
        retJson.setDesc("保存被服务人员症状成功");
        return retJson;
    }

    @RequestMapping(value = "/providerOrder", method = RequestMethod.GET)
    @ApiOperation(value = "显示针对顾问下单页")
    @ApiRequestMethod(requestSide = EnumApiRequestSide.CLIENT)
    @ClientUserIdentify(mustLogin = true, requiredClientUserType = EnumRequiredUserType.COMMON_USER)
    public String providerOrder(Model model, String providerId, String serviceId) throws BizException {
        model.addAttribute("provider", userBiz.getBaseUserInfo(providerId));
        model.addAttribute("user", userBiz.getBaseUserInfo(ClientRequestContext.getCurrent().getUserId()));
        model.addAttribute("service", serviceBiz.getService(providerId, serviceId));
        return "client/providerOrder";
    }
}
