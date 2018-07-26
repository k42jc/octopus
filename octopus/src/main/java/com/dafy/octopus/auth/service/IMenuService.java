package com.dafy.octopus.auth.service;


import com.dafy.common.po.Response;
import com.dafy.octopus.web.core.domain.Request;
import com.dafy.octopus.web.core.dto.Menu;

import java.util.List;
import java.util.Map;

/**
 * 菜单相关
 * Created by liaoxudong
 * Date:2018/1/31
 */

public interface IMenuService {

    List<Menu> getUserMenus(Long userId);


    Response list(Map header);

    Response add(Request request);

    Response delete(Long id);

    Response update(Request request);
}
