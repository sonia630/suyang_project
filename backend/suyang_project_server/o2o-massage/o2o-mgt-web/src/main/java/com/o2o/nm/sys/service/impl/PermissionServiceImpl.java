package com.o2o.nm.sys.service.impl;

import com.google.common.collect.Lists;
import com.o2o.nm.entity.UserInfo;
import com.o2o.nm.sys.entity.Permission;
import com.o2o.nm.sys.entity.SysRolePermission;
import com.o2o.nm.sys.mapper.PermissionMapper;
import com.o2o.nm.sys.service.PermissionService;
import com.o2o.nm.sys.service.SysRolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PermissionServiceImpl extends TreeServiceImpl<PermissionMapper, Permission> implements PermissionService {

    @Autowired
    private SysRolePermissionService sysRolePermissionService;

    @Override
    public void recursiveExclude(List<Permission> source, Map<String, SysRolePermission> rps, List<Permission> target) {

        for (int i = 0; i < source.size(); i++) {
            Permission permission = source.get(i);
            if (target.contains(permission)) {
                continue;
            }
            if (rps.containsKey(permission.getId())) {
                List<Permission> cTarget = Lists.newArrayList();
                addChildren(permission, rps, cTarget);
                if (cTarget.size() != 0) {
                    target.add(permission);
                    target.addAll(cTarget);
                }
            } else {
                if (!target.contains(permission)) {
                    target.add(permission);
                }
                addAllChildren(permission.getChildren(), target);
            }
        }
    }

    private void addChildren(Permission permission, Map<String, SysRolePermission> rps, List<Permission> target) {
        for (Permission cf : permission.getChildren()) {
            if (!rps.containsKey(cf.getId())) {
                if (!target.contains(cf)) {
                    target.add(cf);
                }
                addAllChildren(cf.getChildren(), target);
            } else {
                List<Permission> cTarget = Lists.newArrayList();
                addChildren(cf, rps, cTarget);
                if (cTarget.size() != 0) {
                    if (!target.contains(cf)) {
                        target.add(cf);
                    }
                    target.addAll(cTarget);
                }
            }
        }
    }

    private void addAllChildren(List<Permission> children, List<Permission> target) {
        for (Permission cf : children) {
            target.add(cf);
            addAllChildren(cf.getChildren(), target);
        }
    }

    @Override
    public List<Permission> getResources(UserInfo user) {
        if (user.isAdmin()) {
            return getEntityTreeList(getAllResources());
        } else {
            return getEntityTreeList(baseMapper.getResources(user.getUserId()));
        }
    }

    @Override
    public List<Permission> getAllResources() {
        return baseMapper.getAllResources();
    }

    @Override
    public Permission getPermission(String permissionId) {
        return selectById(permissionId);
    }

    @Override
    public List<Permission> getPermissionsByUserId(String userId, Integer type) {
        return baseMapper.getPermissionsByUserId(userId, type);
    }

    @Override
    public List<Permission> getPermissionsByRole(List<Permission> permissions, String roleId) {
        Map<String, SysRolePermission> maps = sysRolePermissionService.getSysRolePermissions(roleId);
        List<Permission> result = Lists.newArrayList();
        fillTreeNode(permissions, result, entity -> maps.containsKey(entity.getId()));
        return result;
    }

    @Override
    public List<Permission> getExcludePermissionsByRole(List<Permission> funs, String roleId) {
        Map<String, SysRolePermission> rp = sysRolePermissionService.getSysRolePermissions(roleId);
        List<Permission> result = Lists.newArrayList();
        recursiveExclude(funs, rp, result);
        return result;
    }


}
