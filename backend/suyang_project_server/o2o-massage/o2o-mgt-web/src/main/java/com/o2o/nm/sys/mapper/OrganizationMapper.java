package com.o2o.nm.sys.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.o2o.nm.sys.entity.OrgChildRenCount;
import com.o2o.nm.sys.entity.SysOrg;
import com.o2o.nm.sys.entity.SysRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrganizationMapper extends BaseMapper<SysOrg> {

    List<SysOrg> getOrgsByParent(@Param("pid") String pid);

    void delOrgRoles(String orgId);

    Long getUserCount(String orgId);

    List<OrgChildRenCount> getChildrenCountByPid(List<String> pids);

    void updateOrgPid(@Param("pid") String pid, @Param("id") String orgId);

    List<SysRole> getOrgRoles(String orgId);
}