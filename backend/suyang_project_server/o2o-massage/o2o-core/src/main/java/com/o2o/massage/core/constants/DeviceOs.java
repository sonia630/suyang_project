package com.o2o.massage.core.constants;

/**
 *
 */
public enum DeviceOs {
    Android("android"),
    iOS("ios");
    private String value;

    DeviceOs(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public static DeviceOs findByValue(String value) {
        for (DeviceOs os : values()) {
            if (os.getValue().equals(value)) {
                return os;
            }
        }
        return DeviceOs.Android;
    }
}
