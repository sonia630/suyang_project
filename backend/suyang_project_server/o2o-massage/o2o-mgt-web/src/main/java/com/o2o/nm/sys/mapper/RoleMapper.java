package com.o2o.nm.sys.mapper;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.o2o.nm.sys.entity.SysRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMapper extends BaseMapper<SysRole> {
    /**
     * @title: findRoleByUserId
     * @description: 通过用户查找角色
     * @return: List<Role>
     */
    List<SysRole> findRoleByUserId(String userId);

    List<String> getChildrenIds(String pid);

    void updateRoleParent(@Param("id") String id, @Param("pid") String pid);

    List<SysRole> getAssignedRoles();
}
