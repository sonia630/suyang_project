package com.o2o.massage.core.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhuifengbuaa on 16/11/8.
 */
public class InputUtils {

    public static boolean isPostiveNum(String str) {
        String regEx = "^[0-9]+$";
        Pattern pat = Pattern.compile(regEx);
        Matcher mat = pat.matcher(str);
        if (mat.find()) {
            return true;
        } else {
            return false;
        }
    }
}

