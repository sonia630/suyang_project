package com.o2o.massage.model.request;

import lombok.Data;

/**
 * @Author: zhongli
 * @Date: 2018/3/14 11:23
 * @Description:
 */
@Data
public class CustomerProfileUpdateRequest extends ProfileUpdateRequest{
    private String source;
}
