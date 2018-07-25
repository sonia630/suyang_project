package com.o2o.nm.sys.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 *
 * </p>
 *
 * @author warning5
 * @since 2018-02-25
 */
@Getter
@Setter
@TableName("sys_org")
public class SysOrg extends TreeEntity<SysOrg> {


    /**
     * 机构名称
     */
    private String name;
    /**
     * 删除标记
     */
    private String delFlag;
    private String remarks;
    private Integer hierarchy;

    @TableField(exist = false)
    private Long userCount;

    @TableField(exist = false)
    private String pname;

    @Override
    public String toString() {
        return "SysOrg{" +
                "id=" + id +
                ", name=" + name +
                ", parentId=" + parentId +
                ", createBy=" + createBy +
                ", createDate=" + createDate +
                ", updateBy=" + updateBy +
                ", updateDate=" + updateDate +
                ", delFlag=" + delFlag +
                ", remarks=" + remarks +
                ", hierarchy=" + hierarchy +
                "}";
    }
}
