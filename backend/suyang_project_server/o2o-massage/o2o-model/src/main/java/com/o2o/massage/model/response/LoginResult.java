package com.o2o.massage.model.response;

import com.o2o.massage.model.UserBaseInfoVO;
import lombok.Data;

/**
 * Created by lawrence on 09/08/2017.
 */
@Data
public class LoginResult {
    private String token;
    private UserBaseInfoVO userInfo;
    private Boolean infoComplete = false;
}
