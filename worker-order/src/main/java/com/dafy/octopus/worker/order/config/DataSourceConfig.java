package com.dafy.octopus.worker.order.config;

import com.dafy.dal.mybatis.plugin.PageInterceptor;
import com.dafy.dal.mybatis.plugin.SqlCostInterceptor;
import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 当前系统单数据源即可
 * 为什么要改呢？因为20180507出现从库实时修改后读取数据不同步的情况
 * Created by liaoxudong
 * Date:2018/5/7
 */
@Configuration
@Order(1)
@EnableTransactionManagement
public class DataSourceConfig extends MybatisAutoConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(DataSourceConfig.class);

    @Value("${mybatis.mapper-locations}")
    private String mapperLocations;

    @Autowired
    private DataSource dataSource;

    public DataSourceConfig(MybatisProperties properties, ObjectProvider<Interceptor[]> interceptorsProvider, ResourceLoader resourceLoader, ObjectProvider<DatabaseIdProvider> databaseIdProvider, ObjectProvider<List<ConfigurationCustomizer>> configurationCustomizersProvider) {
        super(properties, interceptorsProvider, resourceLoader, databaseIdProvider, configurationCustomizersProvider);
    }

    /**
     * @return sqlSessionFactory
     * @throws Exception
     */
    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactoryBean() throws Exception {
        logger.debug("Created sqlSessionFactory");
        // 替换掉mybatis-config.xml的配置加载插件，动态数据源插件/分页插件(默认mysql分页)/sql执行时间统计插件
        /*List<Interceptor> interceptors = new ArrayList<>();
        interceptors.add(new PageInterceptor());
        if (logger.isDebugEnabled()) {// debug状态下才加载查询耗时插件
            interceptors.add(new SqlCostInterceptor());
        }*/
        Interceptor[] interceptors = {new PageInterceptor(),new SqlCostInterceptor()};
        logger.debug("已加载Mybatis拦截器数量：{}--[{}]",interceptors.length,interceptors);
        SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
        factory.setDataSource(dataSource);
        factory.setVfs(SpringBootVFS.class);
        factory.setPlugins(/*(Interceptor[]) */interceptors/*.toArray()*/);
        factory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(mapperLocations));
        return factory.getObject();
    }

}
