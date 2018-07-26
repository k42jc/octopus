package com.dafy.common.task;

import com.dafy.common.thread.pool.ThreadPoolFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 任务执行策略 提供重试机制
 * Created by liaoxudong
 * Date:2017/12/16
 */

public class TaskExecutor<T> implements ITask<T> {
    private static final Logger logger = LoggerFactory.getLogger(TaskExecutor.class);
    private IExecutor<T> executor; // 自行实现业务的执行器
    private int retryTimes = 0;// 重试次数 默认0

    public TaskExecutor(IExecutor<T> executor) {
        this.executor = executor;
    }

    public TaskExecutor(IExecutor<T> executor, int retryTimes) {
        this.executor = executor;
        this.retryTimes = retryTimes;
    }

    @Override
    public void execute(T body, boolean isSync) {
        if (!isSync) {// 如果是异步执行，则提交线程池
            ThreadPoolFactory.submit(new TaskRunnable(body,retryTimes));
        }else{
            try {
                executor.execute(body);
            } catch (Exception e) {
                retryTimes--;
                if (retryTimes > 0) {
                    logger.warn("重试");
                    execute(body,true);
                }else{
                    executor.abortPolicy(body,e);
                }
            }
        }

    }

    /**
     * 异步任务执行
     */
    class TaskRunnable implements Runnable {
        private T body;
        private int retryTimes;
        public TaskRunnable(T body,int retryTimes) {
            this.body = body;
            this.retryTimes = retryTimes;
        }

        @Override
        public void run() {
            try {
                executor.execute(body);
            } catch (Exception e) {
                retryTimes--;
                if (retryTimes > 0) {
                    logger.warn("重试");
                    run();
                }else{
                    executor.abortPolicy(body,e);
                }
            }

        }
    }

}
