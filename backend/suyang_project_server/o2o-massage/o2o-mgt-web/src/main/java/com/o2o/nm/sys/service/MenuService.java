package com.o2o.nm.sys.service;

import com.baomidou.mybatisplus.service.IService;
import com.o2o.nm.entity.UserInfo;
import com.o2o.nm.sys.entity.Permission;

import java.util.List;
import java.util.Map;

public interface MenuService extends IService<Permission> {

    /**
     * 通过用户ID查找菜单
     */
    List<Permission> findMenuByUser(UserInfo userInfo);

    /**
     * 通过角色查找菜单
     */
    List<Permission> findMenuByRoleId(String roleId);

    List<Permission> getAllMenus();

    void deleteMenu(String id);

    void sortPermission(String[] ids, Integer[] sequences);

    Permission getMenuById(String id);

    void saveMenu(Permission permission);

    List<Map<String, Object>> getMenuTree(String selectIds, String topName, List<Permission> menus);

    List<Map<String, Object>> getMenuTreeRecursion(String selectIds, String topName, List<Permission> menus);

    List<Permission> getTreeTableMenus();

    String getMenuList();
}
