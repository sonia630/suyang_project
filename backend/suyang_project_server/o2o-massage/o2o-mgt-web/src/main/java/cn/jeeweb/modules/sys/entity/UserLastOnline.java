package cn.jeeweb.modules.sys.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.o2o.nm.entity.BasicEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@TableName("sys_user_last_online")
@Getter
@Setter
public class UserLastOnline extends BasicEntity<Long> {

    /**
     * login_count
     */
    @TableField(value = "login_count")
    private Long loginCount;
    /**
     * total_online_time
     */
    @TableField(value = "total_online_time")
    private Long totalOnlineTime;
    /**
     * host
     */
    @TableField(value = "host")
    private String host;
    /**
     * user_agent
     */
    @TableField(value = "user_agent")
    private String userAgent;
    /**
     * system_host
     */
    @TableField(value = "system_host")
    private String systemHost;
    /**
     * username
     */
    @TableField(value = "username")
    private String username;
    /**
     * last_stop_timestamp
     */
    @TableField(value = "last_stop_timestamp")
    private Date lastStopTimestamp;
    /**
     * last_login_timestamp
     */
    @TableField(value = "last_login_timestamp")
    private Date lastLoginTimestamp;
    /**
     * uid
     */
    @TableField(value = "uid")
    private String uid;
    /**
     * id
     */
    @TableId(value = "id", type = IdType.UUID)
    private Long id;
    /**
     * user_id
     */
    @TableField(value = "user_id")
    private Long userId;
}
