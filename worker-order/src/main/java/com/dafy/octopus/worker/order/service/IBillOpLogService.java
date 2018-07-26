package com.dafy.octopus.worker.order.service;

import com.dafy.octopus.worker.order.dto.BillOpLog;

/**
 * 工单操作日志
 * Created by liaoxudong
 * Date:2018/6/4
 */

public interface IBillOpLogService {

    /**
     * 添加工单操作日志
     */
    void addOpLog(BillOpLog billOpLog);
}
