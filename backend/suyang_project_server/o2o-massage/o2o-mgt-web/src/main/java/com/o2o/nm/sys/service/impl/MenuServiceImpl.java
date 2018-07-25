package com.o2o.nm.sys.service.impl;

import cn.jeeweb.core.utils.StringUtils;
import cn.jeeweb.modules.sys.Constants;
import cn.jeeweb.modules.sys.utils.UserUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.o2o.nm.entity.UserInfo;
import com.o2o.nm.sys.entity.Permission;
import com.o2o.nm.sys.mapper.MenuMapper;
import com.o2o.nm.sys.service.MenuService;
import com.o2o.nm.sys.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static cn.jeeweb.modules.sys.Constants.TOPMENUNAME;
import static cn.jeeweb.modules.sys.Constants.TOPSTRINGID;

@Service
public class MenuServiceImpl extends TreeServiceImpl<MenuMapper, Permission> implements MenuService {

    @Autowired
    private PermissionService permissionService;

    @Override
    public List<Permission> findMenuByUser(UserInfo userInfo) {
        if (userInfo.isAdmin()) {
            return getMenuTreeList(baseMapper.getAllMenus());
        }
        return getMenuTreeList(permissionService.getPermissionsByUserId(userInfo.getUserId(), Constants.PERMISSION_MENU));
    }

    @Override
    public List<Permission> findMenuByRoleId(String roleId) {
        return baseMapper.findMenuByRoleId(roleId);
    }

    @Override
    public List<Permission> getAllMenus() {
        return baseMapper.getAllMenus();
    }

    @Override
    public List<Permission> getTreeTableMenus() {
        List<Permission> permissions = Lists.newArrayList();
        buildTreeTable(UserUtils.getMenuList(), permissions);
        return permissions;
    }

    @Override
    public String getMenuList() {
        StringBuilder builder = new StringBuilder();
        buildMenuTree(UserUtils.getMenuList(), builder);
        return builder.toString();
    }

    private void buildTreeTable(List<Permission> permissions, List<Permission> result) {
        permissions.stream().forEach(menu -> {
            result.add(menu);
            if (menu.getChildren() != null && menu.getChildren().size() != 0) {
                buildTreeTable(menu.getChildren(), result);
            }
        });
    }

    private void buildMenuTree(List<Permission> permissions, StringBuilder builder) {
        permissions.stream().filter(menu -> menu.getShow() == 1).forEach(menu -> {
            builder.append("<li>");
            if (StringUtils.isBlank(menu.getUrl())) {
                builder.append("<a href='#'>");
            } else {
                builder.append("<a href='" + menu.getUrl() + "'>");
            }
            builder.append("<i class='" + menu.getMenuIcon() + "'>").append("</i>")
                    .append("<span class='permission-item-parent'>" + menu.getName() + "</span></a>");
            if (menu.getChildren() != null && menu.getChildren().size() != 0) {
                builder.append("<ul>");
                buildMenuTree(menu.getChildren(), builder);
                builder.append("</ul>");
            }
            builder.append("</li>");
        });
    }

    @Override//TODO 需要删除子菜单
    public void deleteMenu(String id) {
        baseMapper.deleteById(id);
        UserUtils.clearMenuCache();
    }

    @Override
    public void sortPermission(String[] ids, Integer[] sequences) {
        for (int i = 0; i < ids.length; i++) {
            baseMapper.updateMenuSort(sequences[i], ids[i]);
        }
        UserUtils.clearMenuCache();
    }

    @Override
    public Permission getMenuById(String id) {
        Permission permission = baseMapper.selectById(id);
        if (StringUtils.isBlank(permission.getParentId())) {
            Permission parent = new Permission();
            parent.setName(TOPMENUNAME);
            parent.setId(TOPSTRINGID);
            permission.setParent(parent);
        } else {
            permission.setParent(baseMapper.selectById(permission.getParentId()));
        }
        return permission;
    }

    @Override
    public void saveMenu(Permission permission) {
        if (TOPSTRINGID.equals(permission.getParentId())) {
            permission.setParentId(null);
        }
        permission.setUrl(permission.getUrl().trim());
        permission.setMenu(1);
        insertOrUpdate(permission);

        Permission findIt = getTreeNode(UserUtils.getMenuList(), permission.getId(), null);
        if (findIt != null) {
            List<Permission> update = Lists.newArrayList();
            showOrHidePermission(findIt.getChildren(), permission.getShow(), update);
            if (update.size() > 0) {
                updateBatchById(update);
            }
        }

        UserUtils.clearMenuCache();
    }

    private void showOrHidePermission(List<Permission> permissions, short show, List<Permission> update) {
        permissions.forEach(_permission -> {
            _permission.setShow(show);
            update.add(_permission);
            showOrHidePermission(_permission.getChildren(), show, update);
        });
    }

    @Override
    public List<Map<String, Object>> getMenuTree(String selectIds, String topName, List<Permission> menus) {
        List<Map<String, Object>> mapList = buildTopMenu(topName);
        for (Permission permission : menus) {
            if (permission.getShow() == 0) {
                continue;
            }
            Map<String, Object> map = Maps.newHashMap();
            map.put("id", permission.getId());
            if (StringUtils.isBlank(permission.getParentId())) {
                map.put("parent", TOPSTRINGID);
            } else {
                map.put("parent", permission.getParentId());
            }
            map.put("text", permission.getName());
            Map<String, Object> state = Maps.newHashMap();
            state.put("opened", true);
            map.put("state", state);
            if (StringUtils.isNoneBlank(selectIds) && permission.getId().toString().equals(selectIds)) {
                map.put("selected", true);
            }
            mapList.add(map);
        }
        return mapList;
    }


    @Override
    public List<Map<String, Object>> getMenuTreeRecursion(String selectIds, String topName, List<Permission> menus) {
        List<Map<String, Object>> mapList = buildTopMenu(topName);
        buildMenuTree(menus, mapList, selectIds);
        return mapList;
    }

    private void buildMenuTree(List<Permission> permissions, List<Map<String, Object>> mapList, String selectIds) {
        for (Permission permission : permissions) {
            if (permission.getShow() == 0) {
                continue;
            }
            Map<String, Object> map = Maps.newHashMap();
            map.put("id", permission.getId());
            if (StringUtils.isBlank(permission.getParentId())) {
                map.put("parent", TOPSTRINGID);
            } else {
                map.put("parent", permission.getParentId());
            }
            map.put("text", permission.getName());
            Map<String, Object> state = Maps.newHashMap();
            state.put("opened", true);
            map.put("state", state);
            if (StringUtils.isNoneBlank(selectIds) && permission.getId().toString().equals(selectIds)) {
                map.put("selected", true);
            }
            mapList.add(map);
            if (permission.getChildren() != null && permission.getChildren().size() != 0) {
                buildMenuTree(permission.getChildren(), mapList, selectIds);
            }
        }
    }

    private List<Map<String, Object>> buildTopMenu(String topName) {
        List<Map<String, Object>> mapList = Lists.newArrayList();
        Map<String, Object> map = Maps.newHashMap();
        map.put("id", TOPSTRINGID);
        map.put("parent", "#");
        map.put("text", topName);
        Map<String, Object> state = Maps.newHashMap();
        state.put("opened", true);
        map.put("state", state);
        mapList.add(map);
        return mapList;
    }

    private List<Permission> getMenuTreeList(List<Permission> permissionList) {
        List<Permission> result = getEntityTreeList(permissionList);
        result.stream().filter(menu -> menu.getChildren().size() != 0).forEach(menu -> {
            Collections.sort(menu.getChildren(), (o1, o2) -> {
                if (o1.getSort() > o2.getSort()) {
                    return 1;
                } else if (o1.getSort() < o2.getSort()) {
                    return -1;
                } else {
                    return 0;
                }
            });
        });
        return result;
    }
}
