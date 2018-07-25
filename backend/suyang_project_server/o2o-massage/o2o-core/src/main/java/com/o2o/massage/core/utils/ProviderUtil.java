package com.o2o.massage.core.utils;

public class ProviderUtil {

    public static String getProviderLevel(int level) {
        if (level == 1) {
            return "初级顾问";
        } else if (level == 2) {
            return "中级顾问";
        } else if (level == 3) {
            return "高级顾问";
        } else if (level == 4) {
            return "特级顾问";
        } else {
            return "实习顾问";
        }
    }
}
