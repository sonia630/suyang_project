package com.o2o.nm.vo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class ProviderScheduleVo {

    BigDecimal neLongitude;
    BigDecimal neLatitude;
    BigDecimal swLongitude;
    BigDecimal swLatitude;
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    Date date;
}
