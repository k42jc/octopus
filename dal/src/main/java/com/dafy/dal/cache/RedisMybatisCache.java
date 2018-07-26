package com.dafy.dal.cache;

import com.dafy.cache.factory.CacheFactory;
import com.dafy.common.util.StringUtils;
import org.apache.ibatis.cache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 使用redis作为mybatis的二级缓存策略<br>
 * 相较于默认的会话级别的缓存，提供mapper查询级别的长期有效缓存
 * 使用：<br>
 *      默认mybatis开启二级缓存，如果没有在mybatis-config.xml或spring-boot应用中关闭则默认开启
 * 两种方式<br>
 *      1.使用配置方式：*mapper.xml方式使用<cache type="com.dafy.yihui.common.db.cache.RedisMybatisCache"/>即可<br>
 *      2.使用注解方式：在指定mapper借口添加@CacheNamespace(implementation = RedisMybatisCache.class)即可<br>
 * 不适用与系统分页(会导致获取不到数据总条数)、跨多表且关联表数据时常更新(其它关联表数据修改导致结果集变更但未触发缓存失效)
 * <br/>
 * 分页在插件中额外增加了缓存<br>
 * @see com.dafy.dal.mybatis.plugin.PageInterceptor
 * Created by liaoxudong
 * Date:2018/1/14
 */

public class RedisMybatisCache implements Cache{
    private static final Logger logger = LoggerFactory.getLogger(RedisMybatisCache.class);
    private static final String cacheKeyPrefix = "mybatis:cache:";
    private String cacheKey;
    private final ReadWriteLock lock = new ReentrantReadWriteLock(true);

    public RedisMybatisCache(String id) {
        if (StringUtils.isEmpty(id)) {
            id = "empty";
        }
        this.cacheKey = cacheKeyPrefix + id;
    }

    @Override
    public String getId() {
        return this.cacheKey;
    }

    @Override
    public void putObject(Object key, Object value) {
        logger.info("cache mybatis query result,key:{},hKey:{}",cacheKey,key);
        CacheFactory.putHKey(cacheKey,key.toString(),value);
    }

    @Override
    public Object getObject(Object key) {
        logger.info("use cache result for this query,key:{},hKey:{}",cacheKey,key);
        return CacheFactory.getHValue(cacheKey,key.toString());
    }

    @Override
    public Object removeObject(Object key) {
        Object value = getObject(key);
        CacheFactory.deleteHObject(cacheKey,key.toString());
        logger.warn("del mybatis cache,key:{,hKey:{}",cacheKey,key);
        return value;
    }

    @Override
    public void clear() {
        logger.warn("release all mybatis mapper cache,key:{}",cacheKey);
        CacheFactory.deleteString(cacheKey);
    }

    @Override
    public int getSize() {
        return (int) CacheFactory.getHSize(cacheKey);
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return this.lock;
    }
}
