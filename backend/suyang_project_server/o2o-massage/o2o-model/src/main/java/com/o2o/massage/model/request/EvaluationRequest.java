package com.o2o.massage.model.request;

import lombok.Data;

@Data
public class EvaluationRequest {
    String desc;
    int score;
    String orderNo;
    String providerUserId;
    String createBy;
    String serviceId;
}
