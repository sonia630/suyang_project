package com.o2o.nm.entity;

import com.google.common.collect.Lists;
import lombok.Getter;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Triple;

import java.util.Arrays;
import java.util.List;

public enum UserType {

    /**
     * 4    2    1
     * 用户|顾问|系统用户|
     * --------------
     * 1|1|1=7
     * ----------
     * 1|1|0=6
     * ---------
     * 1|0|1=5
     * --------
     * 1|0|0=4
     * ---------
     * 0|1|1=3
     * ---------
     * 0|1|0=2
     * ----------
     * 0|0|1=1
     */

    CUSTOMER("用户", 4), PROVIDER("顾问", 2), SYS("系统用户", 1), ADMIN("超级管理员", 120);

    @Getter
    private String name;
    @Getter
    private Integer code;

    UserType(String name, int code) {
        this.name = name;
        this.code = code;
    }

    public static List<Triple> getUserTypeList(Integer code) {
        List<Triple> list = Lists.newArrayList();
        for (UserType userType : values()) {
            if (userType.getCode() != 120) {
                list.add(new ImmutableTriple(userType.getName(), userType.getCode(), code != null ? userType.getCode() & code : 0));
            }
        }
        return list;
    }

    public static boolean isProvider(Integer userType) {
        return userType == 2 || userType == 3 || userType == 7 || userType == 6;
    }

    public static List<Integer> getProviderCode() {
        return Arrays.asList(2, 3, 7, 6);
    }

    public static List<Integer> getSysCode() {
        return Arrays.asList(1, 3, 5, 7);
    }

    public static List<Integer> getCustomCode() {
        return Arrays.asList(4, 5, 6, 7);
    }
}
