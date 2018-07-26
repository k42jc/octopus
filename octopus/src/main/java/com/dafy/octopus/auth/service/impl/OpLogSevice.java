package com.dafy.octopus.auth.service.impl;

import com.dafy.cache.factory.CacheFactory;
import com.dafy.common.po.Response;
import com.dafy.dal.page.po.Page;
import com.dafy.octopus.auth.mapper.OpLogMapper;
import com.dafy.octopus.auth.service.IOpLogSevice;
import com.dafy.octopus.web.core.utils.CommonUtils;
import com.dafy.octopus.web.core.domain.Request;
import com.dafy.octopus.web.core.dto.OpLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 系统操作日志存储
 * Created by liaoxudong
 * Date:2018/4/26
 */

@Service
public class OpLogSevice implements IOpLogSevice {
    private static final String OP_LOG_NO = "opLog:no";

    @Autowired
    private OpLogMapper opLogMapper;

    @Override
    public Response save(Request request) {
        OpLog opLog = new OpLog();
        opLogMapper.insert(opLog);
        return CommonUtils.buildSuccessResp();
    }

    @Override
    public Response list(int currentPage, int pageSize,Request request) {
        /*String condition = request.getString("condition");
        String startTime = request.getString("startTime");
        String endTime = request.getString("endTime");*/
        Page page = new Page<>(currentPage, pageSize);
//        Map<String, Object> map = new HashMap<>();
//        map.put("page", page);
//        map.put("request", request);
        page.setParams(request);
        opLogMapper.selectList(page);
//        Page<OpLog> opLogs = (Page) map.get("page");
        return CommonUtils.buildSuccessResp(page);
    }


    @Override
    @Transactional
    public void addLog(OpLog opLog) {
        opLog.setOpNo(String.valueOf(CacheFactory.incr(OP_LOG_NO)));
        opLogMapper.insertSelective(opLog);
    }
}
