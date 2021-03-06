package com.dafy.octopus.rental;

import com.dafy.cache.factory.CacheFactory;
import com.dafy.cache.redis.ObjectRedisTemplate;
import com.dafy.common.thread.pool.CusThreadPool;
import com.dafy.common.thread.pool.ThreadPoolFactory;
import com.dafy.octopus.web.core.context.ApplicationContextProvider;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * Created by liaoxudong
 * Date:2018/1/26
 */

@SpringBootApplication(scanBasePackages = {"com.dafy.octopus"})
@ServletComponentScan
//启注解事务管理
//@EnableTransactionManagement
@MapperScan("com.dafy.octopus.rental.mapper")
@Configuration
//@ConfigurationProperties(prefix = "config.threadPool")
public class App {
    private static final Logger logger = LoggerFactory.getLogger(App.class);

    @Value("${config.threadPool.coreThreads}")
    private int coreThreads;
    @Value("${config.threadPool.maxThreads}")
    private int maxThreads;
    @Value("${config.threadPool.queueSize}")
    private int queueSize;

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
//        new SpringApplication(App.class).run(args);
        logger.debug("服务器启动完成，端口：{}", ApplicationContextProvider.getBean(Environment.class).getProperty("server.port"));
    }


    //初始化线程池
    @Bean
    public CusThreadPool cusThreadPool(){
        // 固定大小线程池
        CusThreadPool cusThreadPool = new CusThreadPool(coreThreads, maxThreads, queueSize);
        ThreadPoolFactory.init(cusThreadPool);
        return cusThreadPool;
    }

    //初始化redis
    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    /**
     * ObjectRedisTemplate用于Hash list等操作
     *
     * @return
     */
    @Bean
    @DependsOn("redisConnectionFactory")
    public ObjectRedisTemplate objectRedisTemplate() {
        ObjectRedisTemplate objectRedisTemplate = new ObjectRedisTemplate();
        objectRedisTemplate.setConnectionFactory(redisConnectionFactory);
        logger.info("Use ObjectRedisTemplate init redis：【{}】", objectRedisTemplate);
        CacheFactory.setObjectRedisTemplate(objectRedisTemplate);
        return objectRedisTemplate;
    }

    /**
     * 普通字符串
     * @return
     */
    @Bean("stringRedisTemplate")
    @DependsOn("redisConnectionFactory")
    public StringRedisTemplate stringRedisTemplate() {
        StringRedisTemplate redisTemplate = new StringRedisTemplate();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        logger.info("Use StringRedisTemplate init redis：【{}】", redisTemplate);
        CacheFactory.setStringRedisTemplate(redisTemplate);
        return redisTemplate;
    }
}
