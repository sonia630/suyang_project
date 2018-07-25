package com.o2o.massage.model.request;

import lombok.Data;

/**
 * @Author: zhongli
 * @Date: 2018/3/14 0:55
 * @Description:
 */
@Data
public class BasePageRequest {
    private int pageNo;
    private int pageSize;
    private String orderBy;
}
