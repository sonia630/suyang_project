package com.o2o.massage.model.request;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Author: zhongli
 * @Date: 2018/4/26 23:58
 * @Description:
 */
@Data
public class OrderChangeBookTimeRequest extends OrderNoRequest{

    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
    private Date serviceStartTime;
}
