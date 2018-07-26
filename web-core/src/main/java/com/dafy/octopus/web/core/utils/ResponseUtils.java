package com.dafy.octopus.web.core.utils;

import com.dafy.common.po.Response;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
 * 构建返回数据工具类
 * Created by liaoxudong
 * Date:2018/4/17
 */

public class ResponseUtils {

    /**
     * 返回json格式数据
     * @param response
     * @param resp
     */
    public static void responseJson(ServletResponse response,Response resp){
        PrintWriter out = null;
        HttpServletResponse res = (HttpServletResponse) response;
        try {
            res.setCharacterEncoding("UTF-8");
            res.setContentType("application/json");
            out = response.getWriter();
            out.println(/*CommonUtils.buildResp(ResponseCode.LOGIN_EXPIRE)*/resp);
        } catch (Exception e) {
        } finally {
            if (null != out) {
                out.flush();
                out.close();
            }
        }
    }

    /**
     * 设置http返回头
     * @param response
     * @param headers http头参数
     */
    public static void setHeader(HttpServletResponse response, Map<String, Object> headers) {
        if (response == null || headers == null || headers.isEmpty()) {
            return;
        }
        headers.forEach((key,value) -> {
            response.setHeader(key,value+"");
        });
    }

    /**
     * 用于文件流类型
     * 如文件下载、导出等
     * @param response
     * @param bytes 对应的文件二进制
     */
    public static void writeAndFlush(HttpServletResponse response, byte[] bytes){
        try {
            ServletOutputStream outputStream = response.getOutputStream();
            outputStream.write(bytes);
            outputStream.flush();
//            outputStream.close(); fix stream is closed
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
