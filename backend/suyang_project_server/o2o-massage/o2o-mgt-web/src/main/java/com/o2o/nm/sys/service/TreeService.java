package com.o2o.nm.sys.service;

import com.o2o.nm.sys.entity.TreeNode;

import java.util.List;

public interface TreeService<Entity extends TreeNode<String, Entity>> {
    Entity getTreeNode(List<Entity> entities, String targetId, Callback callback);

    void fillTreeNode(List<Entity> entities, List<Entity> result, Callback callback);

    interface Callback<Model extends TreeNode<String, Model>> {
        boolean match(Model entity);
    }
}
