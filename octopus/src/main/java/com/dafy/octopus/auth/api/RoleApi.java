package com.dafy.octopus.auth.api;

import com.dafy.common.po.Response;
import com.dafy.octopus.web.core.domain.OpLogType;
import com.dafy.octopus.auth.service.IRoleService;
import com.dafy.octopus.auth.aop.DisableEditAdmin;
import com.dafy.octopus.web.core.aop.OpLogRecord;
import com.dafy.octopus.web.core.domain.Request;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created by liaoxudong
 * Date:2018/2/2
 */

@RestController
@RequestMapping("/role")
@Api(description = "角色/权限管理")
public class RoleApi {

    @Autowired
    private IRoleService roleService;

    @ApiOperation(value = "角色列表")
    @GetMapping("/list")
    @RequiresPermissions(value = {"CharManage:list","UserManage:list"},logical = Logical.OR)
    public Response list(@RequestHeader Map header) {
        return roleService.list(header);
    }


    @PostMapping("/add")
    @ApiOperation(value = "添加角色")
    @ApiImplicitParam(name = "request",value = "{\"roleName\":\"角色名\",\"desc\":\"描述信息\",\"selectedPermissions\":\"选中的权限id\"}")
    @RequiresPermissions("CharManage:list")
    @OpLogRecord(OpLogType.ADD_ROLE)
    public Response add(@RequestBody Request request) {
//        CommonUtils.assertHasParams(request,"code,roleName");
        return roleService.add(request);
    }


    @RequiresPermissions("CharManage:list")
    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "删除角色")
    @OpLogRecord(OpLogType.DEL_ROLE)
    @DisableEditAdmin(DisableEditAdmin.EditType.ROLE)// 如果角色是管理员，禁止删除
    public Response delete(@ApiParam("角色id") @PathVariable("id") Long id){
        return roleService.delete(id);
    }


    @RequiresPermissions("CharManage:list")
    @ApiOperation(value = "更新角色")
    @ApiImplicitParam(name = "request",value = "{\"id\":\"角色id\",\"roleName\":\"角色名\",\"desc\":\"描述信息\",\"selectedPermissions\":\"选中的权限id\"}")
    @PutMapping("/update")
    @OpLogRecord(OpLogType.EDIT_ROLE)
    @DisableEditAdmin(DisableEditAdmin.EditType.ROLE)// 如果角色是管理员，禁止编辑
    public Response update(@RequestBody Request request){
//        CommonUtils.assertHasParams(request,"code,roleName");
        return roleService.update(request);
    }
}
