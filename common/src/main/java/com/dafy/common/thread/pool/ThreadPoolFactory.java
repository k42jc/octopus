package com.dafy.common.thread.pool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * 线程池工厂
 * Created by liaoxudong
 * Date:2017/11/17
 */

public class ThreadPoolFactory {
    private static final Logger logger = LoggerFactory.getLogger(ThreadPoolFactory.class);

    private static ExecutorService executorService;

    // ****在初始化配置时调用此方法初始化线程池配置***
    public static void init(ExecutorService executorService){
        ThreadPoolFactory.executorService = executorService;
    }

    public static void submit(Runnable task){
        if (executorService == null) {
            throw new IllegalStateException("未初始化线程池");
        }
        Future<?> submit = executorService.submit(task);
        if (submit.isDone()) {
            logger.info("任务：{} ,执行完成",task);
        }
    }

    public static void submit(Callable task) {
        if (executorService == null) {
            throw new IllegalStateException("未初始化线程池");
        }
        Future submit = executorService.submit(task);
        if (submit.isDone()) {
            logger.info("任务：{} ,执行完成",task);
        }
    }


}
