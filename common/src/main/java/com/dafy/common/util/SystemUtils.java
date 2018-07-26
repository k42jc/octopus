package com.dafy.common.util;

/**
 * 用于获取系统参数
 * Created by liaoxudong
 * Date:2018/3/7
 */

public final class SystemUtils {
    private SystemUtils() {
    }


    /**
     * 获取布尔类型的系统参数
     * @param key
     * @return
     */
    public static boolean getBooleanSystemProperty(String key) {
        String value = System.getProperty(key);
        if (value == null)
            return false;
        else
            return value.equalsIgnoreCase("true");
    }

    /**
     * 获取整数类型的系统变量
     * @param key
     * @param def
     * @return
     */
    public static int getIntSystemProperty(String key, int def) {
        String value = System.getProperty(key);
        if (value == null) {
            return def;
        }else{
            try {
                return Integer.valueOf(value);
            } catch (NumberFormatException e) {
                return def;
            }
        }
    }
}
