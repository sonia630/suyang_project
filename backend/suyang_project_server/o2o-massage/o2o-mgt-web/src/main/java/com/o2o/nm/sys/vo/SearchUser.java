package com.o2o.nm.sys.vo;

import com.o2o.nm.entity.UserType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SearchUser {

    private String userName = null;
    private String startDate = null;
    private String finishDate = null;
    private String orgId = null;
    private String includeUser = null;
    private String userType = null;

    public List<Integer> getCodes() {
        if (userType != null) {
            if (userType.equals(UserType.PROVIDER.getCode().toString())) {
                return UserType.getProviderCode();
            } else if (userType.equals(UserType.CUSTOMER.getCode().toString())) {
                return UserType.getCustomCode();
            } else if (userType.equals(UserType.SYS.getCode().toString())) {
                return UserType.getSysCode();
            }
        }
        return null;
    }
}
