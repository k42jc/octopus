package com.dafy.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by liaoxudong on 2017/10/29.
 */
public class DateUtils {
    public static final String DATE_PATTERN_1 = "yyyyMMdd";
    public static final String DATE_PATTERN_2 = "yyyy-MM-dd";

    /**
     * 判断dateStr是否是日期格式
     * @param dateStr
     * @param pattern 日期格式化标准，如：yyyy-MM-dd or yyyyMMdd or yyyyMMddHHmmss等
     * @return
     */
    public static boolean isDate(String dateStr,String pattern) {
        if (StringUtils.isEmpty(dateStr) || StringUtils.isEmpty(pattern)) {
            return false;
        }
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        try {
            format.parse(dateStr);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }


    /**
     * 字符串日期转换
     * @param dateStr
     * @param pattern 特定的格式化
     * @return
     */
    public static Date parseDate(String dateStr, String pattern) {
        if (StringUtils.isEmpty(dateStr) || StringUtils.isEmpty(pattern)) {
            return null;
        }
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        try {
            return format.parse(dateStr);
        } catch (ParseException e) {
            return null;
        }
    }
}
