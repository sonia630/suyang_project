package com.o2o.massage.core.constants;

/**
 * Created by lawrence on 08/08/2017.
 */
public enum VerifyCodeState {
    USED((byte) 1),
    UNUSED((byte) 0);
    private byte value;

    VerifyCodeState(byte value) {
        this.value = value;
    }

    public byte getValue() {
        return this.value;
    }
}
