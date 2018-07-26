package com.dafy.common.util;

/**
 * 数组工具类
 * Created by liaoxudong
 * Date:2017/10/27
 */

public class ArrayUtils {

    /**
     * 判断object是否是objs数组元素
     *
     * @param objs
     * @param object
     * @return
     */
    public static boolean contains(Object[] objs, Object object) {
        if (objs == null || object == null || objs.length <= 0) {
            return false;
        }
        for (Object obj : objs) {
            if (obj.equals(object)) {
                return true;
            }
        }
        return false;
    }
}
