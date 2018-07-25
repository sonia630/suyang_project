package com.o2o.massage.web.common.helper;

import javax.servlet.http.HttpServletRequest;

public class UserUtils {

    public static final String KEY_FROM_UID = "fromuid";

    public static String getFromUid(HttpServletRequest request) {
        return (String) request.getAttribute(KEY_FROM_UID);
    }

}
