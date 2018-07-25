package com.o2o.nm.sys.entity;

import java.util.List;

public interface TreeNode<ID, Entity extends TreeNode> {
    /**
     * 父路径
     */
    ID getParentId();

    ID getId();

    List<Entity> getChildren();

    void setParent(Entity entity);


}
