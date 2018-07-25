package com.o2o.nm.sys.service.impl;

import cn.jeeweb.core.query.wrapper.EntityWrapper;
import cn.jeeweb.modules.sys.Constants;
import cn.jeeweb.modules.sys.utils.UserUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.o2o.nm.entity.UserInfo;
import com.o2o.nm.sys.entity.Permission;
import com.o2o.nm.sys.entity.SysRole;
import com.o2o.nm.sys.entity.SysRolePermission;
import com.o2o.nm.sys.exception.ExistException;
import com.o2o.nm.sys.mapper.RoleMapper;
import com.o2o.nm.sys.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static cn.jeeweb.modules.sys.utils.UserUtils.clearRoleCache;

@Service
public class RoleServiceImpl extends TreeServiceImpl<RoleMapper, SysRole> implements RoleService {

    @Autowired
    PermissionService permissionService;
    @Autowired
    MenuService menuService;
    @Autowired
    SysRolePermissionService sysRolePermissionService;


    @Override
    public List<SysRole> getRolesByUser(UserInfo userInfo) {

        if (userInfo.isAdmin()) {
            return getEntityTreeList(selectList(new EntityWrapper<>(SysRole.class)));
        } else {
            return getEntityTreeList(baseMapper.findRoleByUserId(userInfo.getId()));
        }
    }


    @Override
    public SysRole findRoleByName(String name) {
        return selectOne(new EntityWrapper<>(SysRole.class).eq("name", name));
    }

    @Override
    public void saveRole(SysRole sysRole) throws ExistException {

        if (Constants.TOPROLEID.equals(sysRole.getParentId())) {
            sysRole.setParentId(null);
        }

        SysRole findIt = findRoleByName(sysRole.getName());
        if (findIt == null) {
            if (selectById(sysRole.getId()) == null) {
                insert(sysRole);
            } else {
                updateById(sysRole);
            }
        } else {
            if (findIt.getId().equals(sysRole.getId())) {
                updateById(sysRole);
            } else {
                throw new ExistException();
            }
        }
        clearRoleCache();
    }

    @Override
    public SysRole getRole(String roleId, String pid, String text) {
        SysRole sysRole = selectById(roleId);
        if (sysRole == null) {
            sysRole = new SysRole();
            sysRole.setName(text);
            sysRole.setParentId(pid);
            sysRole.setId(roleId);
        }
        if (Constants.TOPROLEID.equals(pid)) {
            sysRole.setPname(Constants.TOPORGNAME);
        } else {
            sysRole.setPname(selectById(pid).getName());
        }
        return sysRole;
    }

    @Override
    public Collection<String> deleteRole(String id) {
        List<String> removing = Lists.newArrayList();
        Map<String, String> assignedRoles = getAssignedRoles();
        List<String> children = baseMapper.getChildrenIds(id);

        Collection<String> returnValue = Sets.newHashSet();
        for (String child : children) {
            if (!assignedRoles.containsKey(child)) {
                removing.add(child);
            } else {
                returnValue.add(assignedRoles.get(id));
            }
        }

        if (returnValue.size() == 0) {
            removing.add(id);
        }

        if (removing.size() != 0) {
            deleteBatchIds(removing);
        }
        clearRoleCache();
        return returnValue;
    }

    @Override
    public ServiceResponse move(String id, String from, String to) {

        ServiceResponse serviceResponse = new ServiceResponse();
        if (to.equals(Constants.INVALIDPARENT)) {
            serviceResponse.setMessage("无效的移动位置");
            serviceResponse.setResponseType(ServiceResponse.ResponseType.ERRPOR);
            return serviceResponse;
        }
        baseMapper.updateRoleParent(id, to);
        serviceResponse.setMessage("保存成功");
        serviceResponse.setResponseType(ServiceResponse.ResponseType.SUCCESS);
        clearRoleCache();
        return serviceResponse;
    }

    @Override
    public List<Map<String, Object>> getExcludeMenusAsTreeByRole(String roleId) {
        List<Permission> sysPermissions = permissionService.getExcludePermissionsByRole(UserUtils.getMenuList(), roleId);
        return menuService.getMenuTree(null, Constants.TOPMENUNAME, sysPermissions);
    }

    @Override
    public List<Map<String, Object>> getExcludeResourcesAsTreeByRole(String roleId) {
        List<Permission> sysPermissions = permissionService.getExcludePermissionsByRole(permissionService.getResources(UserUtils.getUser()), roleId);
        return menuService.getMenuTree(null, Constants.TOPRESNAME, sysPermissions);
    }

    @Override
    public List<Map<String, Object>> getMenusAsTreeByRole(String roleId) {
        List<Permission> permissions = permissionService.getPermissionsByRole(UserUtils.getMenuList(), roleId);
        return menuService.getMenuTree(null, Constants.TOPMENUNAME, permissions);
    }

    @Override
    public List<Map<String, Object>> getResourcesAsTreeByRole(String roleId) {
        List<Permission> permissons = permissionService.getPermissionsByRole(permissionService.getResources(UserUtils.getUser()), roleId);
        return menuService.getMenuTree(null, Constants.TOPRESNAME, permissons);
    }

    @Override
    public void assign(String roleId, String[] funs, String type) {

        List<SysRolePermission> sysRolePermissions = Lists.newArrayList();
        List<Permission> permissions;
        if ("res".equals(type)) {
            permissions = permissionService.getAllResources();
        } else {
            permissions = UserUtils.getMenuList();
        }
        Map<String, SysRolePermission> rp = sysRolePermissionService.getSysRolePermissions(roleId);
        List<Permission> result = Lists.newArrayList();
        loadPermissions(result, Arrays.asList(funs), rp, permissions);
        String key;
        for (Permission permission : result) {
            key = permission.getId();
            while (key != null) {
                if (!rp.containsKey(key)) {
                    SysRolePermission sysRolePermission = new SysRolePermission();
                    sysRolePermission.setPermissionId(permission.getId());
                    sysRolePermission.setRoleId(roleId);
                    rp.put(permission.getId(), sysRolePermission);
                    sysRolePermissions.add(sysRolePermission);
                }

                Permission parent = permission.getParent();
                if (parent != null && parent.getId() != Constants.TOPSTRINGID) {
                    permission = parent;
                    key = parent.getId();
                } else {
                    key = null;
                }
            }
        }
        if (result.size() != 0) {
            sysRolePermissionService.insertBatch(sysRolePermissions);
        }
    }

    @Override
    public void deAssign(String roleId, List<String> funs, String type) {

        List<Permission> permissions;
        if ("res".equals(type)) {
            permissions = permissionService.getAllResources();
        } else {
            permissions = menuService.getAllMenus();
        }
        Map<String, SysRolePermission> rolePermissionMap = sysRolePermissionService.getSysRolePermissions(roleId);

        for (String fun : funs) {
            Optional<Permission> optional = permissions.stream().filter(permission -> permission.getId().equals(fun)).findFirst();
            if (!optional.isPresent()) {
                continue;
            }
            boolean containAllChildren = true;
            List<Permission> children = permissions.stream().filter(permission -> optional.get().getId().equals(permission.getParentId())).collect(Collectors.toList());
            for (Permission sysPermission : children) {
                if (rolePermissionMap.containsKey(sysPermission.getId()) && !funs.contains(sysPermission.getId())) {
                    containAllChildren = false;
                }
            }
            if (!containAllChildren) {
                continue;
            }

            sysRolePermissionService.delete(new EntityWrapper<>(SysRolePermission.class)
                    .eq("role_id", roleId)
                    .eq("permission_id", fun));
        }
    }

    private void loadPermissions(List<Permission> result, List<String> permissionIds, Map<String, SysRolePermission> rp, List<Permission> permissions) {
        permissionService.fillTreeNode(permissions, result, (Callback<Permission>) entity -> {
            for (String permissionId : permissionIds) {
                if (entity.getId().equals(permissionId)) {
                    if (!rp.containsKey(permissionId)) {
                        return true;
                    }
                }
            }
            return false;
        });
    }

    private Map<String, String> getAssignedRoles() {
        List<SysRole> records = baseMapper.getAssignedRoles();
        Map<String, String> result = Maps.newHashMapWithExpectedSize(records.size());
        for (SysRole rec : records) {
            result.put(rec.getId(), rec.getName());
        }
        return result;
    }

    @Override
    public String getRoleIdByName(String name) {
        SysRole sysRole = selectOne(new EntityWrapper<>(SysRole.class).eq("name", name));
        if (sysRole != null) {
            return sysRole.getId();
        }
        return null;
    }
}