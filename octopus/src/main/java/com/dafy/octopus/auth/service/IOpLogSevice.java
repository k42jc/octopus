package com.dafy.octopus.auth.service;

import com.dafy.common.po.Response;
import com.dafy.octopus.web.core.domain.Request;
import com.dafy.octopus.web.core.dto.OpLog;

/**
 * Created by liaoxudong
 * Date:2018/4/26
 */

public interface IOpLogSevice {

    /**
     * 保存操作信息
     * @param request
     * @return
     */
    Response save(Request request);

    /**
     * 显示操作日志列表
     * @param request
     * @return
     */
    Response list(int currentPage, int pageSize,Request request);


    /**
     * 增加系统操作日志
     * @param opLog
     */
    void addLog(OpLog opLog);
}
