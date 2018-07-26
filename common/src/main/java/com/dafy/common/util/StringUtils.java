package com.dafy.common.util;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * 字符串工具类
 * <p>
 * Created by liaoxudong on 2017/7/28.
 */
public class StringUtils {
    private static String letterChar = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String EMPTY_STR = "";

    /**
     *
     * @return
     */
    public static String generateInviteCode(Integer storeID, int length){
        StringBuffer sb = new StringBuffer();
        int len = letterChar.length();
        for (int i = 0; i < length; i++) {
            sb.append(letterChar.charAt((int) Math.round(Math.random() * (len - 1))));
        }
        return sb.toString();
    }

    /**
     * 判断字符串是否为空
     *
     * @param string 待判断字符串
     * @return str == null || "" == str
     */
    public static boolean isEmpty(Object string) {
        return string == null || "".equals(string);
    }

    /**
     * 通用唯一识别码（英语：Universally Unique Identifier，简称UUID）
     * @return
     */
    public static String getUUID(){
        String uuid = UUID.randomUUID().toString(); //获取UUID并转化为String对象
        uuid = uuid.replace("-", "");

        return uuid;
    }

    /**
     * 列表转以split分割的字符串
     * @param list
     * @param split 如, . 等
     * @return xxx,yyy,eee等格式
     */
    public static String listToSplitString(List list, char split) {
        if (list == null || list.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i=0;i<list.size();i++) {
            if(i<list.size()-1)
                sb.append(list.get(i)).append(split);
            else
                sb.append(list.get(i));
        }
        return sb.toString();
    }


    /**
     * 大陆号码或香港号码均可
     */
    public static boolean isPhone(String str) {
        return isChinaPhoneLegal(str) || isHKPhoneLegal(str);
    }

    /**
     * 大陆手机号码11位数，匹配格式：前三位固定格式+后8位任意数
     * 此方法中前三位格式有：
     * 13+任意数
     * 15+除4的任意数
     * 18+除1和4的任意数
     * 17+除9的任意数
     * 147
     */
    public static boolean isChinaPhoneLegal(String str) throws PatternSyntaxException {
        String regExp = "^((13[0-9])|(15[^4])|(18[0-3,5-9])|(17[0-8])|(147))\\d{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * 香港手机号码8位数，5|6|8|9开头+7位任意数
     */
    public static boolean isHKPhoneLegal(String str)throws PatternSyntaxException {
        String regExp = "^(5|6|8|9)\\d{7}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    public static long getLong(String e) {
        String str = e.trim().replaceAll("[^0-9]", "");
        if (isEmpty(str)) return 0;
        return Long.valueOf(str);
    }

    /**
     * 字符串元转分
     * @param amount
     * @return
     */
    public static long strYuanToLongFen(String amount) {
        return   new BigDecimal(Double.valueOf(amount)).multiply(new BigDecimal(100)).longValue();
    }

    public static String getCode(String code){
        return "0000".equals(code) ? "00" : code;
    }
}
