package com.o2o.massage.core.utils;

import org.apache.commons.codec.binary.Base64;

import java.util.Random;
import java.util.UUID;

/**
 * Created by Liu on 16/12/8.
 */
public class RandomUUID {
    public static String generateUUID() {
        UUID uuid = UUID.randomUUID();
        String randomUUIDString = uuid.toString();
        return randomUUIDString;
    }

    public static String withoutDash() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replace("-", "");
    }

    public static String generateRandomArray(int num) {
        int[] array = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        Random rand = new Random();
        for (int i = 10; i > 1; i--) {
            int index = rand.nextInt(i);
            int tmp = array[index];
            array[index] = array[i - 1];
            array[i - 1] = tmp;
        }
        int result = 0;
        if (array[0] == 0) {
            array[0] = random(2, 9);
        }
        for (int i = 0; i < num; i++)
            result = result * 10 + array[i];
        return Integer.toString(result);
    }

    public static int random(int m, int n) {
        if (m >= n) {
            return -1;
        }
        int c = n - m + 1;
        return (int) Math.floor(Math.random() * c + m);
    }

    public static byte[] asBytes(UUID uuid) {
        long msb = uuid.getMostSignificantBits();
        long lsb = uuid.getLeastSignificantBits();
        byte[] buffer = new byte[16];

        for (int i = 0; i < 8; i++) {
            buffer[i] = (byte) (msb >>> 8 * (7 - i));
        }
        for (int i = 8; i < 16; i++) {
            buffer[i] = (byte) (lsb >>> 8 * (7 - i));
        }
        return buffer;
    }

    public static byte[] long2bytes(long num) {
        byte[] b = new byte[8];
        for (int i = 0; i < 8; i++) {
            b[i] = (byte) (num >>> (56 - (i * 8)));
        }
        return b;
    }

    public static String shorterUUID() {
        String uuid = Base64.encodeBase64URLSafeString(long2bytes(UUID.randomUUID().getMostSignificantBits()));
        return uuid;
    }

    public static void main(String[] args) {
        System.out.println(withoutDash());
        System.out.println(shorterUUID());
        System.out.println(shorterUUID());
        System.out.println(shorterUUID());
    }
}
