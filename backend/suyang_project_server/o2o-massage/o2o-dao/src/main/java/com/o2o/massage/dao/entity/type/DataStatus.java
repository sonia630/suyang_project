package com.o2o.massage.dao.entity.type;

public enum DataStatus{
    NORMAL((short)1), //正常
    DELETE((short)0); //删除

    private short value;

    DataStatus(short value) {
        this.value = value;
    }

    public static DataStatus valueOf(int value) {
        for (DataStatus status : DataStatus.values()) {
            if (status.value == value) {
                return status;
            }
        }
        return null;
    }

    public short getValue(){
        return value;
    }
}
