package com.dafy.common.task;

/**
 * 任务接口
 * Created by liaoxudong
 * Date:2017/11/17
 */

public interface ITask<T> {

    /**
     * 执行
     * @param body 任务对象
     */
    void execute(T body, boolean isSync);
}
