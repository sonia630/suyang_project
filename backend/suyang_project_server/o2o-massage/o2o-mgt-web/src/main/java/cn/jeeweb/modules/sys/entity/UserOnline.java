package cn.jeeweb.modules.sys.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.o2o.nm.entity.BasicEntity;
import com.o2o.nm.sys.security.mgt.OnlineSession;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@SuppressWarnings("serial")
@TableName("sys_user_online")
@Getter
@Setter
public class UserOnline extends BasicEntity {


    /**
     * 用户会话id ===> uid
     */
    @TableId(value = "id", type = IdType.INPUT)
    private String id;

    // 当前登录的用户Id
    @TableField(value = "user_id")
    private String userId;

    @TableField(value = "username")
    private String username;

    /**
     * 用户主机地址
     */
    @TableField(value = "host")
    private String host;

    /**
     * 用户登录时系统IP
     */
    @TableField(value = "system_host")
    private String systemHost;

    /**
     * 用户浏览器类型
     */
    @TableField(value = "user_agent")
    private String userAgent;

    /**
     * 在线状态
     */
    @TableField(value = "status")
    private String status = "on_line";

    /**
     * session创建时间
     */
    @TableField(value = "start_timestsamp")
    private Date startTimestamp;
    /**
     * session最后访问时间
     */
    @TableField(value = "last_access_time")
    private Date lastAccessTime;

    /**
     * 超时时间
     */
    @TableField(value = "online_timeout")
    private Long timeout;

    @TableField(exist = false)
    private OnlineSession session;


    public OnlineSession getSession() {
        return this.session;
    }

    public void setSession(OnlineSession session) {
        this.session = session;
    }

    public String getSystemHost() {
        return systemHost;
    }

    public void setSystemHost(String systemHost) {
        this.systemHost = systemHost;
    }

    public static final UserOnline fromOnlineSession(OnlineSession session) {
        UserOnline online = new UserOnline();
        online.setId(String.valueOf(session.getId()));
        online.setUserId(session.getUserId());
        online.setUsername(session.getUsername());
        online.setStartTimestamp(session.getStartTimestamp());
        online.setLastAccessTime(session.getLastAccessTime());
        online.setTimeout(session.getTimeout());
        online.setHost(session.getHost());
        online.setUserAgent(session.getUserAgent());
        online.setSystemHost(session.getSystemHost());
        online.setSession(session);

        return online;
    }

}
