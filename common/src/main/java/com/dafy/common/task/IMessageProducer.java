package com.dafy.common.task;

/**
 * 消息队列
 * Created by liaoxudong
 * Date:2017/12/21
 */

public interface IMessageProducer<T> {

    /**
     * 任务入队
     * @param body
     */
    void push(T body);
}
