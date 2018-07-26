package com.dafy.common.task;

/**
 * 报警接口
 * Created by liaoxudong
 * Date:2017/11/17
 */

public interface IAlarm<T> {

    /**
     * 发送报警信息
     * @param t 内容
     * @param isSync 是否异步
     */
    void alarm(T t, boolean isSync);
}
