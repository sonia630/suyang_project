package com.o2o.nm.sys.controller;

import com.google.common.base.Joiner;
import com.google.common.collect.Maps;
import com.o2o.nm.sys.entity.SysOrg;
import com.o2o.nm.sys.entity.SysRole;
import com.o2o.nm.sys.service.OrgService;
import com.o2o.nm.sys.service.ServiceResponse;
import com.o2o.nm.sys.util.ResConfig;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequestMapping("/sys/org")
@Controller
@ResConfig(name = "机构管理", id = "org")
public class OrgController extends BaseController {

    @Autowired
    private OrgService orgService;

    @RequestMapping("/")
    @ResConfig(name = "机构列表页", id = "org1")
    public String list() {
        return "/modules/sys/orgList";
    }

    @RequestMapping(value = "showAssignRole")
    @ResConfig(name = "显示赋予机构角色页面", id = "org2")
    public String showAssignRole(Model model, @RequestParam(value = "orgId", required = false) String orgId) {
        model.addAttribute("orgId", orgId);
        List<SysRole> roles = orgService.getOrgRoles(orgId);
        model.addAttribute("roles", roles);
        model.addAttribute("roleIds", roles.stream().map(role -> role.getId()).collect(Collectors.joining(",")));
        return "/modules/sys/orgAssignRole";
    }

    @RequestMapping(value = "assignRole")
    @ResponseBody
    @ResConfig(name = "赋予机构角色", id = "org3")
    public String assignRole(@RequestParam(value = "roles") String roles, @RequestParam("orgId") String orgId) {
        orgService.assignRoles(orgId, roles);
        return getRenderJson(RENDER_JSON_SUCCESS, "保存成功!");
    }

    @RequestMapping(value = "save")
    @ResponseBody
    @ResConfig(name = "保存或更新机构", id = "org4")
    public Map<String, String> save(SysOrg sysOrg, @RequestParam("save.type") String save_type) {
        Map<String, String> result = Maps.newHashMap();

        result.put("id", sysOrg.getId());
        result.put("name", sysOrg.getName());
        try {
            orgService.save(sysOrg, save_type);
            result.put("code", RENDER_JSON_SUCCESS);
            result.put("message", "更新机构'" + sysOrg.getName() + "'成功");
        } catch (Exception e) {
            logger.error("{}", e);
            result.put("code", RENDER_JSON_ERROR);
            result.put("message", e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "detail")
    @ResponseBody
    @ResConfig(name = "获取机构信息", id = "org5")
    public String getDetail(@RequestParam("orgId") String orgId) {
        SysOrg sysOrg = orgService.getOrg(orgId);
        if (sysOrg != null) {
            return ("{\"orgId\":\"" + sysOrg.getId() + "\",\"name\":\"" + sysOrg.getName() + "\"}");
        }
        return "";
    }

    @RequestMapping(value = "assignUser")
    @ResponseBody
    @ResConfig(name = "赋予机构用户", id = "org6")
    public String assignUser(@RequestParam("userIds") String userIds, @RequestParam("orgId") String orgId, HttpServletResponse response) {
        String[] uIds = userIds.split(",");
        if (uIds != null && uIds.length != 0) {
            Long count = orgService.assignUser(uIds, orgId);
            Map<String, Object> param = Maps.newHashMap();
            param.put("code", RENDER_JSON_SUCCESS);
            param.put("message", "分配成功!");
            param.put("userCount", count);
            return getRenderJson(param);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return getRenderJson(RENDER_JSON_ERROR, "分配失败!");
        }
    }

    @RequestMapping(value = "delAssignedUser")
    @ResponseBody
    @ResConfig(name = "赋予机构角色", id = "org7")
    public String delAssignedUser(@RequestParam("userIds") String userIds, @RequestParam("orgId") String orgId, HttpServletResponse response) {
        String[] uIds = userIds.split(",");
        if (uIds != null && uIds.length != 0) {
            Long count = orgService.delAssignedUser(uIds, orgId);
            Map<String, Object> param = Maps.newHashMap();
            param.put("code", RENDER_JSON_SUCCESS);
            param.put("message", "操作成功!");
            param.put("userCount", count);
            return getRenderJson(param);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return getRenderJson(RENDER_JSON_ERROR, "提交的用户无效!");
        }
    }

    @RequestMapping(value = "move")
    @ResponseBody
    @ResConfig(name = "调整机构树", id = "org8")
    public String move(@RequestParam("_old_parent") String _old_parent,
                       @RequestParam("_new_parent") String _new_parent,
                       @RequestParam("_moved_id") String _moved_id) {

        ServiceResponse serviceResponse = orgService.move(_moved_id, _old_parent, _new_parent);
        return serviceResponse.json();
    }

    @RequestMapping(value = "delete")
    @ResponseBody
    @ResConfig(name = "删除机构", id = "org9")
    public String delete(@RequestParam(value = "ids", required = false) String ids, HttpServletResponse response) {

        if (StringUtils.isEmpty(ids)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return getRenderJson(RENDER_JSON_ERROR, "无法获取机构!");
        } else {
            Collection<String> orgIds = orgService.delete(Arrays.asList(ids.split(",")));
            if (orgIds.size() != 0) {
                return getRenderJson(RENDER_JSON_ERROR, Joiner.on(",").join(orgIds));
            } else {
                return getRenderJson(RENDER_JSON_SUCCESS, "删除成功!");
            }
        }
    }

    @RequestMapping(value = "treeData")
    @ResponseBody
    @ResConfig(name = "获取机构树", id = "org10")
    public List<Map<String, Object>> treeData(@RequestParam("id") String pid) {
        return orgService.getOrgTree(pid);
    }
}
