package com.o2o.massage.core.utils;

import java.io.*;

public class SerializeUtil {

    //TODO fix return null
    public static Object toObject(byte[] data) {
        try {
            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
            Object o = ois.readObject();
            ois.close();
            return o;
        } catch (Exception e) {
            return null;
        }
    }

    public static byte[] toBytes(Serializable o) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(o);
            oos.close();
            return baos.toByteArray();
        } catch (Exception e) {
            return null;
        }
    }

}