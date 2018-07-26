package com.dafy.common.util;

import java.lang.reflect.Field;

/**
 * Created by liaoxudong
 * Date:2017/10/30
 */

public class ObjectUtils {


    /**
     * obj中给出的field字段是否存在空值情况
     *
     * @param obj
     * @return
     */
    public static boolean anyFieldsEmpty(Object obj, String... fs) {
        if (obj == null || fs == null || fs.length <= 0) {
            return true;
        }
        for (String fieldName : fs) {
            try {
                Field field = obj.getClass().getDeclaredField(fieldName);
                field.setAccessible(true);
                if (field.getType().getName().equals(String.class.getName()) && field.getName().equals(fieldName) && ObjectUtils.isEmpty(field.get(obj))) {
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return false;
    }

    public static boolean isEmpty(Object o) {
        if (null == o || "".equals(o)) {
            return true;
        }
        return false;
    }

    /**
     * 对象的所有字段都为空
     * @param object
     * @return
     */
    public static boolean allFieldsNull(Object object) {
        if(object == null)
            return true;
        // 获取所有字段
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object o = field.get(object);
                if (o != null) {
                    return false;
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

}
