package com.dafy.common.thread.pool;

import com.dafy.common.util.ConfigUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 自定义方便命名的线程工厂
 * Created by liaoxudong
 * Date:2017/11/17
 */

public class NamedThreadFactory implements ThreadFactory {
    // 线程池内线程名默认前置key
    private static final String DEFAULT_THREAD_NAME_HOLDER = ConfigUtils.get("config.threadPool.namePrefix", "dafy-");

    private static final Logger logger = LoggerFactory.getLogger(NamedThreadFactory.class);
    private static final AtomicInteger poolNumber = new AtomicInteger(1);
    private final ThreadGroup group;
    private final AtomicInteger threadNumber = new AtomicInteger(1);
    private final String namePrefix;

    NamedThreadFactory() {
        SecurityManager s = System.getSecurityManager();
        group = (s != null) ? s.getThreadGroup() :
                Thread.currentThread().getThreadGroup();
        namePrefix = DEFAULT_THREAD_NAME_HOLDER+ "pool-" +
                poolNumber.getAndIncrement() +
                "-t-";
    }

    public Thread newThread(Runnable r) {
        Thread t = new Thread(group, r,
                namePrefix + threadNumber.getAndIncrement(),
                0);
        if (t.isDaemon())
            t.setDaemon(false);
        if (t.getPriority() != Thread.NORM_PRIORITY)
            t.setPriority(Thread.NORM_PRIORITY);
        logger.debug("创建线程：[{}]",t);
        return t;
    }
}
