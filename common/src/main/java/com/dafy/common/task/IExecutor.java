package com.dafy.common.task;


import com.dafy.common.exception.NotifyExcetpion;

/**
 * Created by liaoxudong
 * Date:2017/11/17
 */

public interface IExecutor<T> {

    /**
     * 执行方法
     * @param body
     * @throws NotifyExcetpion
     */
    void execute(T body) throws NotifyExcetpion;


    /**
     * 队列任务丢弃策略
     * @param body
     * @param e
     */
    void abortPolicy(T body, Throwable e);
}
