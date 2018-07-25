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
import lombok.ToString;

import java.util.List;

/**
 * <p>
 * 角色表
 * </p>
 *
 * @author warning5
 * @since 2018-02-24
 */
@Getter
@Setter
@ToString
public class SysRole extends BasicEntity<String> implements TreeNode<String, SysRole> {


    /**
     * 父编号
     */
    @TableField(value = "parent_id", strategy = FieldStrategy.IGNORED)
    protected String parentId;

    @TableId(value = "id", type = IdType.INPUT)
    protected String id;


    @TableField(exist = false)
    @JSONField(serialize = false)
    protected List<SysRole> children = Lists.newArrayList();

    @TableField(exist = false)
    @JSONField(serialize = false)
    protected SysRole parent;


    private String name;
    private String description;
    /**
     * 角色or分类
     */
    private int type;
    /**
     * 备注信息
     */
    private String remarks;

    @TableField(exist = false)
    private String pname;
}
