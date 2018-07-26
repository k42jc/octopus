package com.dafy.common.util;

import java.util.HashMap;
import java.util.Map;

public class BankUtils {
    private static final String BANK_PREFIX = "**** **** **** ";
    private static final Map<String,String> BANK_LIST = new HashMap<>(32);
    static {
        BANK_LIST.put("中国工商银行","ICBC");
        BANK_LIST.put("中国农业银行","ABC");
        BANK_LIST.put("中国建设银行","CCB");
        BANK_LIST.put("中国银行","BOC");
        BANK_LIST.put("中国交通银行","BCOM");
        BANK_LIST.put("兴业银行","CIB");
        BANK_LIST.put("中信银行","CITIC");
        BANK_LIST.put("光大银行","CEB");
        BANK_LIST.put("平安银行","PAB");
        BANK_LIST.put("中国邮政储蓄银行","PSBC");
        BANK_LIST.put("上海银行","SHB");
        BANK_LIST.put("浦东发展银行","SPDB");
        BANK_LIST.put("中国民生银行","CMBC");
        BANK_LIST.put("招商银行","CMB");
        BANK_LIST.put("广发银行","GDB");
        BANK_LIST.put("华夏银行","HXB");
        BANK_LIST.put("杭州银行","HZB");
        BANK_LIST.put("北京银行","BOB");
        BANK_LIST.put("宁波银行","NBCB");
        BANK_LIST.put("江苏银行","JSB");
        BANK_LIST.put("浙商银行","ZSB");
    }
    private static final Map<String,String> BANK_CODE_LIST = new HashMap<>(32);
    static {
        BANK_CODE_LIST.put("1020000", "ICBC");
        BANK_CODE_LIST.put("1020076", "ICBC");
        BANK_CODE_LIST.put("1030000", "ABC");
        BANK_CODE_LIST.put("1040000", "BOC");
        BANK_CODE_LIST.put("1040036", "BOC");
        BANK_CODE_LIST.put("5190344", "BOC");
        BANK_CODE_LIST.put("1050000", "CCB");
        BANK_CODE_LIST.put("1050001", "CCB");
        BANK_CODE_LIST.put("5330344", "CCB");
        BANK_CODE_LIST.put("3050000", "CMBC");
        BANK_CODE_LIST.put("3010000", "BCOM");
        BANK_CODE_LIST.put("3030000", "CEB");
        BANK_CODE_LIST.put("3060000", "GDB");
        BANK_CODE_LIST.put("3090000", "CIB");
        BANK_CODE_LIST.put("3070010", "PAB");
        BANK_CODE_LIST.put("4100000", "PAB");
        BANK_CODE_LIST.put("4105840", "PAB");
        BANK_CODE_LIST.put("6105840", "PAB");
        BANK_CODE_LIST.put("3100000", "SPDB");
        BANK_CODE_LIST.put("4010000", "SHB");
        BANK_CODE_LIST.put("4012900", "SHB");
        BANK_CODE_LIST.put("4012902", "SHB");
        BANK_CODE_LIST.put("1000000", "PSBC");
        BANK_CODE_LIST.put("1009999", "PSBC");
        BANK_CODE_LIST.put("1004900", "PSBC");
        BANK_CODE_LIST.put("3020000", "CITIC");
        BANK_CODE_LIST.put("3080000", "CMB");
        BANK_CODE_LIST.put("3040000", "HXB");
    }



    /**
     * 通过银行全称获取简称
     * @param bankName 全称
     * @return
     */
    public static String getShortNameByName(String bankName){
        if ("".equals(bankName)||null==bankName) {
            return null;
        }
        return BANK_LIST.get(bankName);
    }

    /**
     * ocr的code转换为简称
     * @param bankCode
     * @return
     */
    public static String getShortNameByCode(String bankCode){
        if ("".equals(bankCode)||null==bankCode) {
            return null;
        }
        return BANK_CODE_LIST.get(bankCode);
    }


    /**
     * 获取加密后的银行卡号
     * @param bankCardNum
     * @return
     */
    public static String getBankCardNum(String bankCardNum){
        if ("".equals(bankCardNum)||null==bankCardNum) {
            return null;
        }
        int length = bankCardNum.length();
        if (length < 16){
            return "";
        }
        StringBuffer s1 = new StringBuffer(bankCardNum);
        StringBuffer s2 = new StringBuffer(BANK_PREFIX);
        return s2.append(s1.substring(length - 4, length)).toString();
    }


    public static void main(String[] args) {
        System.out.println(getBankCardNum("1234234534564567"));
    }


}
