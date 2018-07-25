package com.o2o.nm.bo;

import lombok.Data;

@Data
public class EvalAllInfo {
    private int total;
    private double totalScore;

    public int getPraisePercent() {
        return (int)(totalScore / (total * 5) * 100);
    }
}
