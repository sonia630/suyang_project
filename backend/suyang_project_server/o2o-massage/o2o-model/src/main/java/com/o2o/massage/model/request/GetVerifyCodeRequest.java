package com.o2o.massage.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: zhongli
 * @Date: 2018/2/23 18:38
 * @Description:
 */
@Data
public class GetVerifyCodeRequest {
    @ApiModelProperty(value = "手机号",example = "15201291856")
    private String phone;
    @ApiModelProperty(value = "图形验证码",example = "Ad4F")
    private String captcha;
}
