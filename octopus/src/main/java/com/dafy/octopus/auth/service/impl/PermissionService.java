package com.dafy.octopus.auth.service.impl;

import com.dafy.common.po.Response;
import com.dafy.common.util.TreeUtils;
import com.dafy.dal.page.po.Page;
import com.dafy.octopus.web.core.domain.Request;
import com.dafy.octopus.web.core.dto.Permission;
import com.dafy.octopus.auth.service.IPermissionService;
import com.dafy.octopus.web.core.utils.CommonUtils;
import com.dafy.octopus.auth.mapper.PermissionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by liaoxudong
 * Date:2018/1/31
 */

@Service
public class PermissionService implements IPermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public List getUserPermissions(Long id) {
        List<Permission> userPermissions = permissionMapper.getUserPermissions(id);
        /*Map<String, Set<String>> resultMap = new HashMap<>();
        Set<String> pCodes = new HashSet<>();
        resultMap.put("")
        Set<String> pNames = new HashSet<>();
        for (Permission p : userPermissions) {
            pCodes.add(p.getpCode());
            pNames.add(p.getpName());
        }*/
//        List<Permission> menuTrees = TreeUtils.buildTree(userPermissions);
        return userPermissions;
    }

    @Override
    public Response list(Map request) {
        Object pagenum = request.get("pagenum");
        Object pagerow = request.get("pagerow");
        int pageNum = Integer.valueOf(pagenum != null?pagenum.toString():"1");
        int pageRow = Integer.valueOf(pagerow !=null?pagerow.toString():"1000");
        Page<Permission> page = new Page<>(pageNum, pageRow);
//        Map<String, Object> map = new HashMap<>();
//        map.put("page", page);
        permissionMapper.findAllPage(page);
        List<Permission> menuList = page.getData();
        page.setData(TreeUtils.buildTree(menuList));
        return CommonUtils.buildSuccessResp(page);
    }

    @Override
    public Response add(Request request) {
        String pCode = request.getString("pCode");
        Long parentId = request.getLong("parentId");
        String pName = request.getString("pName");
        String desc = request.getString("desc");
        Permission permission = new Permission();
        permission.setpCode(pCode);
        permission.setParentId(parentId);
        permission.setpName(pName);
        permission.setDesc(desc);
        permissionMapper.insertSelective(permission);
        return CommonUtils.buildSuccessResp();
    }
}
