package com.dafy.octopus.rental.common;

import com.alibaba.fastjson.JSONObject;
import com.dafy.common.util.HttpUtils;
import com.dafy.octopus.web.core.context.ApplicationContextProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;

import java.util.HashMap;
import java.util.Map;

public class PhoneLenderHttpUtil {
    private static final Logger logger = LoggerFactory.getLogger(PhoneLenderHttpUtil.class);

    public static JSONObject doPost(String data, String urlPath) throws Exception {
        String aseKey = ApplicationContextProvider.getBean(Environment.class).getProperty("config.phone.lender.ase.key");
        String urlBasePath = ApplicationContextProvider.getBean(Environment.class).getProperty("config.phone.lender.http.url");

        Map<String, Object> params = new HashMap<>();
        params.put("data", AesUtil.aesEncrypt(data, aseKey));
        logger.info("请求数据：{}",data);
        String response = HttpUtils.doPost(urlBasePath + urlPath, params);

        JSONObject result = JSONObject.parseObject(response);

        if (result.get("data") != null) result.put("data", AesUtil.aesDecrypt(result.getString("data").replaceAll("\\\\n", "").replaceAll("\\\\r", ""), aseKey));

        return result;
    }

    public static JSONObject doGet(String data, String urlPath){
        String url = urlPath + "?data=" + data;

        String response = HttpUtils.doGet(url);
        return JSONObject.parseObject(response);
    }
}
