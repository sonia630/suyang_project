package com.o2o.nm.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProviderList {

    BigDecimal neLongitude;
    BigDecimal neLatitude;
    BigDecimal swLongitude;
    BigDecimal swLatitude;

    BigDecimal userLat;
    BigDecimal userLng;
    ProviderVo providerVo;

    int start;
    int count = 10;
}
