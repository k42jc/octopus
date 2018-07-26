package com.dafy.octopus.worker.order.service;

import com.dafy.common.po.Response;
import com.dafy.octopus.web.core.domain.Request;

/**
 * 业务分类编辑
 * Created by liaoxudong
 * Date:2018/6/5
 */

public interface IBizDetailTypeService {

    /**
     * 根据工单分类id获取业务分类
     * @param bizId 工单分类id
     * @return
     */
    Response listByBizType(Long bizId,boolean isAll);

    Response add(Request request);

    Response edit(Request request);

    Response delete(Long request);
}
