package com.dafy.common.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.io.*;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.*;
import java.util.zip.CRC32;

/**
 * 计算文件MD5、SHA1，CRC32码
 * Created by liaoxudong on 2017/10/29.
 */
public class DigistUtils {
    private static final Logger logger = LoggerFactory.getLogger(DigistUtils.class);

    /**
     * 计算大文件 md5获取getMD5(); SHA1获取getSha1() CRC32获取 getCRC32()
     */
    protected static char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6',
            '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    public static MessageDigest messagedigest = null;

    /**
     * 对一个文件获取md5值
     *
     * @return md5串
     * @throws NoSuchAlgorithmException
     */
    public static String getMD5(File file) throws IOException,
            NoSuchAlgorithmException {

        messagedigest = MessageDigest.getInstance("MD5");
        FileInputStream in = new FileInputStream(file);
        FileChannel ch = in.getChannel();
        MappedByteBuffer byteBuffer = ch.map(FileChannel.MapMode.READ_ONLY, 0,
                file.length());
        messagedigest.update(byteBuffer);
        return bufferToHex(messagedigest.digest());
    }

    /**
     * @param target 字符串 求一个字符串的md5值
     * @return md5 value
     */
    public static String getMD5(String target) {
        return DigestUtils.md5Hex(target);
    }

    /***
     * 计算SHA1码
     *
     * @return String 适用于上G大的文件
     * @throws NoSuchAlgorithmException
     * */
    public static String getSha1(File file) throws OutOfMemoryError,
            IOException, NoSuchAlgorithmException {
        messagedigest = MessageDigest.getInstance("SHA-1");
        FileInputStream in = new FileInputStream(file);
        FileChannel ch = in.getChannel();
        MappedByteBuffer byteBuffer = ch.map(FileChannel.MapMode.READ_ONLY, 0,
                file.length());
        messagedigest.update(byteBuffer);
        return bufferToHex(messagedigest.digest());
    }

    /**
     * 获取文件CRC32码
     *
     * @return String
     */
    public static String getCRC32(File file) {
        CRC32 crc32 = new CRC32();
        // MessageDigest.get
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            byte[] buffer = new byte[8192];
            int length;
            while ((length = fileInputStream.read(buffer)) != -1) {
                crc32.update(buffer, 0, length);
            }
            return crc32.getValue() + "";
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (fileInputStream != null)
                    fileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String getMD5String(String s) {
        return getMD5String(s.getBytes());
    }

    public static String getMD5String(byte[] bytes) {
        try {
            messagedigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        messagedigest.update(bytes);
        return bufferToHex(messagedigest.digest());
    }

    /**
     * @return String
     * @Description 计算二进制数据
     */
    private static String bufferToHex(byte bytes[]) {
        return bufferToHex(bytes, 0, bytes.length);
    }

    private static String bufferToHex(byte bytes[], int m, int n) {
        StringBuffer stringbuffer = new StringBuffer(2 * n);
        int k = m + n;
        for (int l = m; l < k; l++) {
            appendHexPair(bytes[l], stringbuffer);
        }
        return stringbuffer.toString();
    }

    private static void appendHexPair(byte bt, StringBuffer stringbuffer) {
        char c0 = hexDigits[(bt & 0xf0) >> 4];
        char c1 = hexDigits[bt & 0xf];
        stringbuffer.append(c0);
        stringbuffer.append(c1);
    }

    public static boolean checkPassword(String password, String md5PwdStr) {
        String s = getMD5String(password);
        return s.equals(md5PwdStr);
    }

    /***
     * MD5加密 生成32位md5码
     *
     * @param inStr 待加密字符串
     * @return 返回32位md5码
     * @throws UnsupportedEncodingException
     */
    public static String md5Encode(String inStr) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

        byte[] byteArray = null;
        try {
            byteArray = inStr.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }

    public static final String CIPHER_ALGORITHM = "DES/ECB/NoPadding";


    /**
     *
     * 生成密钥key对象
     * @param keyStr 密钥字符串
     * @return 密钥对象
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws Exception
     */
    private static SecretKey keyGenerator(String keyStr) throws Exception {
        byte input[] = HexString2Bytes(keyStr);
        DESKeySpec desKey = new DESKeySpec(input);
        //创建一个密匙工厂，然后用它把DESKeySpec转换成
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey securekey = keyFactory.generateSecret(desKey);
        return securekey;
    }

    private static int parse(char c) {
        if (c >= 'a') return (c - 'a' + 10) & 0x0f;
        if (c >= 'A') return (c - 'A' + 10) & 0x0f;
        return (c - '0') & 0x0f;
    }

    // 从十六进制字符串到字节数组转换
    public static byte[] HexString2Bytes(String hexstr) {
        byte[] b = new byte[hexstr.length() / 2];
        int j = 0;
        for (int i = 0; i < b.length; i++) {
            char c0 = hexstr.charAt(j++);
            char c1 = hexstr.charAt(j++);
            b[i] = (byte) ((parse(c0) << 4) | parse(c1));
        }
        return b;
    }

    /**
     * 加密数据
     * @param data 待加密数据
     * @param key 密钥
     * @return 加密后的数据
     */
    public static String desEncrypt(String data, String key) {
        byte[] results = new byte[0];
        try {
            Key deskey = keyGenerator(key);
            // 实例化Cipher对象，它用于完成实际的加密操作
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            SecureRandom random = new SecureRandom();
            // 初始化Cipher对象，设置为加密模式
            cipher.init(Cipher.ENCRYPT_MODE, deskey, random);

            data = convertData(data);
            results = cipher.doFinal(data.getBytes("utf-8"));
        } catch (Exception e) {
            logger.error("DES加密失败：",e);
        }
        // 执行加密操作。加密后的结果通常都会用Base64编码进行传输
        return Base64.encodeBase64String(results);
    }

    /**
     * 用空格补齐8的倍数字节长度
     * @param data
     * @return
     * @throws UnsupportedEncodingException
     */
    private static String convertData(String data) throws UnsupportedEncodingException{

        StringBuilder sb = new StringBuilder();
        int cnt = data.getBytes("UTF-8").length % 8 ;
        if(cnt != 0){
            for(int i = 0 ; i < 8-cnt; i++){
                sb.append(" ");
            }
        }
        return data + sb.toString();
    }

    /**
     * 解密数据
     * @param data 待解密数据
     * @param key 密钥
     * @return 解密后的数据
     */
    public static String desDecrypt(String data, String key) {
        try {
            Key deskey = keyGenerator(key);
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            //初始化Cipher对象，设置为解密模式
            cipher.init(Cipher.DECRYPT_MODE, deskey);
            // 执行解密操作
            return new String(cipher.doFinal(Base64.decodeBase64(data)),"utf-8");
        } catch (Exception e) {
            logger.error("DES解密失败：",e);
        }
        return null;
    }
}
