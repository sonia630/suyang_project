package com.o2o.nm.sys.controller;

import cn.jeeweb.modules.sys.Constants;
import cn.jeeweb.modules.sys.utils.UserUtils;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.o2o.nm.sys.entity.SysRole;
import com.o2o.nm.sys.exception.ExistException;
import com.o2o.nm.sys.service.RoleService;
import com.o2o.nm.sys.service.ServiceResponse;
import com.o2o.nm.sys.util.ResConfig;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 角色Controller
 */
@RequestMapping("/sys/role")
@Controller
@ResConfig(name = "角色管理", id = "role")
public class RoleController extends BaseController {

    @Autowired
    private RoleService roleService;

    @RequestMapping("/")
    @ResConfig(name = "显示角色列表页", id = "role1")
    public String list() {
        return "/modules/sys/roleList";
    }

    @RequestMapping("detail")
    @ResConfig(name = "显示角色详细信息", id = "role2")
    public String getDetail(Model model, @RequestParam("roleId") String roleId, @RequestParam("pid") String pid, @RequestParam("roleId") String text) {
        SysRole role = roleService.getRole(roleId, pid, text);
        model.addAttribute("sysRole", role);
        return "/modules/sys/roleForm";
    }

    @RequestMapping(value = {"tree"})
    @ResConfig(name = "获取角色树", id = "role3")
    public void tree(HttpServletResponse response) {
        List<SysRole> roles = UserUtils.getRoleList();
        StringBuilder builder = new StringBuilder();
        builder.append("{");
        builder.append("\"id\"");
        builder.append(":");
        builder.append("\"" + Constants.TOPROLEID + "\"");
        builder.append(",");
        builder.append("\"text\"");
        builder.append(":");
        builder.append("\"" + Constants.TOPROLENAME + "\"");
        builder.append(",");
        builder.append("\"state\"");
        builder.append(": {");
        builder.append("\"opened\"");
        builder.append(":");
        builder.append("\"true\"");
        builder.append(",");
        builder.append("\"selected\"");
        builder.append(":");
        builder.append("\"true\"");
        builder.append("}");
        builder.append(",");
        builder.append("\"children\"");
        builder.append(":");
        builder.append("[");
        buildRoleTree(roles, builder);
        builder.append("]");
        builder.append("}");
        renderJson(response, builder.toString());
    }

    private void buildRoleTree(List<SysRole> roles, StringBuilder builder) {
        for (int i = 0; i < roles.size(); i++) {
            SysRole sysRole = roles.get(i);
            builder.append("{");
            builder.append("\"id\"");
            builder.append(":");
            builder.append("\"" + sysRole.getId() + "\"");
            builder.append(",");
            builder.append("\"text\"");
            builder.append(":");
            builder.append("\"" + sysRole.getName() + "\"");
            if (sysRole.getType() == 1) {
                // 角色不显示图标
                builder.append(",");
                builder.append("\"icon\"");
                builder.append(":");
                builder.append("false");
            }
            if (sysRole.getChildren().size() != 0) {
                builder.append(",");
                builder.append("\"children\"");
                builder.append(":");
                builder.append("[");
                buildRoleTree(sysRole.getChildren(), builder);
                builder.append("]");
            }
            builder.append("}");
            if (i + 1 != roles.size()) {
                builder.append(",");
            }
        }
    }

    @RequestMapping(value = "move")
    @ResponseBody
    @ResConfig(name = "调整角色树", id = "role4")
    public String move(@RequestParam("_old_parent") String _old_parent,
                       @RequestParam("_new_parent") String _new_parent,
                       @RequestParam("_moved_id") String _moved_id) {
        ServiceResponse serviceResponse = roleService.move(_moved_id, _old_parent, _new_parent);
        return serviceResponse.json();
    }

    @RequestMapping(value = "save")
    @ResponseBody
    @ResConfig(name = "保存或编辑角色", id = "role5")
    public Map<String, Object> save(SysRole sysRole) {
        Map<String, Object> result = Maps.newHashMap();
        String saveType = "保存角色";
        if ("0".equals(sysRole.getType())) {
            saveType = "保存分类";
        }
        try {
            roleService.saveRole(sysRole);
            result.put("code", RENDER_JSON_SUCCESS);
            result.put("id", sysRole.getId());
            result.put("name", sysRole.getName());
            result.put("type", sysRole.getType());
            result.put("message", saveType + "'" + sysRole.getName() + "'成功");
        } catch (ExistException e) {
            result.put("code", RENDER_JSON_ERROR);
            result.put("message", saveType + "'" + sysRole.getName() + "'失败, 角色名已存在");
        }
        return result;
    }

    @RequestMapping(value = "delete")
    @ResConfig(name = "删除角色", id = "role6")
    public void delete(@RequestParam("id") String id, HttpServletResponse response) {
        Collection<String> unDelIds = roleService.deleteRole(id);
        if (unDelIds.size() == 0) {
            renderText(response, "删除角色成功");
        } else {
            renderText(response, "角色 " + unDelIds.toString() + " 无法删除,已被使用!");
        }
    }

    @RequestMapping(value = "assign")
    @ResConfig(name = "显示角色", id = "role7")
    public String assign() {
        return "/modules/sys/roleAssign";
    }

    @RequestMapping(value = "assignIt")
    @ResponseBody
    @ResConfig(name = "分配权限到角色", id = "role8")
    public String assignIt(HttpServletRequest request,
                           @RequestParam(value = "selected", required = false) String ids,
                           @RequestParam(value = "roleId", required = false) String roleId) {
        if (StringUtils.isNotEmpty(ids) && StringUtils.isNotEmpty(roleId)) {
            roleService.assign(roleId, ids.split(","), request.getParameter("type"));
        }
        return getRenderJson(RENDER_JSON_SUCCESS, "分配成功");
    }

    @RequestMapping(value = "deleteIt")
    @ResponseBody
    @ResConfig(name = "删除角色已分配权限", id = "role9")
    public String deleteIt(HttpServletRequest request,
                           @RequestParam(value = "selected", required = false) String ids,
                           @RequestParam(value = "roleId", required = false) String roleId) {
        if (StringUtils.isNotEmpty(ids) && StringUtils.isNotEmpty(roleId)) {
            String[] funs = ids.split(",");
            List<String> funIds = Lists.newArrayListWithCapacity(funs.length);
            for (int i = 0; i < funs.length; i++) {
                funIds.add(funs[i]);
            }
            roleService.deAssign(roleId, funIds, request.getParameter("type"));
        }
        return getRenderJson(RENDER_JSON_SUCCESS, "删除成功");
    }

    @RequestMapping(value = "menus")
    @ResponseBody
    @ResConfig(name = "获取角色包含的菜单树", id = "role10")
    public void menus(@RequestParam(value = "id", required = false) String roleId, HttpServletResponse response) {
        if (StringUtils.isNotEmpty(roleId)) {
            renderJson(response, JSON.toJSONString(roleService.getMenusAsTreeByRole(roleId)));
        } else {
            renderJson(response, roleIdEmpty(response));
        }
    }

    @RequestMapping(value = "resources")
    @ResConfig(name = "获取角色包含的资源树", id = "role11")
    public void resources(@RequestParam(value = "id", required = false) String roleId, HttpServletResponse response) {
        if (StringUtils.isNotEmpty(roleId)) {
            renderJson(response, JSON.toJSONString(roleService.getResourcesAsTreeByRole(roleId)));
        } else {
            renderJson(response, roleIdEmpty(response));
        }
    }

    @RequestMapping(value = "excludeMenus")
    @ResConfig(name = "获取角色未包含的菜单树", id = "role12")
    public void excludeMenus(@RequestParam(value = "roleId", required = false) String roleId, HttpServletResponse response) {
        if (StringUtils.isNotEmpty(roleId)) {
            renderJson(response, JSON.toJSONString(roleService.getExcludeMenusAsTreeByRole(roleId)));
        } else {
            renderJson(response, roleIdEmpty(response));
        }
    }

    @RequestMapping(value = "excludeResources")
    @ResConfig(name = "获取角色未包含的资源树", id = "role13")
    public void excludeResources(@RequestParam(value = "roleId", required = false) String roleId, HttpServletResponse response) {
        if (StringUtils.isNotEmpty(roleId)) {
            renderJson(response, JSON.toJSONString(roleService.getExcludeResourcesAsTreeByRole(roleId)));
        } else {
            renderJson(response, roleIdEmpty(response));
        }
    }

    @RequestMapping(value = "permission_tree")
    @ResConfig(name = "现实权限或资源树", id = "role14")
    public String sysFunTree(Model model, @RequestParam("id") String roleId, @RequestParam("type") String type) {
        model.addAttribute("roleId", roleId);
        model.addAttribute("type", type);
        if ("menu".equals(type)) {
            model.addAttribute("excludeUrl", "excludeMenus");
        } else {
            model.addAttribute("excludeUrl", "excludeResources");
        }
        return "/modules/sys/permissionTree";
    }

    private String roleIdEmpty(HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        return getRenderJson(RENDER_JSON_ERROR, "角色ID不能为空.");
    }
}