package cn.jeeweb.core.utils;

public class JspFunctions {

    public static boolean arrayContains(String[] sources, String target) {
        return ArrayUtils.contains(sources, target);
    }
}
