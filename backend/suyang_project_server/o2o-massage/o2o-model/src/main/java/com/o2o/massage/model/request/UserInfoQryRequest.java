package com.o2o.massage.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: zhongli
 * @Date: 2018/2/7 16:57
 * @Description:
 */
@Data
public class UserInfoQryRequest {

    @ApiModelProperty(value = "用户ID", example = "1001")
    private String userId;
}
