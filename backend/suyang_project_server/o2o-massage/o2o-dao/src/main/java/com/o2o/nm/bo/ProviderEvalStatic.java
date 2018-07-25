package com.o2o.nm.bo;

import lombok.Data;

@Data
public class ProviderEvalStatic {

    private int evalSum;
    private int evalCount;

    public int getAverageScore() {
        if (evalCount == 0) {
            return 0;
        }
        int value = evalSum % evalCount;
        return value == 0 ? evalSum / evalCount - 1 : evalSum / evalCount;
    }

}
