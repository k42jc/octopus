package com.dafy.common.thread.pool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 工作线程异常处理策略
 * Created by liaoxudong
 * Date:2017/11/16
 */

public class CusPolicy implements RejectedExecutionHandler {
    public static final Logger logger = LoggerFactory.getLogger(CusPolicy.class);
    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        logger.error("线程池：[],工作线程：[{}] 处理异常",executor,r);
        exceptionHandler(r);
    }

    // 处理策略，子类实现
    protected void exceptionHandler(Runnable r) {}
}
