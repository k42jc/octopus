package com.dafy.octopus.worker.order.api;

import com.dafy.common.po.Response;
import com.dafy.octopus.web.core.domain.Request;
import com.dafy.octopus.web.core.utils.CommonUtils;
import com.dafy.octopus.worker.order.service.ISysConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 用于获取系统配置参数
 * Created by liaoxudong
 * Date:2018/6/14
 */

@RestController
@RequestMapping("/sysConfig")
@Api(description = "获取系统配置项")
public class SysConfigApi {
    @Autowired
    private ISysConfigService sysConfigService;

//    @RequiresPermissions("Sysconfig:list")
    @GetMapping("/list/{type}")
    @ApiOperation("根据类型获取可用(显示)配置，bill-status=工单状态，bill-source=工单来源，urgent-level=紧急程度，problem-prop=问题定性，approval-result=审批结果，bill-deal-type=操作内容")
    public Response getConfigListByType(@PathVariable("type") String type){
        return CommonUtils.buildSuccessResp(sysConfigService.getConfigsByType(type,true));
    }

    @GetMapping("/listChildren")
    @ApiOperation("获取可用(显示)子配置项,pid 传入父级配置主键")
    public Response getChildrenConfigByPid(@RequestParam("pid") Long pid){
        return CommonUtils.buildSuccessResp(sysConfigService.getChildrenConfigByPid(pid,true));
    }

    @GetMapping("/listAll/{type}")
    @ApiOperation("根据类型获取所有配置，用于设置，bill-status=工单状态，bill-source=工单来源，urgent-level=紧急程度，problem-prop=问题定性，approval-result=审批结果，bill-deal-type=操作内容")
    public Response getAllConfigListByType(@PathVariable("type") String type){
        return CommonUtils.buildSuccessResp(sysConfigService.getConfigsByType(type,false));
    }

    @GetMapping("/listAllChildren")
    @ApiOperation("获取所有子配置项，用于设置,pid 传入父级配置主键")
    public Response getAllChildrenConfigByPid(@RequestParam("pid") Long pid){
        return CommonUtils.buildSuccessResp(sysConfigService.getChildrenConfigByPid(pid,false));
    }


    @RequiresPermissions("Sysconfig:add")
    @PutMapping("/update")
    @ApiOperation("根据分类修改配置")
    @ApiImplicitParam(name = "request",value = "{\n" +
            "\t\"id\":\"主键\",\n" +
            "\t\"type\":\"problem-prop\",\n" +
            "\t\"name\": \"大问题\",\n" +
            "\t\"oldOrder\": \"1\",\n" +
            "\t\"order\": \"2\",\n" +
            "\t\"status\": \"1\"\n" +
            "}")
    public Response updateConfigByType(@RequestBody Request request) {
        return sysConfigService.update(request);
    }

    @RequiresPermissions("Sysconfig:add")
    @PostMapping("/add")
    @ApiOperation("添加配置项")
    @ApiImplicitParam(name = "request",value = "{\n" +
            "\t\"type\":\"参照上面的配置项\",\n" +
            "\t\"pid\":\"父级主键\",\n" +
            "\t\"name\": \"大问题\",\n" +
            "\t\"order\": \"1\",\n" +
            "\t\"status\": \"1\"\n" +
            "}")
    public Response add(@RequestBody Request request) {
        return sysConfigService.add(request);
    }
}
