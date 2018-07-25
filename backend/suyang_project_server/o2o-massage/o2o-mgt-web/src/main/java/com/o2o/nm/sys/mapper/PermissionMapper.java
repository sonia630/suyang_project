package com.o2o.nm.sys.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.o2o.nm.sys.entity.Permission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PermissionMapper extends BaseMapper<Permission> {

    List<Permission> getAllResources();

    List<Permission> getResources(String userId);


    List<Permission> getPermissionsByUserId(@Param("userId") String userId, @Param("type") Integer type);
}
