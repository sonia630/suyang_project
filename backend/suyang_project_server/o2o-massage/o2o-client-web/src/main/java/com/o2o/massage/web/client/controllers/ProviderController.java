package com.o2o.massage.web.client.controllers;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.o2o.massage.biz.ClientLoginBiz;
import com.o2o.massage.biz.ClientOrderBiz;
import com.o2o.massage.biz.ClientUserBiz;
import com.o2o.massage.biz.ProviderBiz;
import com.o2o.massage.core.common.ApiRequestContext;
import com.o2o.massage.core.common.DeviceInfo;
import com.o2o.massage.core.common.EnumApiRequestSide;
import com.o2o.massage.core.common.WebRespResult;
import com.o2o.massage.core.exception.BizException;
import com.o2o.massage.core.utils.Constants;
import com.o2o.massage.dao.entity.ProviderCertificationInfo;
import com.o2o.massage.dao.entity.ProviderInfo;
import com.o2o.massage.dao.entity.SrvProviderSrvRel;
import com.o2o.massage.dao.entity.type.EnumUserType;
import com.o2o.massage.model.ProviderInfoVO;
import com.o2o.massage.model.ProviderScheduleShowVo;
import com.o2o.massage.model.ProviderSimpleInfoVO;
import com.o2o.massage.model.request.*;
import com.o2o.massage.model.response.LoginResult;
import com.o2o.massage.web.client.aop.ClientUserIdentify;
import com.o2o.massage.web.client.aop.EnumRequiredUserType;
import com.o2o.massage.web.common.context.ApiRequestMethod;
import com.o2o.massage.web.common.context.ClientRequestContext;
import com.o2o.nm.bo.ProviderEval;
import com.o2o.nm.bo.ProviderServiceInfo;
import com.o2o.nm.bo.ProviderValidateBo;
import com.o2o.nm.vo.ProviderList;
import com.o2o.nm.vo.TimeRangeVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author: zhongli
 * @Date: 2018/3/13 23:13
 * @Description:
 */
@Controller
@RequestMapping("/provider")
@Api(description = "推拿师相关接口")
public class ProviderController {

    @Resource
    private ClientLoginBiz loginBiz;

    @Resource
    private ClientUserBiz userBiz;

    @Resource
    private ProviderBiz providerBiz;
    @Resource
    private ClientOrderBiz clientOrderBiz;

    @RequestMapping(value = "/login/do", method = RequestMethod.POST)
    @ApiRequestMethod(requestSide = EnumApiRequestSide.CLIENT)
    @ResponseBody
    @ApiOperation(value = "登录")
    public WebRespResult<LoginResult> login(LoginByPhoneRequest loginRequest) throws BizException {
        WebRespResult retJson = new WebRespResult();
        DeviceInfo deviceInfo = ClientRequestContext.getCurrent().getDeviceInfo();
        LoginResult loginResult = loginBiz.login(loginRequest, EnumUserType.PROVIDER, deviceInfo);
        retJson.setData(loginResult);
        retJson.setDesc("登录成功");
        return retJson;
    }

    @RequestMapping(value = "/self/profile", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "推拿师查看自己个人信息")
    @ApiRequestMethod(requestSide = EnumApiRequestSide.CLIENT)
    @ClientUserIdentify(mustLogin = true, requiredClientUserType = EnumRequiredUserType.SERVICE_PROVIDER)
    public WebRespResult<ProviderInfoVO> myProfile() throws BizException {
        WebRespResult retJson = new WebRespResult();
        DeviceInfo deviceInfo = ClientRequestContext.getCurrent().getDeviceInfo();
        String userId = ClientRequestContext.getCurrent().getUserId();
        ProviderInfoVO providerInfoVO = userBiz.providerSelfProfile(userId);
        retJson.setData(providerInfoVO);
        retJson.setDesc("查询成功");
        return retJson;
    }

    @RequestMapping(value = "/self/profile/page", method = RequestMethod.GET)
    @ApiOperation(value = "显示顾问信息")
    @ApiRequestMethod(requestSide = EnumApiRequestSide.CLIENT)
    @ClientUserIdentify(mustLogin = true, requiredClientUserType = EnumRequiredUserType.SERVICE_PROVIDER)
    public String my() throws BizException {
        return "provider/my";
    }

    @RequestMapping(value = "/other/profile", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "查询推拿师信息")
    @ApiRequestMethod(requestSide = EnumApiRequestSide.CLIENT)
    @ClientUserIdentify(mustLogin = false)
    public WebRespResult<ProviderSimpleInfoVO> otherProfile(@RequestBody UserInfoQryRequest infoQryRequest) throws BizException {
        WebRespResult retJson = new WebRespResult();
        ProviderSimpleInfoVO providerInfoVO = userBiz.providerOtherProfile(infoQryRequest.getUserId());
        retJson.setData(providerInfoVO);
        retJson.setDesc("查询成功");
        return retJson;
    }

    @RequestMapping(value = "/completeInfo", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "补充推拿师信息")
    @ApiRequestMethod(requestSide = EnumApiRequestSide.CLIENT)
    @ClientUserIdentify(mustLogin = true, requiredClientUserType = EnumRequiredUserType.SERVICE_PROVIDER)
    public WebRespResult completeInfo(ProviderProfileUpdateRequest updateRequest) throws BizException {
        WebRespResult retJson = new WebRespResult();
        String fromUid = ClientRequestContext.getCurrent().getUserId();
        userBiz.completeProviderInfo(fromUid, updateRequest);
        retJson.setData("");
        retJson.setDesc("补充信息成功");
        return retJson;
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @RequestMapping(value = "/completePage", method = RequestMethod.GET)
    @ApiOperation(value = "显示补充推拿师信息")
    @ApiRequestMethod(requestSide = EnumApiRequestSide.CLIENT)
    @ClientUserIdentify(mustLogin = true, requiredClientUserType = EnumRequiredUserType.SERVICE_PROVIDER)
    public String completePage(Model model) throws BizException {
        String userId = ClientRequestContext.getCurrent().getUserId();
        ProviderInfoVO providerInfoVO = userBiz.providerSelfProfile(userId);
        model.addAttribute("provider", providerInfoVO);
        return "provider/providerInfo";
    }

    @RequestMapping(value = "/program", method = RequestMethod.GET)
    @ApiOperation(value = "显示顾问日程页面")
    @ApiRequestMethod(requestSide = EnumApiRequestSide.CLIENT)
    @ClientUserIdentify(mustLogin = true, requiredClientUserType = EnumRequiredUserType.SERVICE_PROVIDER)
    public String program() throws BizException {
        return "provider/program";
    }

    @RequestMapping(value = "/schedulePage", method = RequestMethod.GET)
    @ApiOperation(value = "显示顾问排期页面")
    @ApiRequestMethod(requestSide = EnumApiRequestSide.CLIENT)
    @ClientUserIdentify(mustLogin = true, requiredClientUserType = EnumRequiredUserType.SERVICE_PROVIDER)
    public String schedule() throws BizException {
        return "provider/schedule";
    }

    @RequestMapping("/complete")
    @ApiRequestMethod(requestSide = EnumApiRequestSide.CLIENT)
    @ClientUserIdentify(mustLogin = true, requiredClientUserType = EnumRequiredUserType.SERVICE_PROVIDER)
    @ResponseBody
    public boolean needCompletion() {
        String userId = ApiRequestContext.getCurrent().getUserId();
        ProviderValidateBo providerValidateBo = providerBiz.getProviderValidateInfo(userId);
        if (providerValidateBo == null) {
            return true;
        }
        if (StringUtils.isEmpty(providerValidateBo.getIdNum())) {
            return true;
        } else {
            if (providerValidateBo.getLatitude() != null) {
                return false;
            } else {
                return true;
            }
        }
    }

    @RequestMapping("/schedule")
    @ApiRequestMethod(requestSide = EnumApiRequestSide.CLIENT)
    @ApiOperation(value = "保存顾问排期")
    @ClientUserIdentify(mustLogin = true, requiredClientUserType = EnumRequiredUserType.SERVICE_PROVIDER)
    @ResponseBody
    public WebRespResult saveSchedule(String data) throws Exception {
        WebRespResult retJson = new WebRespResult();
        String userId = ApiRequestContext.getCurrent().getUserId();
        Preconditions.checkArgument(StringUtils.isNotEmpty(data), "排期不可以为空");
        Map<Date, List<Pair<Integer, Boolean>>> map = Maps.newHashMap();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String[] items = data.split(",");
        for (String item : items) {
            String[] value = item.split("->");
            List<Pair<Integer, Boolean>> slots = Lists.newArrayList();
            for (String vv : value[1].split("-")) {
                String[] value_freeze = vv.split("_");
                if (value_freeze.length == 1) {
                    slots.add(new ImmutablePair<>(Integer.parseInt(vv), true));
                } else if (value_freeze[1].equals(Constants.SCHEDULE_NO_FREEZE)) {
                    slots.add(new ImmutablePair<>(Integer.parseInt(value_freeze[0]), true));
                } else if (value_freeze[1].equals(Constants.SCHEDULE_FREEZE)) {
                    slots.add(new ImmutablePair<>(Integer.parseInt(value_freeze[0]), false));
                }
            }
            map.put(simpleDateFormat.parse(value[0]), slots);
        }
        providerBiz.saveSchedule(userId, map);
        retJson.setData("");
        retJson.setDesc("保存排期成功");
        return retJson;
    }

    @RequestMapping("/scheduled")
    @ApiRequestMethod(requestSide = EnumApiRequestSide.CLIENT)
    @ApiOperation(value = "顾问已排期数据")
    @ClientUserIdentify(mustLogin = true, requiredClientUserType = EnumRequiredUserType.SERVICE_PROVIDER)
    @ResponseBody
    public Map<String, List<ProviderScheduleShowVo>> scheduled() {
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDate localDate = LocalDate.now();
        ZonedDateTime zdt = localDate.atStartOfDay(zoneId);
        return providerBiz.getProviderSchedulesByUserId(ClientRequestContext.getCurrent().getUserId(), Date.from(zdt.toInstant()));
    }

    @RequestMapping(value = "/calendar/latest", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "我的日程表-未来若干天")
    @ApiRequestMethod(requestSide = EnumApiRequestSide.CLIENT)
    @ClientUserIdentify(mustLogin = true, requiredClientUserType = EnumRequiredUserType.SERVICE_PROVIDER)
    public List<TimeRangeVo> latestCalendar(Date start, Date end) throws BizException {
        String fromUid = ClientRequestContext.getCurrent().getUserId();
        return clientOrderBiz.getProgram(fromUid, start, end);
    }

    @RequestMapping(value = "/providerList", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取顾问列表")
    @ApiRequestMethod(requestSide = EnumApiRequestSide.CLIENT)
    public Map<String, Object> providerList(ProviderList providerList) throws BizException {
        Map<String, Object> result = Maps.newHashMap();
        result.put("data", providerBiz.getProvidersOrderbyEval(providerList));
        result.put("total", providerBiz.getProvidersOrderbyEvalCount(providerList));
        return result;
    }

    @RequestMapping(value = "/evalFirst2", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "顾问前两条评价")
    @ApiRequestMethod(requestSide = EnumApiRequestSide.CLIENT)
    public Map<String, Object> evalFirst2(String providerId) throws BizException {
        Map<String, Object> result = Maps.newHashMap();
        result.put("data", providerBiz.getEvalFirst2(providerId));
        result.put("eval", providerBiz.getEvalAllInfo(providerId));
        return result;
    }

    @RequestMapping(value = "/providerDetail", method = RequestMethod.GET)
    @ApiOperation(value = "显示顾问详细信息页面")
    @ApiRequestMethod(requestSide = EnumApiRequestSide.CLIENT)
    public String providerDetail(Model model, String providerId) throws BizException {
        model.addAttribute("detail", providerBiz.getProviderDetail(providerId));
        model.addAttribute("cats", providerBiz.getProviderServiceCat(providerId));
        model.addAttribute("providerId", providerId);
        return "provider/provider_detail";
    }

    @RequestMapping(value = "/introduction", method = RequestMethod.GET)
    @ApiOperation(value = "显示顾问个人简介页面")
    @ApiRequestMethod(requestSide = EnumApiRequestSide.CLIENT)
    @ClientUserIdentify(mustLogin = true, requiredClientUserType = EnumRequiredUserType.SERVICE_PROVIDER)
    public String introduction(Model model) throws BizException {
        model.addAttribute("intro", providerBiz.getIntroduction(ClientRequestContext.getCurrent().getUserId()));
        return "provider/profile";
    }

    @RequestMapping(value = "/save_introduction", method = RequestMethod.POST)
    @ApiOperation(value = "保存顾问个人简介")
    @ApiRequestMethod(requestSide = EnumApiRequestSide.CLIENT)
    @ClientUserIdentify(mustLogin = true, requiredClientUserType = EnumRequiredUserType.SERVICE_PROVIDER)
    @ResponseBody
    public WebRespResult saveIntroduction(String introduction) throws BizException {
        WebRespResult retJson = new WebRespResult();
        providerBiz.saveIntroduction(ClientRequestContext.getCurrent().getUserId(), introduction);
        retJson.setDesc("保存成功");
        return retJson;
    }

    @RequestMapping(value = "/health", method = RequestMethod.GET)
    @ApiOperation(value = "显示顾问健康报告页面")
    @ClientUserIdentify(mustLogin = true, requiredClientUserType = EnumRequiredUserType.SERVICE_PROVIDER)
    @ApiRequestMethod(requestSide = EnumApiRequestSide.CLIENT)
    public String health(Model model) throws BizException {
        model.addAttribute("health", providerBiz.getHealthInfo(ClientRequestContext.getCurrent().getUserId()));
        return "provider/health";
    }

    @RequestMapping(value = "/save_health", method = RequestMethod.POST)
    @ApiOperation(value = "保存顾问健康报告")
    @ClientUserIdentify(mustLogin = true, requiredClientUserType = EnumRequiredUserType.SERVICE_PROVIDER)
    @ApiRequestMethod(requestSide = EnumApiRequestSide.CLIENT)
    @ResponseBody
    public WebRespResult save_health(ProviderInfo providerInfo) throws BizException {
        WebRespResult retJson = new WebRespResult();
        providerInfo.setProviderUserId(ClientRequestContext.getCurrent().getUserId());
        providerInfo.setUpdateBy(ClientRequestContext.getCurrent().getUserId());
        providerInfo.setCreateBy(ClientRequestContext.getCurrent().getUserId());
        providerInfo.setUpdateTime(new Date());
        providerInfo.setCreateTime(new Date());
        providerBiz.saveHealth(providerInfo);
        retJson.setDesc("保存成功");
        return retJson;
    }

    @RequestMapping(value = "/cert", method = RequestMethod.GET)
    @ApiOperation(value = "显示顾问资质证书页面")
    @ClientUserIdentify(mustLogin = true, requiredClientUserType = EnumRequiredUserType.SERVICE_PROVIDER)
    @ApiRequestMethod(requestSide = EnumApiRequestSide.CLIENT)
    public String cert(Model model) throws BizException {
        model.addAttribute("certs", providerBiz.getProviderCerts(ClientRequestContext.getCurrent().getUserId()));
        return "provider/cert";
    }

    @RequestMapping(value = "/save_cert", method = RequestMethod.POST)
    @ApiOperation(value = "保存顾问资质证书")
    @ClientUserIdentify(mustLogin = true, requiredClientUserType = EnumRequiredUserType.SERVICE_PROVIDER)
    @ApiRequestMethod(requestSide = EnumApiRequestSide.CLIENT)
    @ResponseBody
    public WebRespResult save_cert(ProviderCertificationInfo providerCertificationInfo) throws BizException {
        WebRespResult retJson = new WebRespResult();
        providerCertificationInfo.setProviderId(ClientRequestContext.getCurrent().getUserId());
        providerCertificationInfo.setUpdateBy(ClientRequestContext.getCurrent().getUserId());
        providerCertificationInfo.setCreateBy(ClientRequestContext.getCurrent().getUserId());
        providerCertificationInfo.setUpdateTime(new Date());
        providerCertificationInfo.setCreateTime(new Date());
        providerBiz.saveCertification(providerCertificationInfo);
        retJson.setDesc("保存成功");
        return retJson;
    }

    @RequestMapping(value = "/add_cert", method = RequestMethod.GET)
    @ApiOperation(value = "现实新增资质证书页面")
    @ClientUserIdentify(mustLogin = true, requiredClientUserType = EnumRequiredUserType.SERVICE_PROVIDER)
    @ApiRequestMethod(requestSide = EnumApiRequestSide.CLIENT)
    public String addCert() throws BizException {
        return "/provider/add_cert";
    }

    @RequestMapping(value = "/journeyFeeAndTotal", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取上门费用")
    @ApiRequestMethod(requestSide = EnumApiRequestSide.CLIENT)
    public Map<String, Object> journeyFeeAndTotal(ProviderOrderFeeRequest providerOrderFeeRequest) throws BizException {
        Map<String, Object> result = Maps.newHashMap();

        if (StringUtils.isBlank(providerOrderFeeRequest.getProviderId())) {
            result.put("journeyFee", "");
            result.put("total", "");
        } else {
            BigDecimal journeyFee = providerBiz.getJourneyFee(providerOrderFeeRequest);
            result.put("journeyFee", journeyFee);
            SrvProviderSrvRel srvProviderSrvRel = providerBiz.getProviderAndSrv(providerOrderFeeRequest.getProviderId(), providerOrderFeeRequest.getServiceId());
            if (srvProviderSrvRel.getPrice() != null) {
                result.put("total", srvProviderSrvRel.getPrice().add(journeyFee));
            }
        }
        return result;
    }

    @RequestMapping(value = "/providerServiceCat", method = RequestMethod.GET)
    @ApiOperation(value = "获取分类下用户服务")
    @ApiRequestMethod(requestSide = EnumApiRequestSide.CLIENT)
    @ResponseBody
    public List<ProviderServiceInfo> providerServiceCat(String catId, String providerId) throws BizException {
        return providerBiz.getProviderServiceCatInfo(catId, providerId);
    }

    @RequestMapping(value = "/evalListdata", method = RequestMethod.GET)
    @ApiOperation(value = "显示顾问评价列表页")
    @ApiRequestMethod(requestSide = EnumApiRequestSide.CLIENT)
    @ResponseBody
    public Map<String, Object> evalListdata(EvalListRequest request) throws BizException {
        Map<String, Object> result = Maps.newHashMap();
        result.put("data", providerBiz.getEvalList(request));
        result.put("total", providerBiz.getEvalListCount(request));
        return result;
    }


    @RequestMapping(value = "/evalList", method = RequestMethod.GET)
    @ApiOperation(value = "显示顾问评价列表页")
    @ApiRequestMethod(requestSide = EnumApiRequestSide.CLIENT)
    public String evalList(String providerId, Model model) throws BizException {
        model.addAttribute("providerId", providerId);
        EvalListRequest request = new EvalListRequest();
        request.setProviderId(providerId);
        request.setCatId("1");
        model.addAttribute("cat1", providerBiz.getEvalListCount(request));
        request.setCatId("2");
        model.addAttribute("cat2", providerBiz.getEvalListCount(request));
        request.setCatId("3");
        model.addAttribute("cat3", providerBiz.getEvalListCount(request));
        request.setCatId("4");
        model.addAttribute("cat4", providerBiz.getEvalListCount(request));
        return "client/evalList";
    }
}
