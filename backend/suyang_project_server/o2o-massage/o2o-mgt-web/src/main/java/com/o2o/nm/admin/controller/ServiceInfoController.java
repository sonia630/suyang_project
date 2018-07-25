package com.o2o.nm.admin.controller;


import com.google.common.collect.Lists;
import com.o2o.nm.admin.entity.SrvProviderSrvRel;
import com.o2o.nm.admin.service.ServiceCategoryService;
import com.o2o.nm.admin.service.ServiceInfoService;
import com.o2o.nm.admin.vo.ServiceDefVo;
import com.o2o.nm.sys.controller.BaseController;
import com.o2o.nm.sys.util.ResConfig;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static cn.jeeweb.modules.sys.Constants.DEFAULT_PROVIDER_ID;

/**
 * <p>
 * 服务定义表 前端控制器
 * </p>
 *
 * @author warning5
 * @since 2018-03-05
 */
@Controller
@RequestMapping("/serviceInfo")
@ResConfig(name = "服务管理", id = "service")
public class ServiceInfoController extends BaseController {

    @Autowired
    ServiceInfoService serviceInfoService;
    @Autowired
    ServiceCategoryService serviceCategoryService;

    @ResConfig(name = "服务列表", id = "service1")
    @RequestMapping("/")
    public String list() {
        return "modules/admin/serviceList";
    }

    @RequestMapping(value = {"all_data"})
    @ResponseBody
    @ResConfig(name = "全部服务定义", id = "service2")
    public TableResponse data() {
        TableResponse tableResponse = new TableResponse();
        tableResponse.setData(serviceInfoService.getAllServiceDef());
        return tableResponse;
    }

    @RequestMapping(value = {"showAdd"})
    @ResConfig(name = "展现服务添加页面", id = "service3")
    public String showAdd(Model model) {
        model.addAttribute("cats", serviceCategoryService.getCats());
        return "modules/admin/serviceForm";
    }

    @RequestMapping(value = "showEdit")
    @ResConfig(name = "展现服务编辑页面", id = "service4")
    public String showEdit(Model model, @RequestParam("id") String id) {
        ServiceDefVo serviceDef = serviceInfoService.getServiceDef(null, id);
        model.addAttribute("cats", serviceCategoryService.getCats());
        model.addAttribute("service", serviceDef);
        return "modules/admin/serviceForm";
    }

    @RequestMapping(value = "save")
    @ResponseBody
    @ResConfig(name = "保存服务定义", id = "service5")
    public String save(ServiceDefVo serviceDefVo) {
        try {
            serviceInfoService.save(serviceDefVo);
        } catch (Exception e) {
            return getRenderJson(RENDER_JSON_ERROR, e.getMessage());
        }
        return getRenderJson(RENDER_JSON_SUCCESS, "保存成功");
    }

    @RequestMapping(value = "del")
    @ResConfig(name = "删除服务定义", id = "service6")
    public void delete(@RequestParam("ids") String ids, HttpServletResponse response) {
        String message = "删除服务成功";
        String code = RENDER_JSON_SUCCESS;
        if (StringUtils.isNotEmpty(ids)) {
            List<String> it = Lists.newArrayList(ids.split(","));
            if (it.size() != 0) {
                serviceInfoService.deleteBatchIds(it);
            }
        }
        renderJson(response, getRenderJson(code, message));
    }

    @RequestMapping(value = "enable")
    @ResConfig(name = "服务有效", id = "service7")
    public void enable(@RequestParam("ids") String ids, HttpServletResponse response) {
        String message = "操作成功";
        String code = RENDER_JSON_SUCCESS;
        if (StringUtils.isNotEmpty(ids)) {
            List<String> it = Lists.newArrayList(ids.split(","));
            if (it.size() != 0) {
                serviceInfoService.enableServices(it);
            }
        }
        renderJson(response, getRenderJson(code, message));
    }

    @RequestMapping(value = "disable")
    @ResConfig(name = "服务无效", id = "service8")
    public void disable(@RequestParam("ids") String ids, HttpServletResponse response) {
        String message = "操作成功";
        String code = RENDER_JSON_SUCCESS;
        if (StringUtils.isNotEmpty(ids)) {
            List<String> it = Lists.newArrayList(ids.split(","));
            if (it.size() != 0) {
                serviceInfoService.disableServices(it);
            }
        }
        renderJson(response, getRenderJson(code, message));
    }

    @RequestMapping(value = "showAssign")
    @ResConfig(name = "显示用户可以选择的服务列表", id = "service9")
    public String showAssign(Model model, @RequestParam("userId") String userId) {
        model.addAttribute("userId", userId);
        return "modules/admin/serviceAssignList";
    }

    @RequestMapping(value = "relation_direct")
    @ResConfig(name = "关联用户和服务", id = "service10")
    @ResponseBody
    public String relationDirect(@RequestParam("userId") String userId, @RequestParam("rId") String relationId) {
        try {
            serviceInfoService.relationUser(userId, relationId);
            return getSuccessJson("关联成功");
        } catch (Exception e) {
            logger.error("{}", e);
            return getErrorJson("关联失败");
        }
    }

    @RequestMapping(value = "relation_user")
    @ResConfig(name = "更新或创建用户与服务关系", id = "service11")
    @ResponseBody
    public String relationUser(SrvProviderSrvRel srvProviderSrvRel) {
        try {
            serviceInfoService.relationUser(srvProviderSrvRel);
            return getSuccessJson("关联成功");
        } catch (Exception e) {
            logger.error("{}", e);
            return getErrorJson("关联失败");
        }
    }

    @RequestMapping(value = "relationData")
    @ResConfig(name = "用户已关联与未关联的服务", id = "service12")
    @ResponseBody
    public TableResponse relationData(@RequestParam("userId") String userId) {
        TableResponse tableResponse = new TableResponse();
        tableResponse.setData(serviceInfoService.getUserRelationServices(userId));
        return tableResponse;
    }

    @RequestMapping(value = "detail")
    @ResConfig(name = "服务详细信息", id = "service13")
    public String detail(Model model, @RequestParam("userId") String userId, @Param("owner") String owner, @RequestParam("serviceId") String serviceId) {
        boolean defaultProvider = DEFAULT_PROVIDER_ID.equalsIgnoreCase(owner);
        ServiceDefVo serviceDefVo = serviceInfoService.getServiceDef(defaultProvider ? owner : userId, serviceId);
        if (defaultProvider) {
            serviceDefVo.getSrvProviderSrvRel().setId(null);
        }
        model.addAttribute("service", serviceDefVo);
        model.addAttribute("relation", !defaultProvider);
        model.addAttribute("userId", userId);
        model.addAttribute("serviceId", serviceId);
        return "modules/admin/serviceRelationForm";
    }

    @RequestMapping(value = "deRelation")
    @ResConfig(name = "取消用户和服务关联", id = "service14")
    @ResponseBody
    public String deRelation(@RequestParam("rId") Long relationId) {
        try {
            serviceInfoService.deRelation(relationId);
            return getSuccessJson("取消成功");
        } catch (Exception e) {
            logger.error("{}", e);
            return getErrorJson("取消失败");
        }
    }

}

