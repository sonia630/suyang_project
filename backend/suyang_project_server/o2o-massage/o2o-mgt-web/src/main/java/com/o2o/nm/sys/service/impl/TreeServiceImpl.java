package com.o2o.nm.sys.service.impl;

import cn.jeeweb.core.utils.StringUtils;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.o2o.nm.sys.entity.TreeNode;
import com.o2o.nm.sys.service.TreeService;

import java.util.List;
import java.util.Map;
import java.util.Set;


public class TreeServiceImpl<Mapper extends BaseMapper<Entity>, Entity extends TreeNode<String, Entity>> extends ServiceImpl<Mapper, Entity> implements TreeService<Entity> {

    protected List<Entity> getEntityTreeList(List<Entity> entityList) {

        //parentId,children permission
        Map<String, Set<Entity>> mapping = Maps.newHashMap();
        Map<String, Entity> parents = Maps.newHashMap();
        List<Entity> result = Lists.newArrayList();
        entityList.forEach(entity -> {
            if (StringUtils.isNotBlank(entity.getParentId())) {
                Entity parent = parents.get(entity.getParentId());
                if (parent != null) {
                    parent.getChildren().add(entity);
                    entity.setParent(parent);
                    addChildren(mapping.get(entity.getParentId()), parent);
                    if (mapping.get(entity.getParentId()) != null) {
                        mapping.get(entity.getParentId()).clear();
                    }
                    addParent(mapping, parents, result, entity, false);
                } else {
                    mapping.compute(entity.getParentId(), (k, v) -> {
                        if (v == null) {
                            v = Sets.newHashSet();
                        }
                        v.add(entity);
                        return v;
                    });
                }
            } else {
                addParent(mapping, parents, result, entity, true);
            }
        });
        return result;
    }


    private void addParent(Map<String, Set<Entity>> mapping, Map<String, Entity> parents, List<Entity> result,
                           Entity entity, boolean addSelf) {
        //root
        addChildren(mapping.get(entity.getId()), entity);
        parents.put(entity.getId(), entity);
        if (addSelf) {
            result.add(entity);
        }
        if (mapping.get(entity.getId()) != null) {
            mapping.get(entity.getId()).clear();
        }
    }


    private void addChildren(Set<Entity> children, Entity parent) {
        if (children != null) {
            parent.getChildren().addAll(children);
        }
    }

    @Override
    public Entity getTreeNode(List<Entity> entities, String targetId, Callback callback) {
        for (Entity entity : entities) {
            if (entity.getId().equals(targetId)) {
                if (callback != null) {
                    if (callback.match(entity)) {
                        return entity;
                    }
                } else {
                    return entity;
                }
            } else {
                Entity _entity = getTreeNode(entity.getChildren(), targetId, callback);
                if (_entity != null) {
                    return _entity;
                }
            }
        }
        return null;
    }

    @Override
    public void fillTreeNode(List<Entity> entities, List<Entity> result, Callback callback) {
        for (Entity entity : entities) {
            if (callback != null) {
                if (callback.match(entity)) {
                    result.add(entity);
                }
            } else {
                result.add(entity);
            }
            fillTreeNode(entity.getChildren(), result, callback);
        }
    }
}
