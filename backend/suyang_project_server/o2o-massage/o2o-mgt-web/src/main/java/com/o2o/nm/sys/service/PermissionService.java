package com.o2o.nm.sys.service;

import com.o2o.nm.entity.UserInfo;
import com.o2o.nm.sys.entity.Permission;
import com.o2o.nm.sys.entity.SysRolePermission;

import java.util.List;
import java.util.Map;

public interface PermissionService extends TreeService<Permission> {

    void recursiveExclude(List<Permission> source, Map<String, SysRolePermission> rps, List<Permission> target);

    List<Permission> getResources(UserInfo user);

    List<Permission> getAllResources();

    Permission getPermission(String permissionId);

    List<Permission> getPermissionsByUserId(String userId, Integer type);

    List<Permission> getExcludePermissionsByRole(List<Permission> permissions, String roleId);

    List<Permission> getPermissionsByRole(List<Permission> permissions, String roleId);
}
