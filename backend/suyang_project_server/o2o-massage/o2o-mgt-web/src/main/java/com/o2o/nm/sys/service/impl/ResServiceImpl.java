package com.o2o.nm.sys.service.impl;

import cn.jeeweb.core.query.wrapper.EntityWrapper;
import cn.jeeweb.modules.sys.Constants;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.o2o.nm.sys.entity.Permission;
import com.o2o.nm.sys.mapper.MenuMapper;
import com.o2o.nm.sys.service.ResService;
import com.o2o.nm.sys.service.ServiceResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ResServiceImpl extends TreeServiceImpl<MenuMapper, Permission> implements ResService {

    @Override
    public List<Map<String, Object>> getResources() {

        List<Map<String, Object>> result = Lists.newArrayList();

        Map<String, Object> map = Maps.newHashMap();
        map.put("id", Constants.TOPSTRINGID);
        map.put("text", Constants.TOPRESNAME);
        map.put("parent", "#");
        Map<String, Object> state = Maps.newHashMap();
        state.put("opened", true);
        map.put("state", state);
        result.add(map);
        List<Permission> resources;
        resources = selectList(new EntityWrapper<>(Permission.class).eq("menu", 0));
        for (Permission permission : resources) {
            map = Maps.newHashMap();
            map.put("id", permission.getId());
            if (Strings.isNullOrEmpty(permission.getParentId())) {
                map.put("parent", Constants.TOPSTRINGID);
            } else {
                map.put("parent", permission.getParentId());
            }
            map.put("text", getFunctionText(permission));
            result.add(map);
        }
        return result;
    }

    private String getFunctionText(Permission Permission) {
        String text = Permission.getName();
        text += "   <font color=green>resId:" + Permission.getId() + "</font>";
        return text;
    }

    @Override
    public void save(Permission res) {
        insertOrUpdate(res);
    }

    @Override
    public void delete(String id) {
        List<Permission> permissions = getEntityTreeList(selectList(new EntityWrapper<>(Permission.class).eq("menu", 0)));
        Permission permission = getTreeNode(permissions, id, null);
        if (permission != null) {
            List<Permission> result = Lists.newArrayList();
            fillTreeNode(permission.getChildren(), result, null);
            if (result.size() > 0) {
                List<String> ids = result.stream().map(_permission -> _permission.getId()).collect(Collectors.toList());
                ids.add(id);
                deleteBatchIds(ids);
            } else {
                deleteById(id);
            }
        }
    }

    @Override
    public Permission getResource(String id) {
        return selectById(id);
    }

    @Override
    public ServiceResponse move(String id, String to) {
        ServiceResponse serviceResponse = new ServiceResponse();
        if (to.equals(Constants.INVALIDPARENT)) {
            serviceResponse.setMessage("无效的移动位置");
            serviceResponse.setResponseType(ServiceResponse.ResponseType.ERRPOR);
            return serviceResponse;
        }
        baseMapper.updateResParent(id, to);
        serviceResponse.setMessage("保存成功");
        serviceResponse.setResponseType(ServiceResponse.ResponseType.SUCCESS);
        return serviceResponse;
    }

    @Override
    public void emptyResources() {
        baseMapper.delete(new EntityWrapper<>(Permission.class).eq("menu", 0));
    }

    @Override
    public void savePermissions(List<Permission> permissions) {
        insertBatch(permissions);
    }
}