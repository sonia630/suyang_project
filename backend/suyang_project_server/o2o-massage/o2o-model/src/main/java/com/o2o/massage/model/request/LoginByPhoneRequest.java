package com.o2o.massage.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: zhongli
 * @Date: 2018/3/13 23:54
 * @Description:
 */
@Data
public class LoginByPhoneRequest {
    @ApiModelProperty(value = "手机号",example = "15201291856")
    private String phone;
    @ApiModelProperty(value = "验证码",example = "4080")
    private String verifyCode;
}
