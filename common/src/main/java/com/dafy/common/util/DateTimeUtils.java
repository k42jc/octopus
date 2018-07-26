package com.dafy.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * <b>日期/时间工具类</b>
 * <br>
 * Created by liaoxudong on 2017/7/28.
 */
public final class DateTimeUtils {

    public static final String DATE_TIME_FORMAT_1 = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_TIME_FORMAT_2 = "yyyy-MM-dd HH:mm";
    public static final String DATE_TIME_FORMAT_3 = "yyyy-MM-dd";
    public static final String DATE_TIME_FORMAT_4 = "yyyyMMdd";
    public static final String DATE_TIME_FORMAT_5 = "yyyyMMddHHmmss";

    private DateTimeUtils() {
    }

    /**
     * 日期格式化为：yyyy-MM-dd HH:mm:ss
     *
     * @param date java.util.Date
     * @return 格式化之后的日期
     */
    public static String formatDatetime1(Date date) {
        if (date == null)
            return "";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_TIME_FORMAT_1);
        return simpleDateFormat.format(date);
    }

    public static String formatDatetime5(Date date) {
        if (date == null)
            return "";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_TIME_FORMAT_5);
        return simpleDateFormat.format(date);
    }

    public static String formatDatetime4(Date date) {
        if (date == null)
            return "";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_TIME_FORMAT_4);
        return simpleDateFormat.format(date);
    }

    public static String formatDatetime2(Date date) {
        if (date == null)
            return "";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_TIME_FORMAT_2);
        return simpleDateFormat.format(date);
    }

    /**
     * 日期格式化为：yyyy-MM-dd HH:mm:ss
     *
     * @param timemills 时间毫秒数
     * @return 格式化之后的日期
     */
    public static String formatDatetime1(long timemills) {
        return formatDatetime1(new Date(timemills));
    }

    /**
     * 与当前时间的差距,秒为单位
     *
     * @param date
     * @return
     */
    public static Long doubleDateDiffer(Date date) {
        Long beforeTime = date.getTime();
        Long nowTime = new Date().getTime();
        Long time = (nowTime - beforeTime) / 1000;
        return time;
    }

    /**
     * 与当前时间的差距,天为单位
     *
     * @param bdate
     * @param adate
     * @return
     */
    public static Integer doubleDateDifferDays(Date bdate, Date adate) {
        int days = (int) ((adate.getTime() - bdate.getTime()) / (1000 * 3600 * 24));
        return Integer.parseInt(String.valueOf(days));
    }

    /**
     * yyyy-MM-dd HH:mm:ss
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String formatDatetimePattern(Date date, String pattern) {
        if (date == null)
            return "";
        return new SimpleDateFormat(pattern).format(date);
    }

    /**
     * 字符串转换成日期 yyyy-MM-dd HH:mm:ss
     *
     * @param str
     * @return date
     */
    public static Date strToDate(String str) {

        SimpleDateFormat format = new SimpleDateFormat(DATE_TIME_FORMAT_1);
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 字符串转换成日期 yyyy-MM-dd HH:mm:ss
     *
     * @param str
     * @return date
     */
    public static Date StrToDateTakePattern(String str, String pattern) {

        SimpleDateFormat format = new SimpleDateFormat(pattern);
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }


    /**
     * 字符串转换成日期 yyyy-MM-dd HH:mm:ss
     *
     * @param days
     * @return date
     */
    public static String addDays(int days, Date date) {

        SimpleDateFormat format = new SimpleDateFormat(DATE_TIME_FORMAT_3);
        return format.format(new Date(date.getTime() + days * 24 * 60 * 60 * 1000));
    }

    /**
     * 字符串转换成日期 yyyy-MM-dd HH:mm:ss
     *
     * @param days
     * @return date
     */
    public static String addDaysForPattern(int days, Date date, String pattern) {

        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(new Date(date.getTime() + days * 24 * 60 * 60 * 1000));
    }

    /**
     * 第一个时间大于第二个则为true
     *
     * @param before
     * @param after
     * @return
     */
    public static boolean compareDate(Date before, Date after) {
        return before.getTime() - after.getTime() > 0;
    }


    /**
     * 判断两个日期是否同年同月
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean compare2DateIsSameYearAndMonth(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        int nowMonth = cal1.get(Calendar.MONTH);
        int nowYear = cal1.get(Calendar.YEAR);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int planMonth = cal2.get(Calendar.MONTH);
        int planYear = cal2.get(Calendar.YEAR);
        return nowMonth == planMonth && nowYear == planYear;
    }

    /**
     * 是否为同年同月同日
     *
     * @param date1
     * @param date2
     */
    public static boolean compare2DateIsSameYearAndMonthAndDay(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        int nowMonth = cal1.get(Calendar.MONTH);
        int nowYear = cal1.get(Calendar.YEAR);
        int nowDay = cal1.get(Calendar.DAY_OF_MONTH);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int planMonth = cal2.get(Calendar.MONTH);
        int planYear = cal2.get(Calendar.YEAR);
        int planDay = cal2.get(Calendar.DAY_OF_MONTH);
        return nowMonth == planMonth && nowYear == planYear && nowDay == planDay;
    }

    /**
     * 是否为同月同日
     *
     * @param date1
     * @param date2
     */
    public static boolean compare2DateIsSameMonthAndDay(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        int nowMonth = cal1.get(Calendar.MONTH);
        int nowDay = cal1.get(Calendar.DAY_OF_MONTH);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int planMonth = cal2.get(Calendar.MONTH);
        int planDay = cal2.get(Calendar.DAY_OF_MONTH);
        return nowMonth == planMonth && nowDay == planDay;
    }


    /**
     * @param days
     * @param date
     * @return
     */
    public static Date addDaysReturnDate(int days, Date date) {
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        instance.add(Calendar.DAY_OF_MONTH, days);
        return instance.getTime();
    }

    /**
     * 获取今年的月份天数
     *
     * @param month
     * @return
     */
    public static int getYearsMonthDays(int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, month - 1);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }


    /**
     * 获取月份
     *
     * @param diffDays 0表示当月 -1表示上个月
     * @return
     */
    public static int getMonth(int diffDays) {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.MONTH) + 1 + diffDays;
    }

    /**
     * 判断是否为平年2月28日
     *
     * @param date
     * @return
     */
    public static Boolean isCommonYear228(Date date) {
        Calendar a = Calendar.getInstance();
        a.setTime(date);
        a.set(Calendar.DATE, 1);//把日期设置为当月第一天
        a.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天
        Date date1 = StrToDateTakePattern("2-28", "MM-dd");
        Date time = a.getTime();
        return compare2DateIsSameMonthAndDay(date, date1) && compare2DateIsSameMonthAndDay(a.getTime(), date);
    }


    /**
     * 按天算时间，未满24小时，超过一天，返回1
     *
     * @param bdate
     * @param adate
     * @return
     */
    public static int daysBetween(Date bdate, Date adate) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(bdate);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(adate);
        int day1 = cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);
        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if (year1 != year2) {
            int timeDistance = 0;
            for (int i = year1; i < year2; i++) {
                if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0) { //闰年
                    timeDistance += 366;
                } else {//不是闰年
                    timeDistance += 365;
                }
            }
            return timeDistance + (day2 - day1);
        } else {
            return day2 - day1;
        }

    }

    /**
     * 是否为每个月的第一天
     *
     * @param date
     * @return
     */
    public static Boolean isMonthFirstDay(Date date) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date);
        int nowDay = cal1.get(Calendar.DAY_OF_MONTH);
        return nowDay == 1 ? true : false;
    }

    public static void main(String[] args) {
        Date date = strToDate("2018-02-21 14:15:12");
        System.out.println(isMonthFirstDay(date));

    }


    /**
     * 两个日期间的时间差
     *
     * @param before 前一个日期
     * @param after  后一个日期
     * @param needSecs  是否精确到秒
     * @return
     */
    public static String daysDiff(Date before, Date after,boolean needSecs) {
        if (before == null || after == null) {
            throw new IllegalArgumentException("before || after");
        }

        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = after.getTime() - before.getTime();
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        long sec = diff % nd % nh % nm / ns;
        if (needSecs) {
            return String.format("%s %s:%s:%s",day,hour,min,sec);
        }else {
            return String.format("%s天 %s时%s分",day,hour,min);
        }
    }

}
