package com.dafy.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

/**
 * 配置工具类
 *
 * 支持properties,xml,yaml/yml等配置文件
 *
 * 使用方式如下：
 *  读取properties/xml下的配置：ConfigUtils.PROP_XML.getProp();
 *  读取yml/yaml下的配置：ConfigUtils.YAML.getProp();
 *
 * Created by liaoxudong on 2017/8/9.
 */
@SuppressWarnings("all")
public enum ConfigUtils {
    // 读取系统目录下的properties配置文件
    PROP_XML("properties,xml") {
        @Override
        public String getProp(String key) {
            return getProp(key, "");
        }

        @Override
        public String getProp(String key, String defaultValue) {
            return properties.getProperty(key, "");
        }

        @Override
        public <T> T getProp(String key, Class<T> clazz) {
            return getProp(key, clazz, null);
        }

        @Override
        public <T> T getProp(String key, Class<T> clazz, T defaultValue) {
            String value = getProp(key);
            if (value == null || "".equals(value)) {
                return defaultValue;
            }
            if ("Integer".equalsIgnoreCase(clazz.getSimpleName())) {
                return (T) Integer.valueOf(value);
            }
            if ("Long".equalsIgnoreCase(clazz.getSimpleName())) {
                return (T) Long.valueOf(value);
            }
            if ("Double".equalsIgnoreCase(clazz.getSimpleName())) {
                return (T) Double.valueOf(value);
            }
            return null;
        }
    },
    // 读取系统目录下的yml配置文件
    YAML("yaml,yml"){
        @Override
        public String getProp(String key) {
            return getProp(key, "");
        }

        @Override
        public String getProp(String key, String defaultValue) {
            Object result = yamlMap.get(key);
            if (ObjectUtils.isEmpty(result)) {
                return defaultValue;
            }
            return String.valueOf(result);
        }

        @Override
        public <T> T getProp(String key, Class<T> clazz) {
            return getProp(key, clazz, null);
        }

        @Override
        public <T> T getProp(String key, Class<T> clazz, T defaultValue) {
            String value = getProp(key);
            if (value == null || "".equals(value)) {
                return defaultValue;
            }
            if ("Integer".equalsIgnoreCase(clazz.getSimpleName())) {
                return (T) Integer.valueOf(value);
            }
            if ("Long".equalsIgnoreCase(clazz.getSimpleName())) {
                return (T) Long.valueOf(value);
            }
            if ("Double".equalsIgnoreCase(clazz.getSimpleName())) {
                return (T) Double.valueOf(value);
            }
            return null;
        }
    }

    ;
    private final Logger logger = LoggerFactory.getLogger(ConfigUtils.class);
    private String[] fileType;
    protected Properties properties;
    protected Map<String, Object> yamlMap = new LinkedHashMap<>();

    ConfigUtils(String fileType) {
        this.fileType = fileType.split(",");
        String path = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        logger.debug("配置文件加载路径：{}",path);
        if (fileType.contains("yaml") || fileType.contains("yml")) {
            Yaml yaml = new Yaml();
            try {
                loadYamlFile(yaml,new File(path));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(fileType.contains("properties") || fileType.contains("xml")){
            properties = new Properties();
            try {
                loadPropFile(properties, new File(path));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            throw new IllegalStateException("不支持此类型配置文件");
        }


    }

    /**
     * 加载yaml/yml配置
     * @param yaml
     * @param file
     */
    private void loadYamlFile(Yaml yaml, File file) throws IOException {
        if (file != null && file.exists()) {
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                for (File f : files) {
                    if (f.isDirectory()) {
                        loadYamlFile(yaml, f);
                    } else if (checkFileType(f)) {
                        f.setReadable(true);
                        processYaml(yaml,new FileInputStream(f));
                    }
                }
            } else if (checkFileType(file)) {
                file.setReadable(true);
                processYaml(yaml,new FileInputStream(file));
            }
        }
    }

    private boolean checkFileType(File f) {
        boolean result = false;
        for (String ft : this.fileType) {
            result = f.getName().endsWith(ft);
            if (result) {
                return result;
            }
        }
        return result;
    }

    /**
     * 加载yaml文件
     * @param yaml
     * @param fileInputStream
     * @throws IOException
     */
    private void processYaml(Yaml yaml, FileInputStream fileInputStream) throws IOException {
        /*UnicodeReader reader = new UnicodeReader(fileInputStream);
        try {
            Iterator var6 = yaml.loadAll(reader).iterator();

            while(var6.hasNext()) {
                Object object = var6.next();
                Map<String, Object> stringObjectMap = this.asMap(object);
                System.out.println(stringObjectMap);
//                yamlMap.putAll();
            }
        } finally {
            reader.close();
        }*/
        buildFlattenedMap(yamlMap,yaml.loadAs(fileInputStream, Map.class),null);
    }

    /**
     * 摘自spring源码 @see YamlProcesser
     * 将yaml加载完后map“舒展开”
     * @return
     */
    private void buildFlattenedMap(Map<String, Object> result, Map<String, Object> source, String path) {
        Iterator var4 = source.entrySet().iterator();

        while(true) {
            while(var4.hasNext()) {
                Map.Entry<String, Object> entry = (Map.Entry)var4.next();
                String key = (String)entry.getKey();
                if (!StringUtils.isEmpty(path)) {
                    if (key.startsWith("[")) {
                        key = path + key;
                    } else {
                        key = path + '.' + key;
                    }
                }

                Object value = entry.getValue();
                if (value instanceof String) {
                    result.put(key, value);
                } else if (value instanceof Map) {
                    Map<String, Object> map = (Map)value;
                    this.buildFlattenedMap(result, map, key);
                } else if (value instanceof Collection) {
                    Collection<Object> collection = (Collection)value;
                    int count = 0;
                    Iterator var10 = collection.iterator();

                    while(var10.hasNext()) {
                        Object object = var10.next();
                        this.buildFlattenedMap(result, Collections.singletonMap("[" + count++ + "]", object), key);
                    }
                } else {
                    result.put(key, value != null ? value : "");
                }
            }

            return;
        }
    }

    /**
     * 将文件加载到properties
     *
     * @param properties
     * @param file
     */
    private void loadPropFile(Properties properties, File file) throws IOException {
        if (file != null && file.exists()) {
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                for (File f : files) {
                    if (f.isDirectory()) {
                        loadPropFile(properties, f);
                    } else if (checkFileType(file)) {
                        f.setReadable(true);
                        if(file.getName().endsWith(".xml")){
                            properties.loadFromXML(new FileInputStream(f));
                        }else{
                            properties.load(new FileInputStream(f));
                        }
                    }
                }
            } else if (checkFileType(file)) {
                file.setReadable(true);
                if(file.getName().endsWith(".xml")){
                    properties.loadFromXML(new FileInputStream(file));
                }else{
                    properties.load(new FileInputStream(file));
                }
            }
        }
    }

    protected abstract String getProp(String key);

    protected abstract String getProp(String key, String defaultValue);

    protected abstract <T> T getProp(String key, Class<T> clazz);

    protected abstract <T> T getProp(String key, Class<T> clazz, T defaultValue);

    public static String get(String key){
        return get(key,"");
    }

    public static String get(String key, String defaultValue){
        String value = PROP_XML.getProp(key, defaultValue);
        if (defaultValue.equals(value)) {
            value = YAML.getProp(key, defaultValue);
        }
        return value;
    }

    public static <T> T get(String key, Class<T> clazz){
        return get(key,clazz,null);
    }

    public static <T> T get(String key, Class<T> clazz, T defaultValue){
        String value = get(key);
        if (value == null || "".equals(value)) {
            return defaultValue;
        }
        if ("Integer".equalsIgnoreCase(clazz.getSimpleName())) {
            return (T) Integer.valueOf(value);
        }
        if ("Long".equalsIgnoreCase(clazz.getSimpleName())) {
            return (T) Long.valueOf(value);
        }
        if ("Double".equalsIgnoreCase(clazz.getSimpleName())) {
            return (T) Double.valueOf(value);
        }
        return null;
    }




}
