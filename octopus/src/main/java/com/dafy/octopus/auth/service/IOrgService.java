package com.dafy.octopus.auth.service;

import com.dafy.common.po.Response;
import com.dafy.octopus.web.core.domain.Request;

/**
 * Created by liaoxudong
 * Date:2018/4/23
 */

public interface IOrgService {

    Response list(int pagenum,int pageSize);

    Response add(Request request);

    Response delete(long orgId);

    Response edit(Request request);
}
