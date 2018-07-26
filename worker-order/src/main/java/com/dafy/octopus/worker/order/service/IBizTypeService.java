package com.dafy.octopus.worker.order.service;

import com.dafy.common.po.Response;
import com.dafy.octopus.web.core.domain.Request;

/**
 * 业务分类编辑
 * Created by liaoxudong
 * Date:2018/6/5
 */

public interface IBizTypeService {

    /**
     * 根据工单分类id获取业务分类
     * @param typeId 工单分类id
     * @return
     */
    Response listByBillType(Long typeId,boolean isAll);

    Response add(Request request);

    Response edit(Request request);

    Response delete(Long request);
}
