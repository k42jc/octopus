package com.dafy.octopus.worker.order.service;

import com.dafy.common.po.Response;
import com.dafy.octopus.web.core.domain.Request;
import com.dafy.octopus.worker.order.dto.SysConfig;

import java.util.List;

/**
 * Created by liaoxudong
 * Date:2018/6/4
 */

public interface ISysConfigService {
    String BILL_STATUS = "bill-status";
    String BILL_SOURCE = "bill-source";
    String URGENT_LEVEL = "urgent-level";
    String PROBLEM_PROP = "problem-prop";
    String APPR_RESULT = "approval-result";
    String DEAL_TYPE = "bill-deal-type";

    /**
     * 将系统配置加载到redis缓存
     */
    void init();

    /**
     * 根据配置类型获取配置项
     * @param type
     * @param filter 是否过滤禁用项
     * @return
     */
    List<SysConfig> getConfigsByType(String type,boolean filter);

    /**
     * 获取工单来源
     * @return
     */
    List<SysConfig> getBillSource();

    /**
     * 获取操作内容配置
     * @return
     */
    List<SysConfig> getBillDealType();

    /**
     * 根据id获取配置
     * @param type 配置类型
     * @param id 主键
     * @return
     */
    SysConfig getSysConfigById(String type,Long id);


    Response update(Request request);

    Response add(Request request);

    /**
     * 根据父级配置获取子项配置
     * @param pid 父级id
     * @param filter 是否过滤禁用项
     * @return
     */
    List<SysConfig> getChildrenConfigByPid(Long pid,boolean filter);
}
