package com.o2o.nm.sys.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@TableName("sys_permission")
@Getter
@Setter
@ToString
public class Permission extends TreeEntity<Permission> {
    /**
     * 图标
     */
    @TableField(value = "menu_icon")
    private String menuIcon;
    /**
     * 资源类型
     */
    @TableField(value = "type")
    private String type;
    /**
     * 点击后前往的地址
     */
    @TableField(value = "url")
    private String url;
    /**
     * 权限字符串
     */
    @TableField(value = "permission")
    private String permission;
    /**
     * 是否显示
     */
    @TableField(value = "show")
    private Short show;
    /**
     * 排序
     */
    @TableField(value = "sort")
    private Integer sort;
    /**
     * 资源名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 资源名称
     */
    @TableField(value = "menu")
    private int menu;

}
