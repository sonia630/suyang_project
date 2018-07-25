package com.o2o.massage.core.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

public class TypeUtils {

    private TypeUtils() {
        throw new IllegalStateException("No typeUtil instance for you");
    }

    //    public static Object getTypeValue(String value, String type){
    //        if(StringUtils.isBlank(value) || StringUtils.isBlank(type)) throw new NullPointerException();
    //        if("String".equals(type)) return value;
    //        if("Integer".equals(type)) return Integer.valueOf(value);
    //        if("Boolean".equals(type)) return Boolean.valueOf(value);
    //        if("Long".equals(type)) return Long.valueOf(value);
    //        if("Float".equals(type)) return Float.valueOf(value);
    //        // TODO add date/ support etc
    //        throw new IllegalArgumentException("Unknown type:" + type);
    //    }
    /* ------------------------------------------------------------ */

    /**
     * Convert String value to instance.
     *
     * @param type  classname or type (eg int)
     * @param value The value as a string.
     * @return The value as an Object.
     */
    public static Object valueOf(String type, String value) {
        return valueOf(fromName(type), value);
    }
    /* ------------------------------------------------------------ */

    /**
     * Convert String value to instance.
     *
     * @param type  The class of the instance, which may be a primitive TYPE field.
     * @param value The value as a string.
     * @return The value as an Object.
     */
    public static Object valueOf(Class<?> type, String value) {
        try {
            if (type.equals(String.class))
                return value;

            Method m = class2Value.get(type);
            if (m != null)
                return m.invoke(null, value);

            if (type.equals(Character.TYPE) ||
                type.equals(Character.class))
                return new Character(value.charAt(0));

            Constructor<?> c = type.getConstructor(String.class);
            return c.newInstance(value);
        }
        catch (NoSuchMethodException e) {
            // LogSupport.ignore(log,e);
        }
        catch (IllegalAccessException e) {
            // LogSupport.ignore(log,e);
        }
        catch (InstantiationException e) {
            // LogSupport.ignore(log,e);
        }
        catch (InvocationTargetException e) {
            if (e.getTargetException() instanceof Error)
                throw (Error)(e.getTargetException());
            // LogSupport.ignore(log,e);
        }
        return null;
    }
    /* ------------------------------------------------------------ */

    /**
     * Class from a canonical name for a type.
     *
     * @param name A class or type name.
     * @return A class , which may be a primitive TYPE field..
     */
    public static Class<?> fromName(String name) {
        return name2Class.get(name);
    }

    /* ------------------------------------------------------------ */
    private static final HashMap<String, Class<?>> name2Class = new HashMap<String, Class<?>>();

    static {
        name2Class.put("boolean", Boolean.TYPE);
        name2Class.put("byte", Byte.TYPE);
        name2Class.put("char", Character.TYPE);
        name2Class.put("double", Double.TYPE);
        name2Class.put("float", Float.TYPE);
        name2Class.put("int", Integer.TYPE);
        name2Class.put("long", Long.TYPE);
        name2Class.put("short", Short.TYPE);
        name2Class.put("void", Void.TYPE);

        name2Class.put("java.lang.Boolean.TYPE", Boolean.TYPE);
        name2Class.put("java.lang.Byte.TYPE", Byte.TYPE);
        name2Class.put("java.lang.Character.TYPE", Character.TYPE);
        name2Class.put("java.lang.Double.TYPE", Double.TYPE);
        name2Class.put("java.lang.Float.TYPE", Float.TYPE);
        name2Class.put("java.lang.Integer.TYPE", Integer.TYPE);
        name2Class.put("java.lang.Long.TYPE", Long.TYPE);
        name2Class.put("java.lang.Short.TYPE", Short.TYPE);
        name2Class.put("java.lang.Void.TYPE", Void.TYPE);

        name2Class.put("java.lang.Boolean", Boolean.class);
        name2Class.put("java.lang.Byte", Byte.class);
        name2Class.put("java.lang.Character", Character.class);
        name2Class.put("java.lang.Double", Double.class);
        name2Class.put("java.lang.Float", Float.class);
        name2Class.put("java.lang.Integer", Integer.class);
        name2Class.put("java.lang.Long", Long.class);
        name2Class.put("java.lang.Short", Short.class);

        name2Class.put("Boolean", Boolean.class);
        name2Class.put("Byte", Byte.class);
        name2Class.put("Character", Character.class);
        name2Class.put("Double", Double.class);
        name2Class.put("Float", Float.class);
        name2Class.put("Integer", Integer.class);
        name2Class.put("Long", Long.class);
        name2Class.put("Short", Short.class);

        name2Class.put(null, Void.TYPE);
        name2Class.put("string", String.class);
        name2Class.put("String", String.class);
        name2Class.put("java.lang.String", String.class);
    }

    /* ------------------------------------------------------------ */
    private static final HashMap<Class<?>, Method> class2Value = new HashMap<Class<?>, Method>();

    static {
        try {
            Class<?>[] s = {String.class};

            class2Value.put(Boolean.TYPE,
                Boolean.class.getMethod("valueOf", s));
            class2Value.put(Byte.TYPE,
                Byte.class.getMethod("valueOf", s));
            class2Value.put(Double.TYPE,
                Double.class.getMethod("valueOf", s));
            class2Value.put(Float.TYPE,
                Float.class.getMethod("valueOf", s));
            class2Value.put(Integer.TYPE,
                Integer.class.getMethod("valueOf", s));
            class2Value.put(Long.TYPE,
                Long.class.getMethod("valueOf", s));
            class2Value.put(Short.TYPE,
                Short.class.getMethod("valueOf", s));

            class2Value.put(Boolean.class,
                Boolean.class.getMethod("valueOf", s));
            class2Value.put(Byte.class,
                Byte.class.getMethod("valueOf", s));
            class2Value.put(Double.class,
                Double.class.getMethod("valueOf", s));
            class2Value.put(Float.class,
                Float.class.getMethod("valueOf", s));
            class2Value.put(Integer.class,
                Integer.class.getMethod("valueOf", s));
            class2Value.put(Long.class,
                Long.class.getMethod("valueOf", s));
            class2Value.put(Short.class,
                Short.class.getMethod("valueOf", s));
        }
        catch (Exception e) {
            throw new Error(e);
        }
    }

}
