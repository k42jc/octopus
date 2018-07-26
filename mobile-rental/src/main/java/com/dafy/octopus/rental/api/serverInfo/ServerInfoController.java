package com.dafy.octopus.rental.api.serverInfo;

import com.dafy.common.po.Response;
import com.dafy.octopus.rental.service.serverInfo.ServerInfoService;
import com.dafy.octopus.rental.dto.DO.serverInfo.ServerInfoDO;
import com.dafy.octopus.rental.dto.VO.serverInfo.ServerInfoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Api(tags={"serverInfo"})
public class ServerInfoController {
    protected  final Logger logger = LoggerFactory.getLogger(ServerInfoController.class);

    @Autowired
    private ServerInfoService serverInfoService;

    @PostMapping("/serverInfo/addServerInfo")
    @RequiresPermissions(value = {"CustomInfo:commit"} , logical = Logical.OR)
    @ApiOperation(value="添加客服记录",notes="添加客服记录")
    public
    @ResponseBody
    Response addDealStatus(@ModelAttribute ServerInfoDO serverInfoDO){
        try {
            Response<Long> response = new Response<Long>();
            response.setData(serverInfoService.addServerInfo(serverInfoDO));
            return response;
        } catch (Exception e) {
            logger.error("添加客服记录错误",e);
            return new Response("110", "系统异常，请联系管理员");
        }
    }

    @GetMapping("/serverInfo/getServerInfoList")
    @RequiresPermissions(value = {"CustomerList:list","CustomInfo:list"} , logical = Logical.OR)
    @ApiOperation(value="获取客户记录列表",notes="获取客户记录列表")
    public
    @ResponseBody
    Response getServerInfoList(@ApiParam(value="用户userId") @RequestParam(required=true,value = "userId") String userId){
        try {
            Response<List<ServerInfoVO>> response = new Response<List<ServerInfoVO>>();
            response.setData(serverInfoService.getServerInfoList(userId));
            return response;
        } catch (Exception e) {
            logger.error("获取客户记录列表错误",e);
            return new Response("110", "系统异常，请联系管理员");
        }
    }

    @GetMapping("/serverInfo/getServerInfoDetail")
    @RequiresPermissions(value = {"CustomerList:list","CustomInfo:list"} , logical = Logical.OR)
    @ApiOperation(value="获取客户记录详情",notes="获取客户记录详情")
    public
    @ResponseBody
    Response getServerInfoDetail(@ApiParam(value="serverInfoId") @RequestParam(required=true,value = "id") Long id){
        try {
            Response<ServerInfoVO> response = new Response<ServerInfoVO>();
            response.setData(serverInfoService.getServerInfoDetail(id));
            return response;
        } catch (Exception e) {
            logger.error("获取客户记录详情错误",e);
            return new Response("110", "系统异常，请联系管理员");
        }
    }
}
