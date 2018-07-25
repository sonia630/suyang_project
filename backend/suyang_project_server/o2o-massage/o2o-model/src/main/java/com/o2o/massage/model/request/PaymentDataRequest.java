package com.o2o.massage.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: zhongli
 * @Date: 2018/3/15 14:49
 * @Description:
 */
@Data
public class PaymentDataRequest {
    @ApiModelProperty(value = "订单编号",example = "xxxxxxxx")
    private String orderNo;
    /*@ApiModelProperty(value = "支付Token",example = "如果为空则按照")
    private String token;*/
}
