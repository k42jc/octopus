package com.dafy.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Created by liaoxudong
 * Date:2017/11/23
 */

public class GzipUtils {
    private static final Logger logger = LoggerFactory.getLogger(GzipUtils.class);
    // 压缩后“轻加密”秘钥，二进制数据前8位异或
    private static final int[] XOR_HOLDER = {0x1F, 0x8C, 0xF2, 0x5B, 0xE6, 0xC2, 0xE4, 0xCF};

    /**
     * gzip压缩并与给定参数异或后返回
     * @param param 待压缩字符串
     * @return 压缩后字符串
     */
    public static String compress(String param) {
        if (StringUtils.isEmpty(param)) {
            return StringUtils.EMPTY_STR;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        GZIPOutputStream gzip=null;
        try {
            gzip = new GZIPOutputStream(out);
            gzip.write(param.getBytes());
        } catch (IOException e) {
            logger.error(e.getMessage());
            return StringUtils.EMPTY_STR;
        }finally{
            if(gzip!=null){
                try {
                    gzip.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        byte[] bytes = out.toByteArray();
        // 异或操作
        bytes = xor(bytes, XOR_HOLDER);
        return new sun.misc.BASE64Encoder().encode(bytes);
    }

    /**
     * 将二进制数据与给定参数的位数异或操作
     * @param bytes
     * @param xorHolder
     * @return
     */
    private static byte[] xor(byte[] bytes, int[] xorHolder) {
        if (bytes.length >= xorHolder.length) {
            for(int i=0;i<xorHolder.length;i++) {
                bytes[i] ^= xorHolder[i];
            }
        }
        return bytes;
    }

    /**
     * 使用gzip解压字符串
     * @param param 待解压字符串
     * @return 解压后字符串
     */
    public static String deCompress(String param) {
        if (StringUtils.isEmpty(param)) {
            return StringUtils.EMPTY_STR;
        }
        ByteArrayOutputStream out= new ByteArrayOutputStream();
        ByteArrayInputStream in=null;
        GZIPInputStream ginzip=null;
        byte[] compressed=null;
        String decompressed = null;
        try {
            compressed = new sun.misc.BASE64Decoder().decodeBuffer(param);
            // 反异或操作
            compressed = xor(compressed, XOR_HOLDER);
            in=new ByteArrayInputStream(compressed);
            ginzip=new GZIPInputStream(in);

            byte[] buffer = new byte[1024];
            int offset = -1;
            while ((offset = ginzip.read(buffer)) != -1) {
                out.write(buffer, 0, offset);
            }
            decompressed=out.toString();
        } catch (IOException e) {
            logger.error(e.getMessage());
            return StringUtils.EMPTY_STR;
        } finally {
            if (ginzip != null) {
                try {
                    ginzip.close();
                } catch (IOException e) {
                    logger.error(e.getMessage());
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    logger.error(e.getMessage());
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    logger.error(e.getMessage());
                }
            }
        }

        return decompressed;
    }

    public static void main(String[] args) {
        String str = "123456";
        String compress = compress(str);
        System.err.println(compress);

        String s = deCompress(compress);
        System.err.println(s);
    }

}
