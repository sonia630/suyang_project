package com.o2o.nm.admin.controller;


import com.o2o.nm.admin.service.ProviderInfoService;
import com.o2o.nm.admin.vo.ProviderDetail;
import com.o2o.nm.sys.controller.BaseController;
import com.o2o.nm.sys.util.ResConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 服务提供者基本信息表 前端控制器
 * </p>
 *
 * @author warning5
 * @since 2018-03-11
 */
@Controller
@RequestMapping("/providerInfo")
@ResConfig(name = "顾问管理", id = "provider")
public class ProviderInfoController extends BaseController {

    @Autowired
    private ProviderInfoService providerInfoService;

    @RequestMapping(value = "/pDetail")
    @ResConfig(name = "顾问详细信息", id = "provider1")
    public String pDetail(Model model, @RequestParam("id") String userId) {
        model.addAttribute("detail", providerInfoService.getProviderDetailInfo(userId));
        return "/modules/admin/providerForm";
    }

    @RequestMapping(value = "/save")
    @ResponseBody
    @ResConfig(name = "保存顾问信息", id = "provider2")
    public String save(ProviderDetail providerDetail, HttpServletResponse response) {
        try {
            providerInfoService.save(providerDetail);
            return getSuccessJson("保存成功");
        } catch (Exception e) {
            logger.error("{}", e);
            return getErrorRenderJson("保存失败", HttpServletResponse.SC_INTERNAL_SERVER_ERROR, response);
        }
    }

    @RequestMapping(value = "/audit")
    @ResponseBody
    @ResConfig(name = "审核顾问通过过", id = "provider3")
    public String audit(@RequestParam("userId") String userId, HttpServletResponse response) {
        try {
            providerInfoService.audit(userId);
            return getSuccessJson("审核成功");
        } catch (Exception e) {
            logger.error("{}", e);
            return getErrorRenderJson("审核失败", HttpServletResponse.SC_INTERNAL_SERVER_ERROR, response);
        }
    }
}

