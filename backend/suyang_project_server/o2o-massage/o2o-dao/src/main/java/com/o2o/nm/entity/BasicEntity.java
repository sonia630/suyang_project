package com.o2o.nm.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.enums.FieldFill;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Getter
@Setter
public abstract class BasicEntity<ID> implements IEntity<ID>, Serializable {

    @TableField(value = "remarks")
    protected String remarks; // 备注
    @TableField(value = "create_by", el = "createBy.id", fill = FieldFill.INSERT)
    protected UserInfo createBy; // 创建者
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    protected Date createDate; // 创建日期
    @TableField(value = "update_by", el = "updateBy.id", fill = FieldFill.UPDATE)
    protected UserInfo updateBy; // 更新者
    @TableField(value = "update_time", fill = FieldFill.UPDATE)
    protected Date updateDate; // 更新日期

    public BasicEntity() {
        super();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BasicEntity that = (BasicEntity) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getId());
    }
}
