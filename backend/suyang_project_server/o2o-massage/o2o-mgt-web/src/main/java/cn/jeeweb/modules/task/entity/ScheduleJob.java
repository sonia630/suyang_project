package cn.jeeweb.modules.task.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.o2o.nm.entity.IEntity;
import com.o2o.nm.entity.UserInfo;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@TableName("task_schedule_job")
@Getter
@Setter
public class ScheduleJob implements IEntity, Serializable {

    /**
     * 任务主键
     */
    @TableId(value = "id", type = IdType.UUID)
    private String id;
    /**
     * cron表达式
     */
    @TableField(value = "cron_expression")
    private String cronExpression;
    /**
     * 任务调用的方法名
     */
    @TableField(value = "method_name")
    private String methodName;
    /**
     * 任务是否有状态
     */
    @TableField(value = "is_concurrent")
    private String isConcurrent;
    /**
     * 任务描述
     */
    @TableField(value = "description")
    private String description;
    /**
     * 更新者
     */
    @TableField(value = "update_by", el = "updateBy.id")
    private UserInfo updateBy;
    /**
     * 任务执行时调用哪个类的方法 包名+类名
     */
    @TableField(value = "bean_class")
    private String beanClass;
    /**
     * 创建时间
     */
    @TableField(value = "create_date")
    private Date createDate;
    /**
     * 任务状态
     */
    @TableField(value = "job_status")
    private String jobStatus;
    /**
     * 任务分组
     */
    @TableField(value = "job_group")
    private String jobGroup;
    /**
     * 更新时间
     */
    @TableField(value = "update_date")
    private Date updateDate;
    /**
     * 创建者
     */
    @TableField(value = "create_by", el = "createBy.id")
    private UserInfo createBy;
    /**
     * Spring bean
     */
    @TableField(value = "spring_bean")
    private String springBean;
    /**
     * 任务名
     */
    @TableField(value = "job_name")
    private String jobName;

}
