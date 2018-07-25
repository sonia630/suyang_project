package com.o2o.nm.sys.service.impl;

import cn.jeeweb.core.query.wrapper.EntityWrapper;
import cn.jeeweb.modules.sys.Constants;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.o2o.nm.sys.entity.*;
import com.o2o.nm.sys.mapper.OrganizationMapper;
import com.o2o.nm.sys.service.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author warning5
 */
@Service
public class OrgServiceImpl extends TreeServiceImpl<OrganizationMapper, SysOrg> implements OrgService {

    @Autowired
    private SysOrgUserService sysOrgUserService;
    @Autowired
    private SysOrgRoleService sysOrgRoleService;

    @Override
    public List<Map<String, Object>> getOrgTree(String orgId) {
        List<Map<String, Object>> mapList = Lists.newArrayList();
        Map<String, Object> map = Maps.newHashMap();
        if (orgId.equals("#")) {
            orgId = Constants.TOPORGID;
            map.put("id", Constants.TOPORGID);
            map.put("parent", "#");
            map.put("text", getTreeText(Constants.TOPORGNAME, -1L));
            Map<String, Object> state = Maps.newHashMap();
            state.put("opened", true);
            map.put("state", state);
            mapList.add(map);
        }
        List<SysOrg> orgs = baseMapper.getOrgsByParent(orgId);
        if (orgs.size() != 0) {
            for (SysOrg sysOrg : orgs) {
                map = Maps.newHashMap();
                map.put("id", sysOrg.getId());
                map.put("parent", sysOrg.getParentId());
                map.put("text", getTreeText(sysOrg.getName(), sysOrg.getUserCount()));
                map.put("children", true);
                mapList.add(map);
            }
        }
        return mapList;
    }

    @Override
    public boolean save(SysOrg sysOrg, String save_type) throws Exception {
        boolean result = true;
        if (ResultType.INSERT.name().equals(save_type)) {
            SysOrg org = selectOne(new EntityWrapper<>(SysOrg.class).eq("name", sysOrg.getName()));
            if (org != null) {
                throw new Exception(sysOrg.getName() + " 已存在.");
            }
            result = insert(sysOrg);
        } else {
            update(sysOrg);
        }
        return result;
    }

    private String getTreeText(String realText, Long userSize) {

        if (userSize <= 0) {
            return realText;
        }
        return "<bb>" + realText + "</bb>" + "  <span class=\"badge bg-color-blue txt-color-white\">" + userSize + "</span>";
    }

    /**
     * 只能删除单个没有子元素且没有分配用户的机构
     */
    @Override
    public Collection<String> delete(List<String> ids) {

        List<OrgChildRenCount> childrenCount = baseMapper.getChildrenCountByPid(ids);
        List<String> removing = Lists.newArrayList();
        Collection<String> returnValue = Sets.newHashSet();
        if (childrenCount.size() == 0) {
            removing.addAll(ids);
        } else {
            childrenCount.forEach(orgChildRenCount -> {
                if (orgChildRenCount.getCount() == 0) {
                    if (!hasUser(orgChildRenCount.getParent_id())) {
                        removing.add(orgChildRenCount.getParent_id());
                    } else {
                        returnValue.add(orgChildRenCount.getParent_id());
                    }
                } else {
                    returnValue.add(orgChildRenCount.getParent_id());
                }
            });
        }

        if (removing.size() != 0 && returnValue.size() == 0) {
            deAssignOrgRoles(removing);
            deleteBatchIds(removing);
        }
        return returnValue;
    }

    private boolean hasUser(String orgId) {
        return baseMapper.getUserCount(orgId) != 0;
    }

    @Override
    public ServiceResponse move(String id, String from, String to) {

        ServiceResponse serviceResponse = new ServiceResponse();
        if (to.equals(Constants.INVALIDPARENT)) {
            serviceResponse.setMessage("无效的移动位置");
            serviceResponse.setResponseType(ServiceResponse.ResponseType.ERRPOR);
            return serviceResponse;
        }
        baseMapper.updateOrgPid(to, id);
        serviceResponse.setMessage("保存成功");
        serviceResponse.setResponseType(ServiceResponse.ResponseType.SUCCESS);
        return serviceResponse;
    }

    @Override
    public SysOrg getOrg(String orgId) {
        return selectById(orgId);
    }

    @Override
    public Long assignUser(String[] userIds, String orgId) {

        List<SysOrgUser> params = Lists.newArrayListWithCapacity(userIds.length);
        for (int i = 0; i < userIds.length; i++) {
            SysOrgUser sysOrgUser = new SysOrgUser();
            sysOrgUser.setOrgId(orgId);
            sysOrgUser.setUserId(userIds[i]);
            params.add(sysOrgUser);
        }
        sysOrgUserService.insertBatch(params);
        return baseMapper.getUserCount(orgId);
    }

    @Override
    public Long delAssignedUser(String[] userIds, String orgId) {
        for (int i = 0; i < userIds.length; i++) {
            sysOrgUserService.delete(new EntityWrapper<>(SysOrgUser.class).eq("org_id", orgId).eq("user_id", userIds[i]));
        }
        return baseMapper.getUserCount(orgId);
    }

    @Override
    public List<SysRole> getOrgRoles(String orgId) {
        return baseMapper.getOrgRoles(orgId);
    }

    @Override
    public void assignRoles(String orgId, String roles) {
        String[] rols = roles.split(",");
        deAssignOrgRoles(Arrays.asList(orgId));
        if (StringUtils.isNotEmpty(roles)) {
            List<SysOrgRole> param = Lists.newArrayList();
            for (int i = 0; i < rols.length; i++) {
                SysOrgRole sysOrgRole = new SysOrgRole();
                sysOrgRole.setOrgId(orgId);
                sysOrgRole.setRoleId(rols[i]);
                param.add(sysOrgRole);
            }
            sysOrgRoleService.insertBatch(param);
        }
    }

    @Override
    public void deAssignOrgRoles(List<String> ids) {
        ids.forEach(id -> {
            baseMapper.delOrgRoles(id);
        });
    }

    @Override
    public void update(SysOrg SysOrg) {
        update(SysOrg);
    }

    @Override
    public long getAssignUserCountByOrg(String orgId) {
        return sysOrgUserService.selectCount(new EntityWrapper<>(SysOrgUser.class).eq("org_id", orgId));
    }
}
