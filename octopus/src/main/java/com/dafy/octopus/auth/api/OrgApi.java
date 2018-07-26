package com.dafy.octopus.auth.api;

import com.dafy.common.po.Response;
import com.dafy.octopus.web.core.domain.OpLogType;
import com.dafy.octopus.auth.service.IOrgService;
import com.dafy.octopus.web.core.aop.OpLogRecord;
import com.dafy.octopus.web.core.domain.Request;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 部门相关api处理
 * Created by liaoxudong
 * Date:2018/4/23
 */

@RestController
@RequestMapping("/org")
@Api(description = "部门管理")
public class OrgApi {

    @Autowired
    private IOrgService orgService;

    @PostMapping("/add")
    @ApiOperation("增加部门")
    @ApiImplicitParam(name = "request",value = "{\"parentOrgId\":\"父级部门id，可以不指定\",\"name\":\"部门名称\",\"desc\":\"部门描述\"}")
    @RequiresPermissions("DepartmentManage:list")
    @OpLogRecord(OpLogType.ADD_ORG)
    public Response list(@RequestBody Request request){
        return orgService.add(request);
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation("删除部门")
    @RequiresPermissions("DepartmentManage:list")
    @OpLogRecord(OpLogType.DEL_ORG)
    public Response delete(@PathVariable("id") Long id){
        return orgService.delete(id);
    }

    @GetMapping("/list")
    @ApiOperation("部门列表")
    @RequiresPermissions(value={"DepartmentManage:list","UserManage:list"},logical = Logical.OR)
    public Response list(@RequestHeader Map header){
        Object pagenum = header.get("pagenum");
        Object pagesize = header.get("pagesize");
        return orgService.list(pagenum == null?1:Integer.valueOf(pagenum.toString()), pagesize == null?1000:Integer.valueOf(pagesize.toString()));
    }

    @PutMapping("/edit")
    @ApiOperation("编辑部门")
    @ApiImplicitParam(name = "request",value = "{\"id\":\"主键\",\"parentOrgId\":\"父级部门id，可以不指定，如果指定表示更换上级部门\",\"name\":\"部门名称\",\"desc\":\"部门描述\"}")
    @RequiresPermissions("DepartmentManage:list")
    @OpLogRecord(OpLogType.EDIT_ORG)
    public Response edit(@RequestBody Request request){
        return orgService.edit(request);
    }
}
