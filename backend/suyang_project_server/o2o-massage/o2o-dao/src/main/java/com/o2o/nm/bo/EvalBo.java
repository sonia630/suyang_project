package com.o2o.nm.bo;

import lombok.Builder;
import lombok.Getter;

@Builder@Getter
public class EvalBo {

    private String providerId;
    private int start;
    private int count;
    private int evalScoreBegin;
    private int evalScoreEnd;

}
