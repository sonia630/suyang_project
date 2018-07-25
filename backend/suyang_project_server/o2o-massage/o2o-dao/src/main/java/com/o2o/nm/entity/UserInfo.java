package com.o2o.nm.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.tuple.Triple;

import java.util.Date;
import java.util.List;

import static com.alibaba.fastjson.serializer.SerializerFeature.WriteMapNullValue;

/**
 * <p>
 * 系统用户基本信息表
 * </p>
 *
 * @author warning5
 * @since 2018-02-22
 */
@TableName("user_info")
@Getter
@Setter
@ToString
public class UserInfo extends BasicEntity {

    @Override
    @JSONField(serialize = false)
    public String getId() {
        return userId;
    }

    /**
     * 用户Id
     */
    @TableId(value = "user_id", type = IdType.UUID)
    private String userId;
    /**
     * 用户姓名
     */
    private String name;
    /**
     * 用户密码
     */
    private String password;
    /**
     * 用户身份证号
     */
    private String idNum;
    /**
     * 用户手机号码
     */
    private String phone = "";
    /**
     * 用户电子邮箱
     */
    private String email;
    /**
     * 用户性别
     */
    private Integer gender;
    /**
     * 4    2    1
     * 用户|顾问|系统用户|
     * --------------
     * 1|1|1=7
     * ----------
     * 1|1|0=6
     * ---------
     * 1|0|1=5
     * --------
     * 1|0|0=4
     * ---------
     * 0|1|1=3
     * ---------
     * 0|1|0=2
     * ----------
     * 0|0|1=1
     */
    private Integer userType;
    /**
     * 用户生日
     */
    private Date birthday;
    /**
     * 用户头像
     */
    private String headPic;
    /**
     * 用户真实姓名
     */
    private String realName;
    /**
     * 微信openId
     */
    private String wxOpenId;
    /**
     * 用户状态
     */
    private Integer status = STATUS_NORMAL;
    /**
     * 备注说明
     */
    private String remarks;

    @TableField(exist = false)
    private int num;

    @TableField(exist = false)
    private String[] checkedTypes;

    public boolean isProvider() {
        return UserType.isProvider(userType);
    }

    @TableField(value = "login_date")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss", serialzeFeatures = WriteMapNullValue)
    protected Date loginDate = null;

    @JSONField(serialize = false)
    public boolean isAdmin() {
        if (SUPER_ADMIN.equals(userId)) {
            return true;
        } else {
            return false;
        }
    }

    @JSONField(serialize = false)
    public static boolean isAdmin(String userId) {
        if (SUPER_ADMIN.equals(userId)) {
            return true;
        } else {
            return false;
        }
    }

    @JSONField(serialize = false)
    public List<Triple> getUserTypes() {
        return UserType.getUserTypeList(userType);
    }

    /**
     * 是否锁定（1：正常；-1：删除；0：锁定；）
     */
    public static final Integer STATUS_DELETE = 3;
    public static final Integer STATUS_LOCKED = 2;
    public static final Integer STATUS_NORMAL = 1;

    public static final String SUPER_ADMIN = "82191015";
}
