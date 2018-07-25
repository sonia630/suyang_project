package com.o2o.massage.dao.entity.type;

public enum EnumUserType {
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

    CUSTOMER("用户", 4),
    PROVIDER("顾问", 2),
    SYS("系统用户", 1),
    ALL("任何用户", 0),
    ADMIN("超级管理员", 120);

    private String name;
    private Integer code;

    EnumUserType(String name, int code) {
        this.name = name;
        this.code = code;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public static boolean isProvider(Integer userType) {
        return (PROVIDER.getCode() & userType) == PROVIDER.getCode();
    }

    public static boolean isCustomer(Integer userType) {
        return (CUSTOMER.getCode() & userType) == CUSTOMER.getCode();
    }
}
