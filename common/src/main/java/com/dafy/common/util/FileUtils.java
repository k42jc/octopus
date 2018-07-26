package com.dafy.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * 文件工具类
 * Created by liaoxudong
 * Date:2017/10/27
 */

public class FileUtils {
    private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);

    /**
     * 保存文件到目录
     *
     * @param url 获取文件的路径
     * @return
     */
    public static boolean saveToPath(InputStream inputStream, String url) {
        File file = new File(url);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        file.setWritable(true);
        try {
            FileOutputStream outputStream = new FileOutputStream(file);
            int n = 0;// 每次读取的字节长度
            byte[] bb = new byte[1024];// 存储每次读取的内容
            while ((n = inputStream.read(bb)) != -1) {
                outputStream.write(bb, 0, n);// 将读取的内容，写入到输出流当中
            }
            outputStream.close();// 关闭输入输出流
            inputStream.close();
            logger.debug("文件写入成功：{}",url);
            return true;
        } catch (Exception e) {
            logger.error("文件写入失败:",e);
            throw new RuntimeException("文件写入失败！");
        }
    }

    public static void main(String[] args) {
        /*try {
            boolean b = saveToPath(new FileInputStream(new File("D:/idcard.jpg")), "E:/xxx/ddd/xxxx.jpg");
            System.err.println("b:"+b);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }*/
        /*try {
            File tempFile = File.createTempFile("temp", "temp");
            System.out.println(tempFile);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        /*File fileFromBytes = getFileFromBytes(getBytesFromFile(new File("E:\\yihui_upload\\1d75c234-6f51-48b7-9ddd-9ae41c7bf1f3.png")), ".png");
        System.out.println(fileFromBytes);*/
        /*byte[] bytes = HttpUtils.download("http://xxxxooo.oss-cn-hangzhou.aliyuncs.com/2d8ccbe1ef7748fbbfc6ea9fd1b22e27.png", null);
        File fileFromBytes = getFileFromBytes(bytes, ".png");
        System.out.println(fileFromBytes);*/
    }


    /**
     * 把字节数组的图片写到磁盘
     * @param bytes
     * @param path
     * @param append 是否拼接到文件末尾写入
     */
    public static void writeByteToFile(byte[] bytes, String path,boolean append) {
        File file = new File(path);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        file.setWritable(true);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file,append);
            fos.write(bytes);
            fos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取文件的二进制数组
     * @param file
     * @return
     */
    public static byte[] getBytesFromFile(File file) {
        byte[] ret = null;
        try {
            if (file == null) {
                // log.error("helper:the file is null!");
                return null;
            }
            FileInputStream in = new FileInputStream(file);
            ByteArrayOutputStream out = new ByteArrayOutputStream(4096);
            byte[] b = new byte[4096];
            int n;
            while ((n = in.read(b)) != -1) {
                out.write(b, 0, n);
            }
            in.close();
            out.close();
            ret = out.toByteArray();
        } catch (IOException e) {
            // log.error("helper:get bytes from file process error!");
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * 获取inputStream的二进制数组
     * @param inputStream
     * @return
     */
    public static byte[] getBytesFromInputStream(InputStream inputStream) {
        byte[] ret = null;
        try {
            if (inputStream == null) {
                // log.error("helper:the file is null!");
                return null;
            }
            ByteArrayOutputStream out = new ByteArrayOutputStream(4096);
            byte[] b = new byte[4096];
            int n;
            while ((n = inputStream.read(b)) != -1) {
                out.write(b, 0, n);
            }
            inputStream.close();
            out.close();
            ret = out.toByteArray();
        } catch (IOException e) {
            // log.error("helper:get bytes from file process error!");
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * 创建临时文件
     * @param bytes
     * @param fileType 文件类型 带. 如.png
     * @return
     */
    public static File getFileFromBytes(byte[] bytes,String fileType) {
        File tempFile = null;
        try {
            tempFile = File.createTempFile("temp", fileType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        tempFile.setWritable(true);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(tempFile);
            fos.write(bytes);
            fos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return tempFile;
    }


}
