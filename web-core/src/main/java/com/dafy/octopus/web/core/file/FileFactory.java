package com.dafy.octopus.web.core.file;

import com.dafy.common.util.StringUtils;
import com.dafy.octopus.web.core.file.export.ExportExcelFactory;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 文件上传
 * Created by liaoxudong
 * Date:2018/6/22
 */

public abstract class FileFactory {

    /**
     * 方便通过简单配置实现不同文件上传切换
     * @param type 文件上传类型或导出类型 local:本地文件上传 oos:阿里云对象存储文件上传 excel:导出excel
     * @return 具体的操作工厂
     */
    public static FileFactory getInstance(String type) {
        if (StringUtils.isEmpty(type)) {
            return null;
        }
        switch (type.toLowerCase()) {
            case "local":
                return new LocalUpDownloadFactory();
            case "oos":
                // TODO 阿里云文件上传
                break;
            case "excel":
                return new ExportExcelFactory();
        }
        return null;
    }

    /**
     * 文件上传
     * @param bytes 文件二进制
     * @param fileName 文件名
     * @param fileType 文件类型
     * @param saveFolder 保存子目录
     * @return 返回url
     */
    public abstract String upload(byte[] bytes, String fileName, String fileType, String saveFolder);


    /**
     * 文件下载
     * @param response
     * @param url
     */
    public abstract void download(HttpServletResponse response,String url);

    /**
     * 导出内容到指定文件
     * @param response
     * @param fileName 文件名
     * @param data 填充数据
     */
    public abstract void export(HttpServletResponse response, String fileName, Map<String, Object> data);
}
