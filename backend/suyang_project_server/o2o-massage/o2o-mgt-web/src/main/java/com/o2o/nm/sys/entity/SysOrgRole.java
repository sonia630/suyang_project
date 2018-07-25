package com.o2o.nm.sys.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author warning5
 * @since 2018-02-26
 */
public class SysOrgRole implements Serializable {

    @TableId(type = IdType.UUID)
    private String id;
    private String orgId;
    private String roleId;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        return "SysOrgRole{" +
        "id=" + id +
        ", orgId=" + orgId +
        ", roleId=" + roleId +
        "}";
    }
}
