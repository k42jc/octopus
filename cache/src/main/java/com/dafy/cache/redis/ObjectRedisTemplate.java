package com.dafy.cache.redis;

import org.springframework.data.redis.connection.DefaultStringRedisConnection;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * 让hashOperations.put(key,Object)可用
 * Created by liaoxudong
 * Date:2017/11/9
 */

public class ObjectRedisTemplate extends RedisTemplate<String,Object>{

    public ObjectRedisTemplate() {
        RedisSerializer<String> stringSerializer = new StringRedisSerializer();
        this.setKeySerializer(stringSerializer);
        this.setValueSerializer(getDefaultSerializer());
        this.setHashKeySerializer(stringSerializer);
        this.setHashValueSerializer(getDefaultSerializer());
    }

    public ObjectRedisTemplate(RedisConnectionFactory connectionFactory) {
        this();
        this.setConnectionFactory(connectionFactory);
        this.afterPropertiesSet();
    }

    protected RedisConnection preProcessConnection(RedisConnection connection, boolean existingConnection) {
        return new DefaultStringRedisConnection(connection);
    }
}
