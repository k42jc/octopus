package com.dafy.octopus.auth.api;

import com.dafy.common.po.Response;
import com.dafy.octopus.auth.service.IPermissionService;
import com.dafy.octopus.web.core.domain.Request;
import com.dafy.octopus.web.core.utils.CommonUtils;
import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 权限管理
 * Created by liaoxudong
 * Date:2018/2/2
 */

@RestController
@RequestMapping("/permission")
@Api(description = "权限管理")
public class PermissionApi {
    @Autowired
    private IPermissionService permissionService;

//    @RequiresPermissions("permission:list")
    @GetMapping("/list")
    public Response list(@RequestHeader(required = false) Map request){
        Response list = permissionService.list(request);
        return list;
    }

    @RequiresPermissions("permission:add")
    @PostMapping("/add")
//    @OpLogRecord(OpLogType.ADD_ROLE)
    public Response add(@RequestBody Request request) {
        CommonUtils.assertHasParams(request,"pCode,pName");
        return permissionService.add(request);

    }
}
