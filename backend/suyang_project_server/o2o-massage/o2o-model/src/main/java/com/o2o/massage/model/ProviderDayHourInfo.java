package com.o2o.massage.model;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Author: zhongli
 * @Date: 2018/3/4 14:23
 * @Description:
 */
@Data
public class ProviderDayHourInfo {
    private Date day;
    private List<TimePointInfo> hourInfoList;
}
