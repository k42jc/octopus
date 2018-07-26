package com.dafy.octopus.auth.service.impl;

import com.dafy.common.po.Response;
import com.dafy.common.util.TreeUtils;
import com.dafy.dal.page.po.Page;
import com.dafy.octopus.auth.mapper.MenuMapper;
import com.dafy.octopus.auth.service.IMenuService;
import com.dafy.octopus.web.core.utils.CommonUtils;
import com.dafy.octopus.web.core.domain.Request;
import com.dafy.octopus.web.core.dto.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by liaoxudong
 * Date:2018/1/31
 */

@Service
public class MenuService implements IMenuService {
    @Autowired
    private MenuMapper menuMapper;
    @Override
    public List<Menu> getUserMenus(Long userId) {
//        List<Menu> menus = menuMapper.getAllMenus();
        List<Menu> menus = menuMapper.getUserMenus(userId);
        //List<MenuTreeDto> menuTreeDtos = MenuTreeDto.convert(menus);
//        MenuTreeUtils menuTreeUtils = new MenuTreeUtils();
        List<Menu> menuTrees = TreeUtils.buildTree(menus);
//        List<Menu> menuTrees = menuTreeUtils.buildTree(menus);
        return menuTrees;
    }

    @Override
    public Response list(Map header) {
        int pageNum = Integer.valueOf(header.get("pagenum").toString());
        int pageRow = Integer.valueOf(header.get("pagerow").toString());
        Page<Menu> page = new Page<>(pageNum, pageRow);
//        Map<String, Object> map = new HashMap<>();
//        map.put("page", page);
        menuMapper.getAllMenus(page);
        /**
         *
         */
        List<Menu> menuList = page.getData();
        page.setData(TreeUtils.buildTree(menuList));
        return CommonUtils.buildSuccessResp(page);
    }

    @Override
    public Response add(Request request) {
        Menu menu = new Menu();
        menu.setmCode(request.getString("mCode"));
        menu.setmName(request.getString("mName"));
        menu.setmIcon(request.getString("mIcon"));
        menu.setmOrder(request.getInt("mOrder"));
        menu.setParentId(request.getLong("parentId"));
        menu.setmUrl(request.getString("mUrl"));
        menu.setDesc(request.getString("desc"));
        menuMapper.insertSelective(menu);
        return CommonUtils.buildSuccessResp();
    }

    @Override
    public Response delete(Long id) {
        return null;
    }

    @Override
    public Response update(Request request) {
        return null;
    }
}
