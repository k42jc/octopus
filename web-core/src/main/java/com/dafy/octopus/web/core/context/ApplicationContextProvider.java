package com.dafy.octopus.web.core.context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 项目中获取bean
 * Created by liaoxudong
 * Date:2017/11/8
 */

@Component
public class ApplicationContextProvider implements ApplicationContextAware{
    private static final Logger logger = LoggerFactory.getLogger(ApplicationContextProvider.class);
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (ApplicationContextProvider.applicationContext == null) {
            ApplicationContextProvider.applicationContext = applicationContext;
        }
        logger.info("ApplicationContext init success!");
    }

    /**
     * 获取指定bean
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> clazz) {
        if (applicationContext == null) {
            logger.error("applicationContext isn't init!");
            return null;
        }
        return applicationContext.getBean(clazz);
    }

    public static <T> T getBean(String beanName, Class<T> clazz) {
        if (applicationContext == null) {
            logger.error("applicationContext isn't init!");
            return null;
        }
        return applicationContext.getBean(beanName, clazz);
    }

    public static ApplicationContext getApplicationContext(){
        return ApplicationContextProvider.applicationContext;
    }

}
