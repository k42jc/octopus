package com.dafy.common.util;

import com.dafy.common.constant.Constants;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * json工具类
 */
public final class JsonUtils {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        // 设置输入时忽略在JSON字符串中存在但Java对象实际没有的属性
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); // 2011-11-07
        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, false);

        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);

        objectMapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);

        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        objectMapper.setDateFormat(new SimpleDateFormat(Constants.DATE_TIME_FORMAT));
    }

    /**
     * 对象转换为json字符串
     *
     * @param object
     * @return
     */
    public static String toJson(Object object) {
        if (object == null)
            return null;
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 对象转换为格式化json
     *
     * @param object
     * @return
     */
    public static String toPrettyJson(Object object) {
        if (object == null)
            return null;
        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * json字符串转对象
     *
     * @param jsonStr
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T fromJson(String jsonStr, Class<T> clazz) {
        if (StringUtils.isEmpty(jsonStr))
            return null;
        try {
            return objectMapper.readValue(jsonStr, clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * json字符串转map,HashMap实现
     *
     * @param jsonStr
     * @return
     */
    public static Map<String, Object> fromJson(String jsonStr) {
        if (StringUtils.isEmpty(jsonStr))
            return null;
        try {
            return objectMapper.readValue(jsonStr, HashMap.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private JsonUtils() {
    }
}
