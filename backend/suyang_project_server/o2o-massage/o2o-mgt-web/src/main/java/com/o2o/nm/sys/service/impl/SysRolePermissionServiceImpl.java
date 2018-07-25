package com.o2o.nm.sys.service.impl;

import cn.jeeweb.core.query.wrapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.common.collect.Maps;
import com.o2o.nm.sys.entity.SysRolePermission;
import com.o2o.nm.sys.mapper.SysRolePermissionMapper;
import com.o2o.nm.sys.service.SysRolePermissionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 角色-菜单 服务实现类
 * </p>
 *
 * @author warning5
 * @since 2018-02-26
 */
@Service
public class SysRolePermissionServiceImpl extends ServiceImpl<SysRolePermissionMapper, SysRolePermission> implements SysRolePermissionService {


    public Map<String, SysRolePermission> getSysRolePermissions(String roleId) {
        List<SysRolePermission> rp = baseMapper.selectList(new EntityWrapper<>(SysRolePermission.class).eq("role_id", roleId));
        Map<String, SysRolePermission> maps = Maps.newHashMap();
        for (SysRolePermission sysRolePermission : rp) {
            maps.put(sysRolePermission.getPermissionId(), sysRolePermission);
        }
        return maps;
    }
}
