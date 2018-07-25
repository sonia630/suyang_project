package com.o2o.massage.core.utils;

import java.io.*;

/**
 * Created by zhuifengbuaa on 16/11/23.
 */
public class FileUtils {
    public static void main(String[] args) {
        // This is the path where the file's name you want to take.
        String path = "/Users/zhuifengbuaa/mockUser";
        File[] file = getFile(path);
        System.out.println("");
    }

    public static File[] getFile(String path) {
        // get file list where the path has
        File file = new File(path);
        // get the folder list
        File[] array = file.listFiles();

        for (int i = 0; i < array.length; i++) {
            if (array[i].isFile()) {
                // only take file name
                System.out.println(array[i].getName());
                // take file path and name
                System.out.println(array[i]);
                // take file path and name
//                System.out.println("*****" + array[i].getPath());
            } else if (array[i].isDirectory()) {
                getFile(array[i].getPath());
            }
        }
        return array;
    }

    /**
     * 追加文件：使用FileOutputStream，在构造FileOutputStream时，把第二个参数设为true
     *
     * @param file
     * @param conent
     */
    public static void write(String file, String conent) {
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(file, true)));
            out.write(conent);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
