package com.dafy.octopus.rental.api.systemConfig;

import com.dafy.common.po.Response;
import com.dafy.octopus.rental.dto.VO.systemConfig.SourceTypeVO;
import com.dafy.octopus.rental.dto.DO.systemConfig.SourceTypeDO;
import com.dafy.octopus.rental.service.systemConfig.SourceTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@Api(tags={"sourceType"})
public class SourceTypeController {
    protected  final Logger logger = LoggerFactory.getLogger(SourceTypeController.class);
    @Autowired
    private SourceTypeService sourceTypeService;

    @PostMapping("/sourceType/addSourceType")
    @RequiresPermissions(value = {"CommunicationSummerySetting:list","CustomInfo:list"}, logical = Logical.OR)
    public
    @ResponseBody
    Response addSourceType(@ApiParam(value="来源类型名称") @RequestParam(required=true,value = "name") String name){
        try {
            Response<Integer> response = new Response<Integer>();
            response.setData(sourceTypeService.addSourceType(name));
            return response;
        } catch (Exception e) {
            logger.error("添加来源类型错误",e);
            return new Response("110", "系统异常，请联系管理员");
        }
    }

    @PostMapping("/sourceType/updateSourceType")
    @RequiresPermissions(value = {"CommunicationSummerySetting:list","CustomInfo:list"}, logical = Logical.OR)
    public
    @ResponseBody
    Response updateSourceType(@ApiParam(value="来源类型名称") @RequestParam(required=true,value = "name") String name,
                                      @ApiParam(value="id") @RequestParam(required=true,value = "id") Integer id){
        try {
            sourceTypeService.updateSourceType(new SourceTypeDO(name, id));
            return new Response();
        } catch (Exception e) {
            logger.error("更新来源类型错误",e);
            return new Response("110", "系统异常，请联系管理员");
        }
    }

    @PostMapping("/sourceType/deleteSourceType")
    @RequiresPermissions(value = {"CommunicationSummerySetting:list","CustomInfo:list"}, logical = Logical.OR)
    public
    @ResponseBody
    Response deleteSourceType(@ApiParam(value="id") @RequestParam(required=true,value = "id") Integer id){
        try {
            sourceTypeService.deleteSourceType(id);
            return new Response();
        } catch (Exception e) {
            logger.error("删除来源类型错误",e);
            return new Response("110", "系统异常，请联系管理员");
        }
    }

    @GetMapping("/sourceType/getAll")
    @RequiresPermissions(value = {"CommunicationSummerySetting:list","CustomInfo:list","CustomerList:list"}, logical = Logical.OR)
    public
    @ResponseBody
    Response getAll(){
        try {
            Response<List<SourceTypeVO>> response = new Response<List<SourceTypeVO>>();
            response.setData(sourceTypeService.getAll());
            return response;
        } catch (Exception e) {
            logger.error("获取来源类型错误",e);
            return new Response("110", "系统异常，请联系管理员");
        }
    }
}
