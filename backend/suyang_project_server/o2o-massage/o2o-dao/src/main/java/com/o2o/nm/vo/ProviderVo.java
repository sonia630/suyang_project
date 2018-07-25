package com.o2o.nm.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProviderVo {

    int providerStatus;
    int userStatus;
    List<Integer> userTypes;
}
