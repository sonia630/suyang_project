package com.o2o.massage.core.constants;

public enum VerifyCodeScenario {
    REGISTER_LOGIN(1),
    FORGET_PASSWORD(2),
    CHANGE_TELEPHONE(3);
    private int value;

    VerifyCodeScenario(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
