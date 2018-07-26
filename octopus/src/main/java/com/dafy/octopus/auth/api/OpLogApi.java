package com.dafy.octopus.auth.api;

import com.dafy.common.po.Response;
import com.dafy.common.util.StringUtils;
import com.dafy.octopus.web.core.domain.Request;
import com.dafy.octopus.auth.service.IOpLogSevice;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created by liaoxudong
 * Date:2018/4/23
 */

@RestController
@RequestMapping("opLog")
@Api(description = "用户操作日志")
public class OpLogApi {

    @Autowired
    private IOpLogSevice opLogSevice;

    @PostMapping("/save")
    public Response save(@RequestBody Request request){
        return opLogSevice.save(request);
    }

    /**
     * 获取用户列表
     *
     * @return
     */
    @ApiOperation(value = "操作日志列表，需要(OpLog:list)权限")
    @ApiImplicitParams({@ApiImplicitParam(name = "header", value = "在http头部携带分页逻辑：{\n" +
            "\t\"pagenum\":\"起始页\",\n" +
            "\t\"pagesize\":\"每页显示多少条\"\n" +
            "}")
    })
    @RequiresPermissions("OpLog:list")
    @GetMapping("/list")
    public Response list(@RequestHeader Map<String,Object> header, @RequestParam(required = false) String condition,
                         @RequestParam(required = false) String startTime,
                         @RequestParam(required = false) String endTime){
        Object pagenum = header.get("pagenum");
        Object pagesize = header.get("pagesize");
        Request request = new Request();
        request.put("condition", StringUtils.isEmpty(condition)?null:condition);
        request.put("startTime", StringUtils.isEmpty(startTime)?null:startTime);
        request.put("endTime", StringUtils.isEmpty(endTime)?null:endTime);
        return opLogSevice.list(pagenum == null?1:Integer.valueOf(pagenum.toString()),
        pagesize == null?1000:Integer.valueOf(pagesize.toString()),request);
    }
}
