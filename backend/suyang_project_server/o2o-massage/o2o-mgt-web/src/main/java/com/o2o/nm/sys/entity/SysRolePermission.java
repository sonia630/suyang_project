package com.o2o.nm.sys.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * 角色-菜单
 * </p>
 *
 * @author warning5
 * @since 2018-02-26
 */
@Getter
@Setter
public class SysRolePermission implements Serializable {

    /**
     * 编号
     */
    @TableId(type = IdType.UUID)
    private String id;
    /**
     * 菜单或资源编号
     */
    private String permissionId;
    /**
     * 角色编号
     */
    private String roleId;


    @Override
    public String toString() {
        return "SysRolePermission{" +
                "id=" + id +
                ", permissionId=" + permissionId +
                ", roleId=" + roleId +
                "}";
    }
}
