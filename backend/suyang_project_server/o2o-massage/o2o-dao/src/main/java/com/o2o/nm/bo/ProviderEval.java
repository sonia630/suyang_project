package com.o2o.nm.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class ProviderEval {
    private String customerName;
    private String customerHeadPic;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date evalTime;
    private String description;
    private String serviceName;
    @JsonFormat(pattern = "yyyy年MM月dd日", timezone = "GMT+8")
    private Date serviceTime;

    private double score;

    public boolean isStar1() {
        return score >= 1;
    }

    public boolean isStar2() {
        return score >= 2;
    }

    public boolean isStar3() {
        return score >= 3;
    }

    public boolean isStar4() {
        return score >= 4;
    }

    public boolean isStar5() {
        return score >= 5;
    }
}
