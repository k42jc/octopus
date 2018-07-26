package com.dafy.octopus.auth.service.impl;

import com.dafy.common.po.Response;
import com.dafy.common.util.DateTimeUtils;
import com.dafy.dal.page.po.Page;
import com.dafy.octopus.web.core.dto.Role;
import com.dafy.octopus.auth.service.IRoleService;
import com.dafy.octopus.web.core.domain.Request;
import com.dafy.octopus.web.core.domain.ResponseCode;
import com.dafy.octopus.web.core.dto.RolePermission;
import com.dafy.octopus.web.core.dto.UserRole;
import com.dafy.octopus.auth.mapper.PermissionMapper;
import com.dafy.octopus.auth.mapper.RoleMapper;
import com.dafy.octopus.auth.mapper.RolePermissionMapper;
import com.dafy.octopus.auth.mapper.UserRoleMapper;
import com.dafy.octopus.web.core.shiro.session.RedisSessionDao;
import com.dafy.octopus.web.core.utils.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by liaoxudong
 * Date:2018/1/31
 */

@Service
public class RoleService implements IRoleService {
    private static final Logger logger = LoggerFactory.getLogger(RoleService.class);
    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private RolePermissionMapper rolePermissionMapper;
    @Override
    public List<Role> getRoleByUserId(Long userId) {
        return roleMapper.getRoleByUserId(userId);
    }

    @Override
    public List<Role> getAllRoles() {
        return roleMapper.getAllRoles();
    }

    @Override
    public Response list(Map header) {
        Object pagenum = header.get("pagenum");
        Object pagesize = header.get("pagesize");
        int pageNum = Integer.valueOf(pagenum == null ? 1 : Integer.parseInt(pagenum.toString()));
        int pageRow = Integer.valueOf(pagesize == null ? 1000 : Integer.parseInt(pagesize.toString()));
        Page<Role> page = new Page<>(pageNum, pageRow);
//        Map<String, Object> map = new HashMap<>();
//        map.put("page", page);
        roleMapper.findRoles(page);
//        Page<Role> rolePage = (Page<Role>) map.get("page");
        page.getData().forEach(r -> {
            r.setCreateTimeStr(DateTimeUtils.formatDatetime1(r.getCreateTime()));
            if(r.getUpdateTime() != null)
                r.setUpdateTimeStr(DateTimeUtils.formatDatetime1(r.getUpdateTime()));
            /*long[] permissions = new long[r.getPermissionList().size()];
            for(int i=0;i<r.getPermissionList().size();i++) {
                permissions[i] = r.getPermissionList().get(i).getId();
            }
            r.setPermissions(permissions);
            r.setPermissionList(null);*/
        });
//        rolePage.setTotalSize((long) rolePage.getData().size());
        /*List<Role> roleInfos = page.getData();
        for (Role roleInfo : roleInfos) {
            List<Permission> permissionList = roleInfo.getPermissionList();
            for (Permission p : permissionList) {
                roleInfo.addExistsPermisson(p.getId());
            }
            roleInfo.setPermissionList(TreeUtils.buildTree(permissionList));
        }
        Map<String,Object> result = new HashMap<>();
        result.put("roleInfo", page);
        result.put("permissionList", TreeUtils.buildTree(permissionMapper.findAll()));*/

        return CommonUtils.buildSuccessResp(page);
    }

    @Override
    public Response add(Request request) {
        Role role = new Role();
//        role.setCode(request.getString("code"));
        role.setRoleName(request.getString("roleName"));
        role.setDesc(request.getString("desc"));
//        role.setStatus(request.getBoolean("status")?"1":"0");
        roleMapper.insertSelective(role);
        List<RolePermission> rolePermissions = new ArrayList<>();
        List<Integer> pIds = request.get("selectedPermissions", List.class);
        logger.debug("选中权限：{}",pIds);
        for (Integer pId : pIds) {// 批量插入
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(role.getId());
            rolePermission.setPermissionId(Long.valueOf(pId));
            rolePermissions.add(rolePermission);
        }
        if(!rolePermissions.isEmpty())
            rolePermissionMapper.inserts(rolePermissions);
        return CommonUtils.buildSuccessResp();
    }

    @Override
    public Response delete(Long id) {
        // 先检查当前角色是否存在用户
        List<UserRole> userRoles = userRoleMapper.selectByRoleId(id);
        if (userRoles != null && !userRoles.isEmpty()) {
            CommonUtils.throwException(ResponseCode.ROLE_EXIST_USER);
        }
        // 删除角色
        roleMapper.deleteByPrimaryKey(id);
        // 删除用户角色关联
        userRoleMapper.deleteByRoleId(id);
        // 删除角色权限关联
        rolePermissionMapper.deleteByRoleId(id);
        return CommonUtils.buildSuccessResp();
    }

    @Override
    public Response update(Request request) {
        Role role = new Role();
        long id = request.getLong("id");
        role.setId(id);
//        role.setCode(request.getString("code"));
        role.setRoleName(request.getString("roleName"));
        role.setDesc(request.getString("desc"));
        role.setUpdateTime(new Date());
//        role.setStatus(request.getBoolean("status")?"1":"0");
        roleMapper.updateByPrimaryKeySelective(role);
        // 先删除角色权限关联
        rolePermissionMapper.deleteByRoleId(role.getId());
        // 再重新插入
        List<RolePermission> rolePermissions = new ArrayList<>();
        List<Integer> pIds = request.get("selectedPermissions", List.class);
        for (Integer pId : pIds) {// 批量插入
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(role.getId());
            rolePermission.setPermissionId(Long.valueOf(pId));
            rolePermissions.add(rolePermission);
        }
        if(!rolePermissions.isEmpty()) {
            rolePermissionMapper.inserts(rolePermissions);
            // 如果调整了角色与权限关联，清空绑定的用户
            List<Map<String,Object>> userRoles = userRoleMapper.selectUserInfosByRoleId(id);
            userRoles.forEach(ur -> {
                RedisSessionDao.clearRolePermissions(ur.get("user_code").toString());
            });
        }
        return CommonUtils.buildSuccessResp();
    }
}
