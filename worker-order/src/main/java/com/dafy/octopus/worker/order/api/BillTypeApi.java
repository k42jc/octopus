package com.dafy.octopus.worker.order.api;

import com.dafy.common.po.Response;
import com.dafy.octopus.web.core.domain.Request;
import com.dafy.octopus.worker.order.service.IBillTypeService;
import com.dafy.octopus.worker.order.service.IBizDetailTypeService;
import com.dafy.octopus.worker.order.service.IBizTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 工单分类api
 * Created by liaoxudong
 * Date:2018/6/5
 */

@RestController
@RequestMapping("/billType")
@Api(description = "工单类型、业务分类、业务细分设置")
public class BillTypeApi {
    @Autowired
    private IBillTypeService billTypeService;

    @Autowired
    private IBizTypeService bizTypeService;

    @Autowired
    private IBizDetailTypeService bizDetailTypeService;

    // 工单分类列表
//    @RequiresPermissions("ClassifySet:list")
    @GetMapping("/listAll")
    @ApiOperation("工单分类列表：过滤不显示项")
    public Response listAll() {
        return billTypeService.list(true);
    }
    // 获取某分类下的业务分类列表
//    @RequiresPermissions("ClassifySet:list")
    @GetMapping("/listBizAll/{typeId}")
    @ApiOperation("根据工单分类获取业务分类列表：过滤不显示项")
    public Response listBizAll(@PathVariable("typeId") Long typeId) {
        return bizTypeService.listByBillType(typeId,true);
    }
    // 获取某业务分类下的业务详情列表
//    @RequiresPermissions("ClassifySet:list")
    @GetMapping("/listBizDetailAll/{bizId}")
    @ApiOperation("根据业务分类获取业务细分列表：过滤不显示项")
    public Response listBizDetailAll(@PathVariable("bizId") Long bizId) {
        return bizDetailTypeService.listByBizType(bizId,true);
    }

    // 工单分类列表
//    @RequiresPermissions("ClassifySet:list")
    @GetMapping("/list")
    @ApiOperation("工单分类列表：过滤不显示项")
    public Response list() {
        return billTypeService.list(false);
    }
    // 获取某分类下的业务分类列表
//    @RequiresPermissions("ClassifySet:list")
    @GetMapping("/listBiz/{typeId}")
    @ApiOperation("根据工单分类获取业务分类列表：过滤不显示项")
    public Response listBiz(@PathVariable("typeId") Long typeId) {
        return bizTypeService.listByBillType(typeId,false);
    }
    // 获取某业务分类下的业务详情列表
//    @RequiresPermissions("ClassifySet:list")
    @GetMapping("/listBizDetail/{bizId}")
    @ApiOperation("根据业务分类获取业务细分列表：过滤不显示项")
    public Response listBizDetail(@PathVariable("bizId") Long bizId) {
        return bizDetailTypeService.listByBizType(bizId,false);
    }

    @RequiresPermissions("BillType:add")
    @PostMapping("/add")
    @ApiOperation("添加工单分类")
    @ApiImplicitParam(name = "request",value = "{\n" +
            "\t\"typeName\":\"达分期3\",\n" +
            "\t\"order\": \"1\",\n" +
            "\t\"status\": \"1\"\n" +
            "}")
    public Response add(@RequestBody Request request){
        return billTypeService.add(request);
    }
    @RequiresPermissions("BillBiz:add")
    @PostMapping("/addBiz")
    public Response addBiz(@RequestBody Request request){
        return bizTypeService.add(request);
    }
    @RequiresPermissions("BillBizDetail:add")
    @PostMapping("/addBizDetail")
    public Response addBizDetail(@RequestBody Request request){
        return bizDetailTypeService.add(request);
    }


    @RequiresPermissions("BillType:add")
    @PutMapping("/edit")
    @ApiOperation("编辑工单分类")
    @ApiImplicitParam(name = "request",value = "{\n" +
            "\t\"id\": 1,\n" +
            "\t\"typeName\":\"达分期3\",\n" +
            "\t\"order\": \"1\",\n" +
            "\t\"status\": \"1\"\n" +
            "}")
    public Response edit(@RequestBody Request request){
        return billTypeService.edit(request);
    }

    @RequiresPermissions("BillType:add")
    @PutMapping("/editBiz")
    public Response editBiz(@RequestBody Request request){
        return bizTypeService.edit(request);
    }

    @RequiresPermissions("BillType:add")
    @PutMapping("/editBizDetail")
    public Response editBizDetail(@RequestBody Request request){
        return bizDetailTypeService.edit(request);
    }

    // FIXME 当下面有子类型时提示删除确认 or 禁止删除此类数据
    @RequiresPermissions("BillType:add")
    @DeleteMapping("/delete/{id}")
    public Response delete(@PathVariable Long id){
        return billTypeService.delete(id);
    }

    @RequiresPermissions("BillType:add")
    @DeleteMapping("/deleteBiz/{typeId}")
    public Response deleteBiz(@PathVariable("typeId") Long id){
        return bizTypeService.delete(id);
    }

    @RequiresPermissions("BillType:add")
    @DeleteMapping("/deleteBizDetail/{bizId}")
    public Response deleteBizDetail(@PathVariable("bizId") Long id){
        return bizDetailTypeService.delete(id);
    }


}
