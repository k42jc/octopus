package com.dafy.octopus.rental.api.systemConfig;

import com.dafy.common.po.Response;
import com.dafy.octopus.rental.dto.DO.systemConfig.DealStatusDO;
import com.dafy.octopus.rental.dto.VO.systemConfig.DealStatusVO;
import com.dafy.octopus.rental.service.systemConfig.DealStatusService;
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
@Api(tags={"dealStatus"})
public class DealStatusController {
    protected  final Logger logger = LoggerFactory.getLogger(DealStatusController.class);

    @Autowired
    private DealStatusService dealStatusService;

    @PostMapping("/dealStatus/addDealStatus")
    @RequiresPermissions(value = {"CommunicationSummerySetting:list","CustomInfo:list"}, logical = Logical.OR)
    public
    @ResponseBody
    Response addDealStatus(@ApiParam(value="状态名称") @RequestParam(required=true,value = "name") String name){
        try {
            Response<Integer> response = new Response<Integer>();
            response.setData(dealStatusService.addDealStatus(name));
            return response;
        } catch (Exception e) {
            logger.error("添加处理状态错误",e);
            return new Response("110", "系统异常，请联系管理员");
        }
    }

    @PostMapping("/dealStatus/updateDealStatus")
    @RequiresPermissions(value = {"CommunicationSummerySetting:list","CustomInfo:list"}, logical = Logical.OR)
    public
    @ResponseBody
    Response updateDealStatus(@ApiParam(value="状态名称") @RequestParam(required=true,value = "name") String name,
                                     @ApiParam(value="id") @RequestParam(required=true,value = "id") Integer id){
        try {
            dealStatusService.updateDealStatus(new DealStatusDO(name, id));
            return new Response();
        } catch (Exception e) {
            logger.error("更新处理状态错误",e);
            return new Response("110", "系统异常，请联系管理员");
        }
    }

    @PostMapping("/dealStatus/deleteDealStatus")
    @RequiresPermissions(value = {"CommunicationSummerySetting:list","CustomInfo:list"}, logical = Logical.OR)
    public
    @ResponseBody
    Response deleteDealStatus(@ApiParam(value="id") @RequestParam(required=true,value = "id") Integer id){
        try {
            dealStatusService.deleteDealStatus(id);
            return new Response();
        } catch (Exception e) {
            logger.error("删除处理状态错误",e);
            return new Response("110", "系统异常，请联系管理员");
        }
    }

    @GetMapping("/dealStatus/getAll")
    @RequiresPermissions(value = {"CommunicationSummerySetting:list","CustomInfo:list","CustomerList:list"}, logical = Logical.OR)
    public
    @ResponseBody
    Response getAll(){
        try {
            Response<List<DealStatusVO>> response = new Response<List<DealStatusVO>>();
            response.setData(dealStatusService.getAll());
            return response;
        } catch (Exception e) {
            logger.error("获取处理状态错误",e);
            return new Response("110", "系统异常，请联系管理员");
        }
    }

}
