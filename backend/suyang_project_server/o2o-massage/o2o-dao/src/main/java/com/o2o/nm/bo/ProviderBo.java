package com.o2o.nm.bo;

import com.o2o.massage.core.utils.ProviderUtil;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProviderBo {

    int age;
    private String providerIntroduction;
    int level;
    private String headPic;
    private String realName;
    private double distance;
    private String providerId;

    public double getDistance() {
        return new BigDecimal(distance / 1000).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public String getLevelString() {
        return ProviderUtil.getProviderLevel(level);
    }
}
