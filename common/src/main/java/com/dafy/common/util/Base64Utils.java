package com.dafy.common.util;

import org.apache.commons.codec.binary.Base64;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * <p>
 * BASE64编码解码工具包
 * </p>
 * 
 */
public class Base64Utils {
    
    /**  
     * <p>
     * BASE64字符串解码为二进制数据
     * </p>
     * 
     * @param base64
     * @return
     * @throws Exception
     */
    public static byte[] decode(String base64) throws Exception {
        return Base64.decodeBase64(base64);
    }
    
    /**  
     * <p>
     * 二进制数据编码为BASE64字符串
     * </p>
     * 
     * @param bytes
     * @return
     * @throws Exception
     */
    public static String encode(byte[] bytes) {
        return Base64.encodeBase64String(bytes);
    }

    // ==Base64加解密==================================================================
    /**
     * Base64加密
     */
    public static String Base64Encode(String str) {
        try {
            return new BASE64Encoder().encode(str.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解密
     */
    public static String Base64Decode(String str) {
//		str = str.replaceAll(" ", "+");
        try {
            return new String(new BASE64Decoder().decodeBuffer(str), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    
    
}
