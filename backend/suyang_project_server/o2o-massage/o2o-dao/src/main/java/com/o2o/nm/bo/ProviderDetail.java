package com.o2o.nm.bo;

import com.o2o.massage.core.utils.ProviderUtil;
import lombok.Data;

@Data
public class ProviderDetail {

    private String headPic;
    private String realName;
    private int level;
    private String providerIntroduction;
    private Integer serviceTimes;
    private ProviderEvalStatic providerEvalStatic;

    public String getLevelString() {
        return ProviderUtil.getProviderLevel(level);
    }

    //索引从0开始
    public int getAverageScore() {
        if (providerEvalStatic == null) {
            return 0;
        }
        return providerEvalStatic.getAverageScore();
    }
}
