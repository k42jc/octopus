package com.dafy.cache.redis.queue;

import com.dafy.cache.factory.CacheFactory;
import com.dafy.common.exception.NotifyExcetpion;
import com.dafy.common.task.IExecutor;
import com.dafy.common.task.IMessageProducer;
import com.dafy.common.thread.pool.ThreadPoolFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 任务队列
 * 生产者-消费者
 * 去掉定时器，改为线程池
 * Created by liaoxudong
 * Date:2017/12/16
 */

public class TaskProducer<T> implements IMessageProducer<T> {
    private static final Logger logger = LoggerFactory.getLogger(TaskProducer.class);
    private IExecutor<T> executor; // 自行实现业务的执行器
    private String queueKey; // 用于队列的redis list key
    private final static AtomicInteger serialize = new AtomicInteger(0);
//    private Timer timer; // 任务消费者，用于重复执行的定时器 暂时创建一个定时器即可
//    private static final int DELAY = 1000;// 执行延时 30s
//    private static final int EXE_PRIOD = 1000; // 执行间隔 30s
    private String consumerName;

    public TaskProducer(String queueKey, IExecutor<T> executor) {
        this.queueKey = queueKey;
        this.executor = executor;
        this.consumerName = queueKey+"-consumer-"+serialize.getAndIncrement();
//        this.timer  = new Timer(timerName);
//        this.timer.schedule(new TaskConsumer(timerName),DELAY,EXE_PRIOD);
        ThreadPoolFactory.submit(new TaskConsumer(consumerName));
    }

    /*public TaskExecutor(IExecutor<T> executor, int retryTimes) {
        this.executor = executor;
        this.retryTimes = retryTimes;
    }*/

    @Override
    public void push(T body) {
        // 直接放入队列
        logger.debug(consumerName + "入队");
        CacheFactory.lPush(queueKey,body);
    }

    class TaskConsumer implements Runnable {
        private String timer;

        public TaskConsumer(String timer) {
            this.timer = timer;
        }

        @Override
        public void run() {
            logger.debug(timer+" is running");
            while(true){
                Object result = CacheFactory.bRPop(queueKey, CacheFactory.DEFAULT_CACHE_EXPIRE_TIME, Object.class);
                logger.debug(timer+ " 出队");
                if(result != null){
                    try {
                        executor.execute((T) result);
                    } catch (NotifyExcetpion e) {// 只有当前任务执行出现异常才执行重试策略
                        logger.warn("任务重试，重新入队");
                        CacheFactory.lPush(queueKey, (T) result);
                    } catch (Exception e) {// 其它异常执行自定义处理策略
                        logger.error("执行丢弃策略",e);
                        executor.abortPolicy((T) result,e);
                    }
                }
            }
        }
    }


    /**
     * 任务消费者
     *//*
    class TaskConsumer extends TimerTask {
        private String timer;

        public TaskConsumer(String timer) {
            this.timer = timer;
        }

        @Override
        public void run() {
            logger.debug(timer+" 具体任务消费者 is running");
            Object result = CacheFactory.bRPop(queueKey, Constants.DEFAULT_REDIS_EXPIRE_TIME, Object.class);
            logger.debug("任务出队");
            if(result != null){
                try {
                    executor.execute((T) result);
                } catch (NotifyExcetpion e) {// 只有当前任务执行出现异常才执行重试策略
                    logger.warn("任务重试，重新入队");
                    CacheFactory.lPush(queueKey, (T) result);
                } catch (Exception e) {// 其它异常执行自定义处理策略
                    logger.error("执行丢弃策略",e);
                    executor.abortPolicy((T) result,e);
                }
            }
        }
    }*/
}
