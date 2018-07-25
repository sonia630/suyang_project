package com.o2o.nm.sys.controller;


import cn.jeeweb.modules.sys.Constants;
import cn.jeeweb.modules.sys.utils.UserUtils;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.o2o.nm.admin.service.ProviderInfoService;
import com.o2o.nm.entity.UserInfo;
import com.o2o.nm.entity.UserType;
import com.o2o.nm.sys.entity.SysRole;
import com.o2o.nm.sys.service.UserService;
import com.o2o.nm.sys.util.ResConfig;
import com.o2o.nm.sys.vo.SearchUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 系统用户基本信息表 前端控制器
 * </p>
 *
 * @author warning5
 * @since 2018-02-22
 */
@Controller
@RequestMapping("/sys/user")
@ResConfig(name = "用户管理", id = "user")
public class UserController extends BaseController {

    static Map<String, String> sortMapping = Maps.newHashMap();

    static {
        sortMapping.put("1", "userId");
        sortMapping.put("2", "name");
        sortMapping.put("3", "login_date");
    }

    @Autowired
    UserService userService;
    @Autowired
    ProviderInfoService providerInfoService;

    @RequestMapping("/")
    @ResConfig(name = "显示用户列表页", id = "user1")
    public String list() {
        return "/modules/sys/userList";
    }

    @Override
    protected Page<UserInfo> getPageData(PagerSort pagerSort, HttpServletRequest request) {
        Page<UserInfo> page = new Page<>(pagerSort.getCurrent(), pagerSort.getCount());
        return userService.selectUserPage(page, getSearchUser(request), pagerSort.getOffset());
    }

    @Override
    protected String getSortName(String sort) {
        return sortMapping.get(sort);
    }


    @RequestMapping(value = {"lookup"})
    @ResConfig(name = "显示用户查询页面", id = "user3")
    public String lookup(HttpServletRequest request, Model model, @RequestParam(value = "orgId", required = false) String orgId) {
        model.addAttribute("orgId", orgId);
        model.addAttribute("includeUser", request.getParameter("includeUser"));
        return "/modules/sys/userLookup";
    }

    @RequestMapping(value = "showAdd")
    @ResConfig(name = "显示新增用户页", id = "user4")
    public String showAdd(Model model) {
        model.addAttribute("userInfo", new UserInfo());
        return "/modules/sys/userForm";
    }

    @RequestMapping(value = "showToolbar")
    @ResConfig(name = "显示用户查询状态栏", id = "user5")
    public String showToolbar() {
        return "/modules/sys/userSearchToolbar";
    }

    @RequestMapping(value = "showAssignRole")
    @ResConfig(name = "显示赋予用户角色页面", id = "user6")
    public String showAssignRole(Model model, @RequestParam("id") String id) {
        model.addAttribute("userId", id);
        List<SysRole> roles = userService.getUserRoles(id);
        model.addAttribute("roles", roles);
        model.addAttribute("roleIds", roles.stream().map(role -> role.getId()).collect(Collectors.joining(",")));
        return "/modules/sys/userAssignRole";
    }

    @RequestMapping(value = "assignRole")
    @ResConfig(name = "赋予用户角色", id = "user7")
    public void assignRole(HttpServletResponse response,
                           @RequestParam(value = "roles") String roles,
                           @RequestParam(value = "userId") String userId) {
        userService.assignRoles(userId, roles);
        renderJson(response, getRenderJson(RENDER_JSON_SUCCESS, "保存成功!"));
    }

    @RequestMapping(value = "save")
    @ResponseBody
    @ResConfig(name = "保存或修改用户名和密码", id = "user8")
    public String save(UserInfo userInfo) {
        try {
            userService.saveUser(userInfo);
        } catch (Exception e) {
            return getRenderJson(RENDER_JSON_ERROR, e.getMessage());
        }
        return getRenderJson(RENDER_JSON_SUCCESS, "保存成功");
    }

    @RequestMapping(value = "update")
    @ResponseBody
    @ResConfig(name = "保存修改用户", id = "user15")
    public String update(UserInfo userInfo) {
        try {
            userService.updateById(userInfo);
        } catch (Exception e) {
            return getRenderJson(RENDER_JSON_ERROR, e.getMessage());
        }
        return getRenderJson(RENDER_JSON_SUCCESS, "保存成功");
    }

    @RequestMapping(value = "showEdit")
    @ResConfig(name = "显示用户编辑页面", id = "user9")
    public String showEdit(Model model, @RequestParam("id") String userId) {
        UserInfo userInfo = userService.getUserById(userId);
        model.addAttribute("userInfo", userInfo);
        return "/modules/sys/userForm";
    }

    @RequestMapping(value = "d")
    @ResConfig(name = "删除用户", id = "user10")
    public void delete(@RequestParam("ids") String ids, HttpServletResponse response) {
        String message = "删除用户成功";
        String code = RENDER_JSON_SUCCESS;
        List<String> it = Lists.newArrayList();
        if (StringUtils.isNotEmpty(ids)) {
            for (String id : ids.split(",")) {
                if (UserUtils.getUser().getId().equals(id)) {
                    message = "删除[" + id + "]失败, 不允许删除当前用户";
                    code = RENDER_JSON_ERROR;
                } else if (Constants.SUPER_ADMIN.equals(id)) {
                    message = "删除" + id + "失败, 不允许删除超级管理员用户";
                    code = RENDER_JSON_ERROR;
                } else {
                    it.add(id);
                }
            }
        }
        if (it.size() != 0) {
            userService.deleteUsers(it);
        }
        renderJson(response, getRenderJson(code, message));
    }

    @RequestMapping(value = "/lock")
    @ResConfig(name = "锁定用户", id = "user11")
    public void lock(@RequestParam("ids") String ids, HttpServletResponse response) {
        String message = "锁定用户成功";
        String code = RENDER_JSON_SUCCESS;
        List<String> it = Lists.newArrayList();
        if (StringUtils.isNotEmpty(ids)) {
            for (String id : ids.split(",")) {
                if (UserUtils.getUser().getId().equals(id)) {
                    message = "锁定[" + id + "]失败, 不允许删除当前用户";
                    code = RENDER_JSON_ERROR;
                } else if (Constants.SUPER_ADMIN.equals(id)) {
                    message = "锁定" + id + "失败, 不允许删除超级管理员用户";
                    code = RENDER_JSON_ERROR;
                } else {
                    it.add(id);
                }
            }
        }
        if (it.size() != 0) {
            try {
                userService.lockUsers(it);
            } catch (Exception e) {
                renderJson(response, getErrorRenderJson(e.getMessage(), HttpServletResponse.SC_BAD_REQUEST, response));
            }
        }
        renderJson(response, getRenderJson(code, message));
    }

    @RequestMapping(value = "/unlock")
    @ResConfig(name = "解锁用户", id = "user12")
    public void unlock(@RequestParam("ids") String ids, HttpServletResponse response) {
        String message = "解锁用户成功";
        String code = RENDER_JSON_SUCCESS;
        if (StringUtils.isNotEmpty(ids)) {
            List<String> it = Lists.newArrayList(ids.split(","));
            if (it.size() != 0) {
                try {
                    userService.unlockUsers(it);
                } catch (Exception e) {
                    renderJson(response, getErrorRenderJson(e.getMessage(), HttpServletResponse.SC_BAD_REQUEST, response));
                }
            }
        }
        renderJson(response, getRenderJson(code, message));
    }

    @RequestMapping(value = "/detail")
    @ResConfig(name = "用户详细信息", id = "user13")
    public String detail(Model model, @RequestParam("id") String userId) {
        UserInfo userInfo = userService.selectById(userId);
        if (userInfo != null) {
            boolean provider = (userInfo.getUserType() & UserType.PROVIDER.getCode()) == UserType.PROVIDER.getCode();
            model.addAttribute("provider", provider);
            model.addAttribute("user", (userInfo.getUserType() & UserType.CUSTOMER.getCode()) == UserType.CUSTOMER.getCode());
        }
        model.addAttribute("userId", userId);
        return "/modules/sys/userDetail";
    }

    @RequestMapping(value = "/cDetail")
    @ResConfig(name = "客户详细信息", id = "user14")
    public String cDetail(Model model, @RequestParam("id") String userId) {
        UserInfo userInfo = userService.selectById(userId);
        if (userInfo != null) {
            boolean provider = (userInfo.getUserType() & UserType.PROVIDER.getCode()) == UserType.PROVIDER.getCode();
            model.addAttribute("provider", provider);
            model.addAttribute("user", (userInfo.getUserType() & UserType.CUSTOMER.getCode()) == UserType.CUSTOMER.getCode());
            model.addAttribute("detail", userInfo);
        }
        return "/modules/sys/userDetailForm";
    }

    private SearchUser getSearchUser(HttpServletRequest request) {
        SearchUser searchUser = new SearchUser();
        searchUser.setUserName(request.getParameter("name"));
        searchUser.setStartDate(request.getParameter("startdate"));
        searchUser.setFinishDate(request.getParameter("finishdate"));
        searchUser.setOrgId(request.getParameter("orgId"));
        searchUser.setIncludeUser(request.getParameter("includeUser"));
        searchUser.setUserType(request.getParameter("userType"));
        return searchUser;
    }
}

