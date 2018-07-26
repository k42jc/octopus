package com.dafy.octopus.web.core.file;

import com.dafy.common.util.StringUtils;
import com.dafy.octopus.web.core.utils.CommonUtils;
import com.dafy.octopus.web.core.utils.ResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 文件上传下载
 * Created by liaoxudong
 * Date:2018/6/22
 */

public abstract class UpDownloadFileFactory extends FileFactory {
    private static final Logger logger = LoggerFactory.getLogger(UpDownloadFileFactory.class);
    @Override
    public abstract String upload(byte[] bytes, String fileName, String fileType, String saveFolder);

    @Override
    public void download(HttpServletResponse response, String url) {
        if (response == null || StringUtils.isEmpty(url)) {
            CommonUtils.throwIllegalParamsException("url");
        }
        logger.info("文件下载：{}", url);
        Map<String, Object> headers = new HashMap<>();
        headers.put("Content-Type", "application/octet-stream");
        String fileName = url.substring(url.lastIndexOf("/") + 1);
        headers.put("Content-Disposition", "attachment;filename="+fileName);
        ResponseUtils.setHeader(response,headers);
        ResponseUtils.writeAndFlush(response,getFileBytes(url));
    }

    /**
     * 通过url获取到服务器存在的文件二进制
     * @param url
     * @return
     */
    protected abstract byte[] getFileBytes(String url);

    @Override
    public void export(HttpServletResponse response, String fileName, Map<String, Object> data) {
        // do nothing
    }
}
