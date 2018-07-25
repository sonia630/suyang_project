package cn.jeeweb.modules.sys.entity;

import cn.jeeweb.core.utils.StringUtils;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;
import com.o2o.nm.entity.IEntity;
import com.o2o.nm.entity.UserInfo;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

@TableName("sys_log")
@Getter
@Setter
public class Log implements IEntity<String>, Serializable {

    // 日志类型（1：接入日志；2：错误日志）
    public static final String TYPE_ACCESS = "1";
    public static final String TYPE_EXCEPTION = "2";

    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.UUID)
    private String id;
    /**
     * 用户代理
     */
    private String userAgent;
    /**
     * 请求URI
     */
    private String requestUri;
    /**
     * 日志标题
     */
    private String title;
    /**
     * 日志类型
     */
    private String type;
    /**
     * 操作方式
     */
    private String method;
    /**
     * 异常信息
     */
    private String exception;
    /**
     * 日志内容
     */
    private String content;
    /**
     * 操作方式
     */
    private String logtype;
    /**
     * 操作提交的数据
     */

    private String params;
    /**
     * 操作IP地址
     */
    private String remoteAddr;
    /**
     * 创建者
     */
    @TableField(value = "create_by", el = "createBy.id", fill = FieldFill.INSERT)
    private UserInfo createBy;
    /**
     * 创建时间
     */
    @TableField(value = "create_date", fill = FieldFill.INSERT)
    private Date createDate;

    public void setParams(Map paramMap) {
        if (paramMap == null) {
            return;
        }
        StringBuilder params = new StringBuilder();
        for (Map.Entry<String, String[]> param : ((Map<String, String[]>) paramMap).entrySet()) {
            params.append(("".equals(params.toString()) ? "" : "&") + param.getKey() + "=");
            String paramValue = (param.getValue() != null && param.getValue().length > 0 ? param.getValue()[0] : "");
            params.append(StringUtils.abbr(StringUtils.endsWithIgnoreCase(param.getKey(), "password") ? "" : paramValue,
                    100));
        }
        this.params = params.toString();
    }


}
