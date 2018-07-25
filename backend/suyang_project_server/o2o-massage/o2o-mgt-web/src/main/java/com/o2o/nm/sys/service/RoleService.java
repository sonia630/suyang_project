package com.o2o.nm.sys.service;

import com.o2o.nm.entity.UserInfo;
import com.o2o.nm.sys.entity.SysRole;
import com.o2o.nm.sys.exception.ExistException;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface RoleService {

    List<SysRole> getRolesByUser(UserInfo userInfo);

    SysRole findRoleByName(String name);

    void saveRole(SysRole sysRole) throws ExistException;

    SysRole getRole(String roleId, String pid, String text);

    Collection<String> deleteRole(String id);

    ServiceResponse move(String id, String from, String to);

    List<Map<String, Object>> getExcludeMenusAsTreeByRole(String roleId);

    List<Map<String, Object>> getExcludeResourcesAsTreeByRole(String roleId);

    List<Map<String, Object>> getMenusAsTreeByRole(String roleId);

    List<Map<String, Object>> getResourcesAsTreeByRole(String roleId);

    void assign(String roleId, String[] funs, String type);

    void deAssign(String roleId, List<String> funs, String type);

    String getRoleIdByName(String name);

}
