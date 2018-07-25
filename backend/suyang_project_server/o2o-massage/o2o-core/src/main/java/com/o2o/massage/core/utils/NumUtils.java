package com.o2o.massage.core.utils;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Created by zhuifengbuaa on 16/11/16.
 */
public class NumUtils {

    public static String generateUUID() {
        UUID uuid = UUID.randomUUID();
        String randomUUIDString = uuid.toString();
        return randomUUIDString;
    }

    /**
     * @param divisor
     * @param dividend
     * @param scale
     * @return
     */
    public static int getPercent(long divisor, long dividend, int scale) {
        return BigDecimal.valueOf(divisor).divide(BigDecimal.valueOf(dividend), scale, BigDecimal.ROUND_UP).multiply(BigDecimal.valueOf(100)).intValue();
    }
}
