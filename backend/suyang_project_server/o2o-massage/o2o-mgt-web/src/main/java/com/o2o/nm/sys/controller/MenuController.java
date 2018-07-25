package com.o2o.nm.sys.controller;

import cn.jeeweb.modules.sys.Constants;
import cn.jeeweb.modules.sys.utils.UserUtils;
import com.o2o.nm.sys.entity.Permission;
import com.o2o.nm.sys.service.MenuService;
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
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping({"/sys/menu"})
@ResConfig(name = "菜单管理", id = "menu")
public class MenuController extends BaseController {
    @Autowired
    private MenuService menuService;
    public static final String menu_root = "/sys/menu/";
    public static final String redirect_menu_root = "redirect:" + menu_root;

    @RequestMapping("/")
    @ResConfig(name = "菜单列表", id = "menu1")
    public String list(Model model) {
        model.addAttribute("list", this.menuService.getTreeTableMenus());
        return "/modules/sys/menuList";
    }

    @RequestMapping({"form"})
    @ResConfig(name = "菜单表单", id = "menu2")
    public String form(HttpServletRequest request, Model model) {
        String id = request.getParameter("id");
        String pId = request.getParameter("pId");
        Permission permission = null;
        if (StringUtils.isNotEmpty(pId)) {
            permission = new Permission();
            permission.setParent(this.menuService.getMenuById(pId));
        } else if (StringUtils.isNotEmpty(id)) {
            permission = this.menuService.getMenuById(id);
        }

        model.addAttribute("menu", permission);
        return "/modules/sys/menuForm";
    }

    @RequestMapping({"save"})
    @ResConfig(name = "保存菜单", id = "menu3")
    public String save(Permission permission) {
        this.menuService.saveMenu(permission);
        return redirect_menu_root;
    }

    @RequestMapping({"delete"})
    @ResConfig(name = "删除菜单", id = "menu4")
    public String delete(@RequestParam("id") String id) {
        this.menuService.deleteMenu(id);
        return redirect_menu_root;
    }

    @RequestMapping({"left"})
    @ResConfig(name = "左侧菜单列表", id = "menu5")
    public void leftMenus(HttpServletResponse response) {
        renderText(response, menuService.getMenuList());
    }

    @RequestMapping({"updateSort"})
    @ResConfig(name = "保存菜单排序", id = "menu6")
    public String updateSort(HttpServletRequest request, Model model) {
        String[] ids = request.getParameterValues("ids");
        this.menuService.sortPermission(ids, this.getParaValuesToInt("sequence", request));
        return redirect_menu_root;
    }

    @RequestMapping({"treeData"})
    @ResponseBody
    @ResConfig(name = "获取菜单树", id = "menu7")
    public List<Map<String, Object>> treeData(String selectIds) {
        return menuService.getMenuTreeRecursion(selectIds, Constants.TOPMENUNAME, UserUtils.getMenuList());
    }
}
