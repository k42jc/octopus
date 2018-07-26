package com.dafy.cache.factory;

import com.dafy.cache.redis.ObjectRedisTemplate;
import com.dafy.common.util.ConfigUtils;
import com.dafy.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 缓存操作，配置工厂便于各模块缓存操作
 * <p>
 * Created by liaoxudong
 * Date:2017/10/25
 */
public class CacheFactory {
    private static final Logger logger = LoggerFactory.getLogger(CacheFactory.class);
    // 默认缓存过期时间 一天
    public static final int DEFAULT_CACHE_EXPIRE_TIME = ConfigUtils.get("config.cache.defaultExpireTime", Integer.class,86400);

    private CacheFactory() {
    }

    private static ObjectRedisTemplate objectRedisTemplate;
    private static StringRedisTemplate stringRedisTemplate;
    private static ValueOperations<String, String> valueOperations;
    private static HashOperations<String, String, Object> hashOperations;
    private static ListOperations<String, Object> listOperations;


    public static void setObjectRedisTemplate(ObjectRedisTemplate objectRedisTemplate) {
        CacheFactory.objectRedisTemplate = objectRedisTemplate;
        hashOperations = objectRedisTemplate.opsForHash();
        listOperations = objectRedisTemplate.opsForList();
    }

    public static void setStringRedisTemplate(StringRedisTemplate stringRedisTemplate) {
        CacheFactory.stringRedisTemplate = stringRedisTemplate;
        valueOperations = stringRedisTemplate.opsForValue();
    }

    public static ObjectRedisTemplate getObjectRedisTemplate() {
        return objectRedisTemplate;
    }

    public static StringRedisTemplate getStringRedisTemplate() {
        return stringRedisTemplate;
    }


    /**
     * 获取自增，默认过期时间1天
     *
     * @param key
     * @param detail 增长因子
     * @return
     */
    public static long incrBy(String key, long detail) {
        return incrBy(key, detail, DEFAULT_CACHE_EXPIRE_TIME);
    }

    public static long incrBy(String key, long detail, long expireTime) {
        logger.info("incr {} by {} expire time {}", key, detail, expireTime);
        Long aLong = valueOperations.increment(key, detail);
        valueOperations.getOperations().expire(key, expireTime, TimeUnit.SECONDS);
        return aLong;
    }

    /**
     * 过期某key 针对普通key操作
     *
     * @param key
     * @param expireTime
     */
    public static void expireString(String key, long expireTime) {
        valueOperations.getOperations().expire(key, expireTime, TimeUnit.SECONDS);
    }

    /**
     * 以1为增长因子 获取下一个增长值
     *
     * @param key
     * @return
     */
    public static long incr(String key) {
        return valueOperations.increment(key, 1);
    }

    /**
     * 存入字符串
     *
     * @param key
     * @param value
     */
    public static void putString(String key, String value) {
        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(value)) {
            logger.warn("Cache not supported empty key || value");
            return;
        }
        try {
            valueOperations.set(key, value);
            logger.info("Data cache success:[{}]-[{}]", key, value);
        } catch (Exception e) {
            logger.error("缓存数据失败：{}", key, e);
        }
    }

    /**
     * 存入字符串
     *
     * @param key
     * @param value
     */
    public static String getSetString(String key, String value) {
        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(value)) {
            logger.warn("Cache not supported empty key || value");
            return null;
        }
        try {
            logger.info("Data cache success:[{}]-[{}]", key, value);
            return valueOperations.getAndSet(key, value);
        } catch (Exception e) {
            logger.error("缓存数据失败：{}", key, e);
        }
        return null;
    }

    /**
     * 存入字符串并设置超时时间
     *
     * @param key
     * @param value
     * @param seconds 秒
     */
    public static void putString(String key, String value, long seconds) {
        try {
            valueOperations.set(key, value, seconds, TimeUnit.SECONDS);
            logger.info("Data cache success:[{}]-[{}]，超时时间：[{}]", key, value, seconds);
        } catch (Exception e) {
            logger.error("缓存数据失败：{}", key, e);
        }
    }

    /**
     * 获取字符串
     *
     * @param key
     * @return
     */
    public static String getString(String key) {
        try {
            String result = null;
            Object value = valueOperations.get(key);
            if (value != null) {
                result = value.toString();
            }
            logger.info("get cache::[{}]-[{}]", key, result);
            return result;
        } catch (Exception e) {
            logger.error("获取缓存数据失败：{}", key, e);
        }
        return null;
    }

    /**
     * 获取字符串，不存在返回defaultValue
     *
     * @param key
     * @param defaultValue
     * @return
     */
    public static String getString(String key, String defaultValue) {
        String result = getString(key);
        if (StringUtils.isEmpty(result)) {
            logger.info("return defaultValue:[{}]", defaultValue);
            return defaultValue;
        }
        return result;
    }

    /**
     * 删除字符串
     *
     * @param key
     */
    public static void deleteString(String key) {
        try {
            valueOperations.getOperations().delete(key);
            logger.warn("delete cache,key:[{}]", key);
        } catch (Exception e) {
            logger.error("delete cache key:[{}] falure!", key);
        }
    }

    /**
     * set if not exists，key存在不做任何操作，否则设值
     *
     * @param key
     * @param newValue
     * @return
     */
    public static Boolean setNX(String key, String newValue) {
        logger.info("setNx：[{}]--[{}]", key, newValue);
        return valueOperations.setIfAbsent(key, newValue);
    }


    /**
     * 判断是否存在某个hash key
     *
     * @param key
     * @param hKey
     * @return
     */
    public static boolean containsHKey(String key, String hKey) {
        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(hKey)) {
            return false;
        }
        Boolean aBoolean = hashOperations.hasKey(key, hKey);
        logger.debug("hExists key:{} hKey:{},result:{}", key, hKey, aBoolean);
        return aBoolean;
    }

    /**
     * 存入hash字符
     *
     * @param key
     * @param hKey
     * @param hValue
     */
    public static void putHKey(String key, String hKey, Object hValue) {
        hashOperations.put(key, hKey, hValue);
        logger.info("cache hash key:{},hKey:{},hValue:{}", key, hKey, hValue);
    }

    /**
     * 只有在hValue不存在的情况下才存入值
     *
     * @param key
     * @param hKey
     * @param hValue
     */
    public static boolean putHKeyIfNull(String key, String hKey, Object hValue) {
        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(hKey)) {
            throw new IllegalArgumentException("please check params key||hKey");
        }
        if (!containsHKey(key, hKey)) {
            putHKey(key, hKey, hValue);
            return true;
        }
        return false;
    }

    /**
     * 只有在hValue不存在的情况下才存入值
     *
     * @param key
     * @param hKey
     * @param hValue
     */
    public static void putHKeyIfNull(String key, String hKey, Object hValue, long seconds) {
        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(hKey)) {
            throw new IllegalArgumentException("please check params key||hKey");
        }
        if (!containsHKey(key, hKey)) {
            putHKey(key, hKey, hValue, seconds);
        }
    }

    /**
     * 存储hash结构 并设置过期时间
     *
     * @param key
     * @param hKey
     * @param hValue
     * @param seconds
     */
    public static void putHKey(String key, String hKey, Object hValue, long seconds) {
        putHKey(key, hKey, hValue);
        logger.info("expire:【{}】", seconds);
        hashOperations.getOperations().expire(key, seconds, TimeUnit.SECONDS);
    }


    /**
     * 缓存map结构
     *
     * @param key
     * @param map
     */
    public static void putHAll(String key, Map<String, Object> map) {
        hashOperations.putAll(key, map);
        logger.info("cache hash key:{},hash:{}", key, map);
    }

    /**
     * 存入map并设置过期时间
     *
     * @param key
     * @param map
     * @param seconds
     */
    public static void putHAll(String key, Map<String, Object> map, long seconds) {
        putHAll(key, map);
        logger.info("expire:【{}】", seconds);
        hashOperations.getOperations().expire(key, seconds, TimeUnit.SECONDS);
    }


    /**
     * 直接获取所有的hash并存入map返回
     *
     * @param key
     * @return
     */
    public static Map<String, Object> getHAll(String key) {
        Set<String> keys = hashOperations.keys(key);
        Map<String, Object> map = new HashMap<>();
        keys.forEach(k -> {
            map.put(k, hashOperations.get(key, k));
        });
        return map;
    }

    /**
     * 直接获取所有的hash并存入map返回
     *
     * @param key
     * @param clazz value的类型
     * @return
     */
    public static <T> Map<String, T> getHAll(String key, Class<T> clazz) {
        Set<String> keys = hashOperations.keys(key);
        Map<String, T> map = new HashMap<>();
        keys.forEach(k -> {
            map.put(k, getHValue(key, k, clazz));
        });
        return map;
    }


    /**
     * 获取redis hash值
     *
     * @param key
     * @param hKey
     * @return
     */
    public static Object getHValue(String key, String hKey) {
        return getHValue(key, hKey, String.class);
    }

    /**
     * 获取指定hash值 并转换为指定类型
     *
     * @param key
     * @param hKey
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getHValue(String key, String hKey, Class<T> clazz) {
        Object object = hashOperations.get(key, hKey);
        logger.info("get hash key:{},hKey:{},type:{},value:{}", key, hKey, clazz, object);
        if (object == null) return null;
        try {
            T t = (T) object;
            return t;
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * 存入对象
     *
     * @param key
     * @param value
     */
    public static void putObject(String key, Object value) {
        hashOperations.put(key, "obj", value);
        logger.info("cache put Object:[{}]-[{}]", key, value);
    }

    /**
     * 存入对象并指定超时时间
     *
     * @param key
     * @param value
     * @param seconds 秒
     */
    public static void putObject(String key, Object value, long seconds) {
        putObject(key, value);
        hashOperations.getOperations().expire(key, seconds, TimeUnit.SECONDS);
        logger.info("expire:【{}】", seconds);
    }

    /**
     * 获取对象
     *
     * @param key
     * @return
     */
    public static Object getObject(String key) {
        Object obj = hashOperations.get(key, "obj");
        logger.info("get obj:[{}] -- [{}]", key, obj);
        return obj;
    }

    /**
     * 获取对象并转换成指定类型
     *
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getObject(String key, Class<T> clazz) {
        Object object = getObject(key);
        if (object == null) return null;
        try {
            T t = (T) object;
            return t;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 删除指定key
     *
     * @param key
     */
    public static void deleteObject(String key) {
        stringRedisTemplate.delete(key);
        logger.info("del hash:[{}]", key);
    }

    /**
     * 获取hash类型元素的个数
     *
     * @param key
     * @return
     */
    public static long getHSize(String key) {
        Long size = hashOperations.size(key);
        return size;
    }


    /**
     * 删除指定的Map vlaue
     *
     * @param key
     * @param hkey
     */
    public static void deleteHObject(String key, String hkey) {
        hashOperations.delete(key, hkey);
        logger.info("del hash obj:[{}]-[{}]", key, hkey);
    }


    /**
     * 左入队
     *
     * @param key
     * @param value
     */
    public static void lPush(String key, Object value) {
        listOperations.leftPush(key, value);
        logger.info("lpush obj:[{}] -- [{}]", key, value);
    }

    public static void lPush(String key, Object value, int expireSeconds) {
        lPush(key, value);
        listOperations.getOperations().expire(key, expireSeconds, TimeUnit.SECONDS);
        logger.info("expire:[{}]", expireSeconds);
    }

    /**
     * 右入队
     *
     * @param key
     * @param value
     */
    public static void rPush(String key, Object value) {
        listOperations.rightPush(key, value);
        logger.info("rpush obj:[{}] -- [{}]", key, value);
    }

    public static void rPush(String key, Object value, int expireSeconds) {
        rPush(key, value);
        listOperations.getOperations().expire(key, expireSeconds, TimeUnit.SECONDS);
        logger.info("expire:[{}]", expireSeconds);
    }

    public static long lLen(String key) {
        return listOperations.size(key);
    }

    /**
     * 右弹出
     *
     * @param key
     * @param clazz
     * @param <T>   转换类型
     * @return
     */
    public static <T> T rPop(String key, Class<T> clazz) {
        Object object = listOperations.rightPop(key);
        logger.info("rPop:[{}]", key);
        try {
            T t = (T) object;
            return t;
        } catch (Exception e) {
            return null;
        }
    }

    public static String rPop(String key) {
        Object object = listOperations.rightPop(key);
        logger.info("rPop:[{}]", key);
        return object.toString();
    }

    /**
     * 由阻塞弹出
     *
     * @param key
     * @param seconds
     * @param clazz
     * @param <T>     类型
     * @return
     */
    public static <T> T bRPop(String key, int seconds, Class<T> clazz) {
        Object o = listOperations.rightPop(key, seconds, TimeUnit.SECONDS);
        logger.info("brPop:[{}]", key);
        if (o == null)
            return null;
        else
            try {
                T t = (T) o;
                return t;
            } catch (Exception e) {
                return null;
            }
    }

    /**
     * 发布
     *
     * @param channel 发布消息的频道
     * @param value
     */
    public static void publish(String channel, Object value) {
        hashOperations.getOperations().convertAndSend(channel, value);
    }

    /**
     * 订阅
     *
     * @param listener
     * @param channels
     */
    public static void subscribe(MessageListener listener, String... channels) {
        if (listener == null || channels == null || channels.length <= 0) {
            throw new IllegalArgumentException("please check params listener||channels");
        }
        objectRedisTemplate.execute((RedisCallback<Object>) redisConnection -> {
//            byte[][] keys = new byte[channels.length][];
//            for (int i=0;i<channels.length;i++) {
//                keys[i] = objectRedisTemplate.getStringSerializer().serialize(channels[i]);
//            }
            String channel = channels[0];
            redisConnection.subscribe(listener, objectRedisTemplate.getStringSerializer().serialize(channel));
            return null;
        });
    }


}
