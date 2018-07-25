package com.o2o.massage.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by lawrence on 09/08/2017.
 */
@Data
public class LoginRequest {
    @ApiModelProperty(value = "手机号",example = "15201291856")
    private String phone;
    @ApiModelProperty(value = "密码",example = "abcd1234")
    private String password;
}
