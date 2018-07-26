package com.dafy.common.util;

import java.util.Map;

/**
 * Created by liaoxudong
 * Date:2017/10/30
 */

public class MapUtils {

    /**
     * 判断map是否为空，包括Null与empty
     *
     * @param map
     * @return
     */
    public static boolean isEmpty(Map map) {
        return map != null ? map.isEmpty() : true;

    }
}
