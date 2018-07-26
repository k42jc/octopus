package com.dafy.octopus.auth;

import com.dafy.cache.factory.CacheFactory;
import com.dafy.cache.redis.ObjectRedisTemplate;
import com.dafy.common.thread.pool.CusThreadPool;
import com.dafy.common.thread.pool.ThreadPoolFactory;
import com.dafy.dal.page.po.Page;
import com.dafy.octopus.auth.mapper.OrgMapper;
import com.dafy.octopus.auth.mapper.UserMapper;
import com.dafy.octopus.web.core.context.ApplicationContextProvider;
import com.dafy.octopus.web.core.domain.Constants;
import com.dafy.octopus.web.core.dto.Organization;
import com.dafy.octopus.web.core.dto.User;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by liaoxudong
 * Date:2018/1/26
 */

@SpringBootApplication(scanBasePackages = {"com.dafy.octopus"})
@ServletComponentScan
//启注解事务管理
@EnableTransactionManagement
@MapperScan("com.dafy.octopus.auth.mapper")
//@ImportResource({"classpath:dubbo-provider.xml"})
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
//        System.setProperty("sun.misc.ProxyGenerator.saveGeneratedFiles","true");
        SpringApplication.run(App.class, args);

        logger.debug("服务器启动完成，端口：{}", ApplicationContextProvider.getBean(Environment.class).getProperty("server.port"));
//        new SpringApplication(App.class).run(args);

        loadDataToCache();

        // JVM关闭时清除缓存数据
        Runtime.getRuntime().addShutdownHook(new Thread(App::clearBaseDataCache));
    }

    private static void clearBaseDataCache() {
        logger.info("系统关闭，清除缓存");
        CacheFactory.deleteObject(Constants.OCTOPUS_ALL_USER_KEY);
        CacheFactory.deleteObject(Constants.OCTOPUS_ALL_ORG_KEY);
    }

    /**
     * 容器启动完成后加载关键数据到中央缓存
     *
     * 主要使用用户信息与部门信息
     */
    private static void loadDataToCache() {
        OrgMapper orgMapper = ApplicationContextProvider.getBean(OrgMapper.class);
        List<Organization> orgs = orgMapper.findOrgs(new Page<>(1,10000));
        UserMapper userMapper = ApplicationContextProvider.getBean(UserMapper.class);
        List<User> users = userMapper.findUserInfos(new Page<>(1,10000));
        logger.info("初始化用户&部门数据到中央缓存");
        // 过滤掉未启用的用户
        CacheFactory.putObject(Constants.OCTOPUS_ALL_USER_KEY, users.parallelStream().filter(user -> user.getStatus() == 1).collect(Collectors.toList()));
        CacheFactory.putObject(Constants.OCTOPUS_ALL_ORG_KEY, orgs);
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
//    @Autowired
//    private RedisConnectionFactory redisConnectionFactory;

    /**
     * ObjectRedisTemplate用于Hash list等操作
     *
     * @return
     */
    @Bean
    @DependsOn("redisConnectionFactory")
    public ObjectRedisTemplate objectRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
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
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        StringRedisTemplate redisTemplate = new StringRedisTemplate();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        logger.info("Use StringRedisTemplate init redis：【{}】", redisTemplate);
        CacheFactory.setStringRedisTemplate(redisTemplate);
        return redisTemplate;
    }

}
