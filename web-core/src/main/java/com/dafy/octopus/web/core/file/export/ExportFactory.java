package com.dafy.octopus.web.core.file.export;

import com.dafy.octopus.web.core.file.FileFactory;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 专用导出实现即可
 * Created by liaoxudong
 * Date:2018/6/22
 */

public abstract class ExportFactory extends FileFactory{

    @Override
    public String upload(byte[] bytes, String fileName, String fileType, String saveFolder) {
        // do nothing
        return null;
    }

    @Override
    public void download(HttpServletResponse response, String url) {
        // do nothing
    }

    @Override
    public abstract void export(HttpServletResponse response, String fileName, Map<String, Object> data);
}
