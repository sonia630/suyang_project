package com.o2o.nm.sys.service;

import com.o2o.nm.sys.entity.SysOrg;
import com.o2o.nm.sys.entity.SysRole;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface OrgService {
    List<Map<String, Object>> getOrgTree(String orgId);

    boolean save(SysOrg sysOrg, String save_type) throws Exception;

    Collection<String> delete(List<String> id);

    ServiceResponse move(String id, String from, String to);

    SysOrg getOrg(String orgId);

    Long assignUser(String[] userIds, String orgId);

    Long delAssignedUser(String[] userIds, String orgId);

    List<SysRole> getOrgRoles(String id);

    void assignRoles(String orgId, String roles);

    void deAssignOrgRoles(List<String> ids);

    void update(SysOrg SysOrg);

    long getAssignUserCountByOrg(String orgId);
}
