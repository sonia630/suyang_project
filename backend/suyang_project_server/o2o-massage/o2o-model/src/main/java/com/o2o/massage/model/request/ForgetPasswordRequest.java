package com.o2o.massage.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ForgetPasswordRequest {
	@ApiModelProperty(value = "手机号",example = "15201291856")
	private String phone;
	@ApiModelProperty(value = "验证码",example = "4080")
	private String verifyCode;
	@ApiModelProperty(value = "密码",example = "abcd1234")
	private String password;
}
