package com.dafy.octopus.auth.service;

import com.dafy.common.po.Response;
import com.dafy.octopus.web.core.domain.Request;
import com.dafy.octopus.web.core.dto.Permission;

import java.util.List;
import java.util.Map;

/**
 * Created by liaoxudong
 * Date:2018/1/31
 */

public interface IPermissionService {

    List<Permission> getUserPermissions(Long id);

    /**
     * 查询权限列表
     * @param request
     * @return
     */
    Response list(Map request);

    /**
     * 新增权限
     * @param request
     * @return
     */
    Response add(Request request);
}
