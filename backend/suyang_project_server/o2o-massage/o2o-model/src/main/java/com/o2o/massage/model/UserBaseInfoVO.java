package com.o2o.massage.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Author: zhongli
 * @Date: 2018/2/23 18:53
 * @Description:
 */
@Data
public class UserBaseInfoVO extends UserSimpleInfoVO {
    @ApiModelProperty(value = "生日", example = "1988-10-20")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    protected Date birthday;

    @ApiModelProperty(value = "身份证号", example = "110345198012345678")
    protected String idNum;

    @ApiModelProperty(value = "手机号", example = "15201291856")
    protected String phone;

    @ApiModelProperty(value = "邮箱", example = "")
    protected String email;

    @ApiModelProperty(value = "真实姓名", example = "林豆豆")
    protected String realName;

    @ApiModelProperty(value = "微信OpenId", example = "")
    protected String wxOpenId;

    @ApiModelProperty(value = "登录时间", example = "")
    protected Date loginDate;
}
