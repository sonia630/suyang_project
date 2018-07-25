package com.o2o.massage.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: zhongli
 * @Date: 2018/2/23 18:47
 * @Description:
 */
@Data
public class UserSimpleInfoVO {
    @ApiModelProperty(value = "用户ID", example = "user1001")
    protected String userId;
    @ApiModelProperty(value = "用户名称", example = "林豆豆")
    protected String name;

    @ApiModelProperty(value = "用户类型",example = "4-普通用户，2-推拿师")
    protected Byte userType;
    @ApiModelProperty(value = "用户头像",example = "http://pic.xxxx.com/img/123.jpg")
    protected String headPic;
    @ApiModelProperty(value = "性别", example = "1:男,0:女,-1:未设置")
    protected Byte gender;
}
