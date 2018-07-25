package com.o2o.nm.sys.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.FieldStrategy;
import com.baomidou.mybatisplus.enums.IdType;
import com.google.common.collect.Lists;
import com.o2o.nm.entity.BasicEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TreeEntity<T extends TreeNode> extends BasicEntity implements TreeNode<String, T> {

    /**
     * 父编号
     */
    @TableField(value = "parent_id", strategy = FieldStrategy.IGNORED)
    protected String parentId;

    @TableId(value = "id", type = IdType.UUID)
    protected String id;


    @TableField(exist = false)
    @JSONField(serialize = false)
    protected List<T> children = Lists.newArrayList();

    @TableField(exist = false)
    @JSONField(serialize = false)
    protected T parent;

}
