package com.dafy.octopus.worker.order.service.impl;

import com.dafy.common.thread.pool.ThreadPoolFactory;
import com.dafy.octopus.worker.order.dto.BillOpLog;
import com.dafy.octopus.worker.order.mapper.BillOpLogMapper;
import com.dafy.octopus.worker.order.service.IBillOpLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by liaoxudong
 * Date:2018/6/4
 */

@Service
public class BillOpLogService implements IBillOpLogService {
    private static final Logger logger = LoggerFactory.getLogger(BillOpLogService.class);

    @Autowired
    private BillOpLogMapper billOpLogMapper;

    @Override
    public void addOpLog(BillOpLog billOpLog) {
        ThreadPoolFactory.submit(() -> {
            logger.info("添加工单操作信息：{}", billOpLog);
            billOpLogMapper.insertSelective(billOpLog);
        });
    }


}
