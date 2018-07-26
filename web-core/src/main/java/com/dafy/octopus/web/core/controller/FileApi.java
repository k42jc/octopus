package com.dafy.octopus.web.core.controller;

import com.dafy.common.po.Response;
import com.dafy.octopus.web.core.context.ApplicationContextProvider;
import com.dafy.octopus.web.core.file.FileFactory;
import com.dafy.octopus.web.core.utils.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by liaoxudong
 * Date:2018/6/25
 */

@RestController
@RequestMapping("/file")
public class FileApi {
    private static final Logger logger = LoggerFactory.getLogger(FileApi.class);
//    @Value("${config.file.upload.type}")
//    private String fileUpDownloadType;

    /**
     * form类型文件上传
     * @param file 文件对象
     * @param type 上传类型 用于保存子文件夹,如：type=工单附件,type=attach
     * @return 文件访问url
     */
    private static final Environment environment = ApplicationContextProvider.getBean(Environment.class);
    @PostMapping("/upload")
    public Response upload(@RequestParam("file") MultipartFile file,@RequestParam("type") String type){
        String fileUpDownloadType = environment.getProperty("config.file.upload.type");
        FileFactory fileFactory = FileFactory.getInstance(fileUpDownloadType);
        String contentType = file.getContentType();
        logger.info("文件上传,contentType:{},fileName:{}", contentType, file.getOriginalFilename());
        String fileName = file.getOriginalFilename();
        /*String fileType = fileName.substring(fileName.lastIndexOf(".") + 1);
        if(!fileName.contains("."))
            fileType = contentType.substring(contentType.lastIndexOf("/")+1,contentType.length());*/
        Map<String, Object> resultMap = new HashMap<>();
        try {
            String url =  fileFactory.upload(file.getBytes(), fileName, "", type);
            resultMap.put("url", url);
        } catch (Exception e) {
            logger.error("文件上传失败：{}",e);
            CommonUtils.throwException("error", "文件上传失败");
        }
        return CommonUtils.buildSuccessResp(resultMap);

    }

    @GetMapping("/download/{url}")
    public void download(HttpServletResponse response){
        String fileUpDownloadType = environment.getProperty("config.file.upload.type");
        FileFactory fileFactory = FileFactory.getInstance(fileUpDownloadType);
//        fileFactory.download(response,filePath);
    }
}
