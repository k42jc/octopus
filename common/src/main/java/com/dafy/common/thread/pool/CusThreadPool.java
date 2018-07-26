package com.dafy.common.thread.pool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

/**
 * Created by liaoxudong
 * Date:2017/11/16
 */

public class CusThreadPool extends ThreadPoolExecutor{

    public static final Logger logger = LoggerFactory.getLogger(CusThreadPool.class);
    // 指定自定义线程工厂以及拒绝策略
    private static ThreadFactory defaultThreadFactory = new NamedThreadFactory();
    private static RejectedExecutionHandler policy = new CusPolicy();
    /*private int corePoolSize;
    private int maximumPoolSize;
    private int queueSize;*/

    /**
     * newCachedThreadPool 可伸缩大小线程池
     */
    public CusThreadPool(){//
        this(0, Integer.MAX_VALUE,
                60L, TimeUnit.SECONDS,
                new SynchronousQueue<>());
    }

    /**
     * newFixedThreadPool 固定大小线程池
     * @param threads 线程数量
     */
    public CusThreadPool(int threads){
        this(threads, threads,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>());
    }

    /**
     *  newFixedThreadPool 固定大小线程池
     * @param threads 线程池数量
     * @param queueSize 阻塞队列大小
     */
    public CusThreadPool(int threads,int queueSize){
        this(threads, threads,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(queueSize));
    }


    /**
     * newFixedThreadPool 固定大小线程池
     * @param threads 核心线程数
     * @param maxThreads 最大工作线程数
     * @param queueSize 队列大小
     */
    public CusThreadPool(int threads,int maxThreads,int queueSize){
        this(threads, maxThreads,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(queueSize));
    }


    private CusThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, defaultThreadFactory, policy);
    }

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        logger.info("线程池任务执行开始，当前线程：{}",t.getName());
//        logger.info("当前执行任务线程：{}，任务线程数：{}，已完成任务线程数：{}",r.toString(),getTaskCount(),getCompletedTaskCount());
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
//        String info = (t == null?"任务执行成功，task:{},");
        if (t == null) {
            logger.info("任务执行完成");
        }else{
            logger.error("任务执行失败，异常信息：",t);
        }
    }

    @Override
    protected void terminated() {
        logger.warn("任务终止");
    }
}
