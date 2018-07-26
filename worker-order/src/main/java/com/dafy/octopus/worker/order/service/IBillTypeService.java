package com.dafy.octopus.worker.order.service;

import com.dafy.common.po.Response;
import com.dafy.octopus.web.core.domain.Request;

/**
 * 工单分类设置
 * Created by liaoxudong
 * Date:2018/6/4
 */

public interface IBillTypeService {

    /**
     * 工单分类列表
     * @return
     */
    Response list(boolean all);

    /**
     * 添加
     * @param request
     * @return
     */
    Response add(Request request);

    /**
     * 编辑
     * @param request
     * @return
     */
    Response edit(Request request);

    /**
     * 删除
     * @param request
     * @return
     */
    Response delete(Long request);
}
