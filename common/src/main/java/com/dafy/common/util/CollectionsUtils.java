package com.dafy.common.util;

import java.util.Collection;

/**
 * 集合工具类
 * Created by liaoxudong
 * Date:2017/10/26
 */

public class CollectionsUtils {

    private CollectionsUtils() {
    }

    /**
     * 判断集合是否为空
     *
     * @param collection
     * @return
     */
    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.size() == 0;
    }
}
