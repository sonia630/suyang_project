package com.o2o.nm.sys.service;

import com.baomidou.mybatisplus.service.IService;
import com.o2o.nm.sys.entity.SysRolePermission;

import java.util.Map;

/**
 * <p>
 * 角色-菜单 服务类
 * </p>
 *
 * @author warning5
 * @since 2018-02-26
 */
public interface SysRolePermissionService extends IService<SysRolePermission> {

    Map<String, SysRolePermission> getSysRolePermissions(String roleId);
}
