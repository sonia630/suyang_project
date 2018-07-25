package com.o2o.nm.sys.controller;

import cn.jeeweb.core.utils.SpringContextHolder;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.o2o.nm.sys.entity.Permission;
import com.o2o.nm.sys.service.ResService;
import com.o2o.nm.sys.service.ServiceResponse;
import com.o2o.nm.sys.util.ResConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

@RequestMapping("/sys/res")
@Controller
@ResConfig(name = "资源管理", id = "res")
public class ResController extends BaseController {

    @Resource
    private ResService resService;

    @RequestMapping("/")
    @ResConfig(name = "资源列表", id = "res1")
    public String list() {
        return "/modules/sys/resList";
    }

    @RequestMapping("tree")
    @ResponseBody
    @ResConfig(name = "资源树形数据", id = "res2")
    public List<Map<String, Object>> tree() {
        return resService.getResources();
    }

    @RequestMapping("showAdd")
    @ResConfig(name = "展现新增资源表单", id = "res3")
    public String showAdd(Model model, @RequestParam("pText") String pText, @RequestParam("pId") String pId) {
        model.addAttribute("pText", pText);
        model.addAttribute("pId", pId);
        return "/modules/sys/resForm";
    }

    @RequestMapping(value = "roleAssignRes")
    @ResConfig(name = "赋予角色资源", id = "res4")
    public String assign() {
        return "/modules/sys/roleAssignRes";
    }

    @RequestMapping(value = "move")
    @ResConfig(name = "资源树结构调整", id = "res8")
    public void move(@RequestParam("_old_parent") String _old_parent,
                     @RequestParam("_new_parent") String _new_parent,
                     @RequestParam("_moved_id") String _moved_id,
                     HttpServletResponse response) {
        ServiceResponse serviceResponse = resService.move(_moved_id, _new_parent);
        renderJson(response, serviceResponse.json());
    }

    @RequestMapping(value = "delete")
    @ResConfig(name = "删除资源", id = "res5")
    public void delete(@RequestParam() String id, HttpServletResponse response) {
        try {
            resService.delete(id);
            renderJson(response, getRenderJson(RENDER_JSON_SUCCESS, "删除成功"));
        } catch (Exception e) {
            renderJson(response, getErrorRenderJson(e.toString(), HttpServletResponse.SC_INTERNAL_SERVER_ERROR, response));
        }
    }

    @RequestMapping(value = "save")
    @ResConfig(name = "保存资源", id = "res6")
    public void save(Permission permission, HttpServletResponse response) {
        permission.setMenu(0);
        resService.save(permission);
        renderText(response, "保存资源'" + permission.getName() + "'成功");
    }

    @RequestMapping(value = {"showEdit"})
    @ResConfig(name = "显示编辑资源", id = "res7")
    public String shwoEdit(Model model, @RequestParam("id") String id, @RequestParam("pText") String pText) {
        Permission permission = resService.getResource(id);
        model.addAttribute("pText", pText);
        model.addAttribute("pId", permission.getParentId());
        model.addAttribute("res", permission);
        return "/modules/sys/resForm";
    }


    @RequestMapping("empty_res")
    @ResponseBody
    public Map<String, String> emptyRes() {
        Map<String, String> result = Maps.newHashMap();
        result.put("message", "保存成功");
        resService.emptyResources();
        return result;
    }

    @RequestMapping("build_res")
    @ResponseBody
    public Map<String, String> buildRes() {
        ApplicationContext applicationContext = SpringContextHolder.getApplicationContext();
        String[] names = applicationContext.getBeanNamesForType(BaseController.class);
        for (String name : names) {
            Object bean = applicationContext.getBean(name);
            Class<?> clazz = bean.getClass();
            ResConfig classResConfig = clazz.getAnnotation(ResConfig.class);
            if (classResConfig != null) {
                List<Permission> resources = Lists.newArrayList();
                String title = classResConfig.name();
                String pId = classResConfig.id();
                RequestMapping requestMapping = clazz.getAnnotation(RequestMapping.class);
                String[] urls = requestMapping.value();
                String url = null;
                if (urls != null) {
                    url = urls[0];
                    if (!url.endsWith("/")) {
                        url = url + "/";
                    }
                }
                Permission parent = new Permission();
                parent.setId(pId);
                parent.setMenu(0);
                parent.setName(title);
                parent.setUrl("#");
                parent.setShow((short) 1);
                resources.add(parent);
                for (Method method : clazz.getMethods()) {
                    Permission permission = new Permission();
                    ResConfig methodResConfig = method.getAnnotation(ResConfig.class);
                    RequestMapping methodRequest = method.getAnnotation(RequestMapping.class);
                    if (methodResConfig == null && methodRequest != null) {
                        throw new RuntimeException(method + " need @ResConfig");
                    }

                    String[] values = methodRequest.value();
                    if (values != null && values.length > 1) {
                        Map<String, String> result = Maps.newHashMap();
                        result.put("message", clazz.getName() + "-> " + method.getName() + " has more than one RequestMapping value of [" + values + "]");
                        return result;
                    }

                    String m_url = values[0];
                    if (m_url.startsWith("/") && !Strings.isNullOrEmpty(url)) {
                        m_url = m_url.substring(1);
                    }

                    permission.setUrl(Strings.isNullOrEmpty(url) ? m_url : url + m_url);
                    permission.setId(methodResConfig.id());
                    permission.setName(methodResConfig.name());
                    permission.setMenu(0);
                    permission.setShow((short) 1);
                    permission.setParentId(pId);
                    resources.add(permission);
                }
                resService.savePermissions(resources);
            }
        }
        Map<String, String> result = Maps.newHashMap();
        result.put("message", "保存成功");
        return result;
    }
}
