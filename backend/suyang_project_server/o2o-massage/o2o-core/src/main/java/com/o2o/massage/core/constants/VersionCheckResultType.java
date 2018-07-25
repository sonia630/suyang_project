package com.o2o.massage.core.constants;

public enum VersionCheckResultType {
    ALREADY_LATEST_VERSION("already_latest_version"),
    OPTIONAL_UPDATE("optional_update"),
    FORCE_UPDATE("force_update"),;
    private String value;

    VersionCheckResultType(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public static VersionCheckResultType findByValue(String value) {
        for (VersionCheckResultType os : values()) {
            if (os.getValue().equals(value)) {
                return os;
            }
        }
        return null;
    }
}
