package com.o2o.massage.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProviderScheduleShowVo {

    String orderNo;
    Integer slot;
}
