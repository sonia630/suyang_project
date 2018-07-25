package com.o2o.nm.sys.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.o2o.nm.sys.entity.Permission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MenuMapper extends BaseMapper<Permission> {

    /**
     * @title: findMenuByRoleId
     * @description: 通过角色查找菜单
     * @return: List<Permission>
     */
    List<Permission> findMenuByRoleId(String roleId);

    List<Permission> getAllMenus();

    void updateMenuSort(@Param("sort") Integer sort, @Param("id") String id);

    void updateResParent(@Param("id") String id, @Param("pid") String pid);
}