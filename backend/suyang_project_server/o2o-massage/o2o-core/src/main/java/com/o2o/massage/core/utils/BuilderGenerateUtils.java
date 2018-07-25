package com.o2o.massage.core.utils;

import java.lang.reflect.Field;

/**
 * @Author: zhongli
 * @Date: 2018/3/12 10:50
 * @Description:
 */
public class BuilderGenerateUtils {


    public static void getObjectValue(Object object) throws Exception {
        String builderClassName="OrderInfoDataBuilder";
        if (object != null) {//if (object!=null )  ----begin
            // 拿到该类
            Class<?> clz = object.getClass();
            // 获取实体类的所有属性，返回Field数组
            Field[] fields = clz.getDeclaredFields();

            for (Field field : fields) {// --for() begin
                System.out.println("private "+field.getType().getSimpleName()+" "+field.getName()+";");//打印该类的所有属性类型
            }//for() --end

            for (Field field : fields) {// --for() begin
                System.out.println(String.format("public %s %s(%s %s){",
                        builderClassName,field.getName(),field.getType().getSimpleName(),field.getName()));
                /*System.out.println("public "+builderClassName+" "+getMethodName(field.getName())
                        +"( "+field.getType().getSimpleName()+" "+field.getType().getSimpleName()+"){");//打印该类的所有属性类型
                */
                System.out.println(String.format("this.%s=%s;",field.getName(),field.getName()));
                System.out.println("return this;");
                System.out.println("}");
            }//for() --end

            for (Field field : fields) {// --for() begin
                System.out.println(String.format("orderInfo.set%s(this.%s);",getMethodName(field.getName()),field.getName()));//打印该类的所有属性类型
            }
        }//if (object!=null )  ----end
    }

    // 把一个字符串的第一个字母大写、效率是最高的、
    private static String getMethodName(String fildeName) throws Exception{
        byte[] items = fildeName.getBytes();
        items[0] = (byte) ((char) items[0] - 'a' + 'A');
        return new String(items);
    }
}
