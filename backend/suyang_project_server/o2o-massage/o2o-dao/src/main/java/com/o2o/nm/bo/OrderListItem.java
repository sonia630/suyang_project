package com.o2o.nm.bo;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class OrderListItem extends ProviderOrderListItem{
    String headPic;
    String createTime;
    String providerName;
    BigDecimal totalAmount;
}
