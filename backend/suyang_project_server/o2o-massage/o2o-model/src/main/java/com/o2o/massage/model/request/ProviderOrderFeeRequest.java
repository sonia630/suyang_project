package com.o2o.massage.model.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProviderOrderFeeRequest {
    private String providerId;
    private BigDecimal userLat;
    private BigDecimal userLng;
    private String serviceId;
}
