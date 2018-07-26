package com.dafy.octopus.web.core.file.export;

import com.dafy.octopus.web.core.utils.CommonUtils;
import com.dafy.octopus.web.core.utils.ResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 导出excel工厂类
 * Created by liaoxudong
 * Date:2018/6/19
 */

public class ExportExcelFactory extends ExportFactory{
    private static final Logger logger = LoggerFactory.getLogger(ExportExcelFactory.class);

    /**
     *
     * @param response
     * @param fileName 文件名
     * @param data 填充数据 "headers,contents"分别表示表格头、内容数据
     */
    @Override
    public void export(HttpServletResponse response, String fileName, Map<String, Object> data) {
        logger.info("导出excel：{}",fileName += ".xls");
        // 获取标题、表格头、内容数据
        CommonUtils.assertHasParams(data,"headers,contents");
        List<String> headers = (List) data.get("headers");
        List<List<Object>> contents = (List) data.get("contents");
        // 使用流将数据导出
        OutputStream out = null;
        try {
            // 防止中文乱码
            Map<String,Object> respHeaders = new HashMap<>();
            respHeaders.put("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes(), "iso-8859-1"));
            respHeaders.put("Content-Type","application/vnd.ms-excel;charset=utf-8");
            ResponseUtils.setHeader(response,respHeaders);
//            response.setHeader("content-type", "application/octet-stream");
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ExportExcel ex = new ExportExcel(fileName, headers,contents,os);
            ex.export();
            ResponseUtils.writeAndFlush(response,os.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
