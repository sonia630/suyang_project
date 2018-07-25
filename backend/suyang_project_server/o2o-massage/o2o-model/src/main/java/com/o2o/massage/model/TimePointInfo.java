package com.o2o.massage.model;

import lombok.Data;

import java.util.Date;

/**
 * @Author: zhongli
 * @Date: 2018/3/4 14:54
 * @Description:
 */
@Data
public class TimePointInfo {
    private Date startTime;
    private boolean free;
    private String notFreeReason;
}
