package com.o2o.massage.model.request;

import lombok.Data;

@Data
public class EvalListRequest {

    String catId = "1";
    String providerId;
    int start = 0;
    int count = 10;
}
