package com.o2o.nm.bo;

import lombok.Data;

@Data
public class ProviderServiceInfo {
    private String serviceName;
    private int estimatedTime;
    private String serviceSummary;
    private double price;
    private int serviceTimes;
    private String serviceId;
}
