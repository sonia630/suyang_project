package com.o2o.massage.model;

import lombok.Data;

/**
 * @Author: zhongli
 * @Date: 2018/3/14 0:08
 * @Description:
 */
@Data
public class CustomerInfoVO extends UserBaseInfoVO{

    private String customerSource;

    private String defaultAddress;
}
