package com.dafy.octopus.auth.service;

import com.dafy.common.po.Response;
import com.dafy.octopus.web.core.dto.Role;
import com.dafy.octopus.web.core.domain.Request;

import java.util.List;
import java.util.Map;

/**
 * Created by liaoxudong
 * Date:2018/1/31
 */

public interface IRoleService {

    List<Role> getRoleByUserId(Long userId);

    List<Role> getAllRoles();

    Response list(Map header);

    /**
     * 添加角色信息
     * @param request
     * @return
     */
    Response add(Request request);

    /**
     * 删除角色
     * @param id
     * @return
     */
    Response delete(Long id);

    /**
     * 更新角色信息
     * @param request
     * @return
     */
    Response update(Request request);
}
