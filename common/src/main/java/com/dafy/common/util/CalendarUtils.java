package com.dafy.common.util;

import java.util.*;

/**
 * Date:2017/12/15
 */

public class CalendarUtils  {

    /** * 判断是否是法定假日 * * @param calendar * @return * @throws Exception */
    /*public static boolean isLawHoliday(List<String> lawHolidays,String calendar){
        if (lawHolidays.contains(calendar)) {
            return true;
        }
        return false;
    }*/



    /** * 判断是否是周末 * * @param calendar * @return * @throws ParseException */
    public static boolean isWeekends(Date date){
        // 先将字符串类型的日期转换为Calendar类型
        /*SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date date = null;
        try {
            date = sdf.parse(calendar);
        } catch (ParseException e) {
            e.printStackTrace();
        }*/
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        if (ca.get(Calendar.DAY_OF_WEEK) == 1
                || ca.get(Calendar.DAY_OF_WEEK) == 7) {
            return true;
        }
        return false;
    }

    /** * 判断是否是需要额外补班的周末 * * @param calendar * @return * @throws Exception */
    /*public static boolean isExtraWorkday(List<String> extraWorkdays,String calendar) {
        if (extraWorkdays.contains(calendar)) {
            return true;
        }
        return false;
    }*/

    public int getTotalDays() {
        return new GregorianCalendar().isLeapYear(Calendar.YEAR) ? 366 : 365;
    }
    /** * 获取一年中所有周末的天数 * @return */
    public int getTotalWeekends() {
        List<String> saturdays = new ArrayList<String>();
        List<String> sundays = new ArrayList<String>();
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        int nextYear = 1 + calendar.get(Calendar.YEAR);
        Calendar cstart = Calendar.getInstance();
        Calendar cend = Calendar.getInstance();
        cstart.set(currentYear, 0, 1);// 今年的元旦
        cend.set(nextYear, 0, 1);// 明年的元旦
        return this.getTotalSaturdays(saturdays, calendar, cstart, cend,
                currentYear)
                + this.getTotalSundays(sundays, calendar, cstart, cend,
                currentYear);
    }

    private int getTotalSaturdays(List<String> saturdays, Calendar calendar,
                                  Calendar cstart, Calendar cend, int currentYear) {
        // 将日期设置到上个周六
        calendar.add(Calendar.DAY_OF_MONTH, -calendar.get(Calendar.DAY_OF_WEEK));
        // 从上周六往这一年的元旦开始遍历，定位到去年最后一个周六
        while (calendar.get(Calendar.YEAR) == currentYear) {
            calendar.add(Calendar.DAY_OF_YEAR, -7);
        }
        // 将日期定位到今年第一个周六
        calendar.add(Calendar.DAY_OF_YEAR, 7);
        // 从本年第一个周六往下一年的元旦开始遍历
        for (; calendar.before(cend); calendar.add(Calendar.DAY_OF_YEAR, 7)) {
            saturdays.add(calendar.get(Calendar.YEAR) + "-"
                    + calendar.get(Calendar.MONTH) + "-"
                    + calendar.get(Calendar.DATE));
        }
        return saturdays.size();
    }

    private int getTotalSundays(List<String> sundays, Calendar calendar,
                                Calendar cstart, Calendar cend, int currentYear) {
        // 将日期设置到上个周日
        calendar.add(Calendar.DAY_OF_MONTH,
                -calendar.get(Calendar.DAY_OF_WEEK) + 1);
        // 从上周日往这一年的元旦开始遍历，定位到去年最后一个周日
        while (calendar.get(Calendar.YEAR) == currentYear) {
            calendar.add(Calendar.DAY_OF_YEAR, -7);
        }
        // 将日期定位到今年第一个周日
        calendar.add(Calendar.DAY_OF_YEAR, 7);
        // 从本年第一个周日往下一年的元旦开始遍历
        for (; calendar.before(cend); calendar.add(Calendar.DAY_OF_YEAR, 7)) {
            sundays.add(calendar.get(Calendar.YEAR) + "-"
                    + calendar.get(Calendar.MONTH) + "-"
                    + calendar.get(Calendar.DATE));
        }
        return sundays.size();
    }


    /** * 使用正则表达式匹配日期格式 * * @throws Exception */
    /*private void isMatchDateFormat(String calendar) throws Exception {
        Pattern pattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");
        Matcher matcher = pattern.matcher(calendar);
        boolean flag = matcher.matches();
        if (!flag) {
            throw new Exception("输入日期格式不正确，应该为year-month-day");
        }
    }*/

    public static void main(String[] args) throws Exception {
        /*String calendar = "2019-02-04";
        CalendarUtils cc = new CalendarUtils();
        System.out.println("是否是法定节假日：" + cc.isLawHoliday(calendar));
        System.out.println("是否是周末：" + cc.isWeekends(calendar));
        System.out.println("是否是需要额外补班的周末：" + cc.isExtraWorkday(calendar));
        System.out.println("是否是休息日：" + cc.isHoliday(calendar));
        System.out.println("是否是工作日：" + cc.isWorkday(calendar));
        System.out.println("今年总共有" + cc.getTotalDays() + "天");
        System.out.println("今年总共有" + cc.getTotalLawHolidays() + "天法定节假日");
        System.out.println("今年总共有" + cc.getTotalExtraWorkdays() + "天需要补班的周末");
        System.out.println("今年总共有" + cc.getTotalWeekends() + "天周末");
        System.out.println("今年总共有" + cc.getTotalHolidays() + "天休息日");*/
    }

}
