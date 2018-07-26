package com.dafy.octopus.web.core.file;

import com.dafy.common.constant.Constants;
import com.dafy.common.util.DateTimeUtils;
import com.dafy.common.util.FileUtils;
import com.dafy.common.util.StringUtils;
import com.dafy.octopus.web.core.context.ApplicationContextProvider;
import com.dafy.octopus.web.core.utils.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;

import java.io.File;
import java.util.Date;

/**
 * 本地文件上传下载实现
 * Created by liaoxudong
 * Date:2018/6/22
 */

public class LocalUpDownloadFactory extends UpDownloadFileFactory {
    private static final Logger logger = LoggerFactory.getLogger(LocalUpDownloadFactory.class);

    private static final Environment env = ApplicationContextProvider.getBean(Environment.class);
    private String savePath = env.getProperty("config.file.upload.save-path");
    private String baseUrl = env.getProperty("config.file.upload.base-url");

    @Override
    public String upload(byte[] bytes, String fileName, String fileType, String saveFolder) {
        if (bytes == null || fileName == null || fileType == null) {
            CommonUtils.throwIllegalParamsException("bytes || fileName || fileType");
        }
        // 未指定文件名则自动生成
        fileName = (StringUtils.isEmpty(fileName)? DateTimeUtils.formatDatetime1(new Date()):fileName) + (StringUtils.isEmpty(fileType)?"":("." + fileType));

        // 解决中文问题，liunx下中文路径，图片显示问题
        if (!savePath.endsWith(Constants.FILE_SEPARATOR)) {
            savePath = savePath + Constants.FILE_SEPARATOR;
        }
        // 每个用户的文件都存放在独立目录
        String filePath = saveFolder + "/" + fileName;
        FileUtils.writeByteToFile(bytes,savePath+filePath,false);
        //        asyncSaveDB(dest,userCode,filePath,fileType);
        logger.info("文件存放目录：{}", savePath);
        return baseUrl +filePath;
    }

    @Override
    protected byte[] getFileBytes(String url) {
        if (StringUtils.isEmpty(url)) {
            CommonUtils.throwIllegalParamsException("url");
        }
        String fullUrl = url;
        String fileSavePath = fullUrl.replace(baseUrl, "");
        File file = new File(fileSavePath);
        if (!file.exists()) {
            logger.warn("文件不存在：{}", fileSavePath);
            CommonUtils.throwException("error", "文件不存在");
        }
        return FileUtils.getBytesFromFile(file);
    }

}
