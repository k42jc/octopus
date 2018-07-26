package com.dafy.octopus.rental.api.order;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dafy.common.po.Response;
import com.dafy.common.util.StringUtils;
import com.dafy.octopus.rental.service.order.OrderInfoService;
import com.dafy.octopus.rental.dto.DO.order.OrderHandleInfoDO;
import com.dafy.octopus.web.core.domain.Request;
import com.dafy.octopus.web.core.utils.RequestUtils;
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

import javax.servlet.http.HttpServletRequest;

@Controller
@Api(tags={"orderInfo"})
public class OrderInfoController {
    protected  final Logger logger = LoggerFactory.getLogger(OrderHandleInfoController.class);

    @Autowired
    private OrderInfoService orderInfoService;

    @GetMapping("/orderInfo/getOrderDetailInfo")
    @RequiresPermissions(value = {"CustomerList:list","CustomInfo:list"} , logical = Logical.OR)
    @ApiOperation(value="获取订单详情",notes="获取订单详情")
    public
    @ResponseBody
    Response getServerInfoDetail(@ApiParam(value="orderNo 订单号") @RequestParam(required=true,value = "orderNo") String orderNo){
        try {
            Response<JSONObject> response = new Response<JSONObject>();
            JSONObject result = orderInfoService.getOrderDetailInfo(orderNo);
            response.setCode(StringUtils.getCode(result.getString("code")));
            response.setMsg(result.getString("msg"));
            JSONObject data = JSONObject.parseObject(result.getString("data"));
            response.setData(data);
            return response;
        } catch (Exception e) {
            logger.error("获取订单详情错误",e);
            return new Response("110", "系统异常，请联系管理员");
        }
    }

    @GetMapping("/orderInfo/getOrderNoList")
    @RequiresPermissions(value = {"CustomerList:list","CustomInfo:list"} , logical = Logical.OR)
    @ApiOperation(value="获取客户的订单号列表",notes="获取客户的订单号列表")
    public
    @ResponseBody
    Response getOrderNoList(@ApiParam(value="userId / customerId 客户ID") @RequestParam(required=true,value = "userId") String userId){
        try {
            Response<JSONArray> response = new Response<JSONArray>();
            JSONObject result = orderInfoService.getOrderNoList(userId);
            response.setCode(StringUtils.getCode(result.getString("code")));
            response.setMsg(result.getString("msg"));
            JSONArray data = JSONArray.parseArray(result.getString("data"));
            response.setData(data);
            return response;
        } catch (Exception e) {
            logger.error("获取客户的订单号列表错误",e);
            return new Response("110", "系统异常，请联系管理员");
        }
    }

//    @GetMapping("/orderInfo/getOrderFeeInfo")
//    @RequiresPermissions("CustomInfo:list")
//    @ApiOperation(value="获取订单费用信息",notes="获取订单费用信息")
//    public
//    @ResponseBody
//    Response getOrderFeeInfo(@ApiParam(value="orderNo 订单ID ") @RequestParam(required=true,value = "orderNo") String orderNo,
//                             @ApiParam(value="订单详情接口返回的 feeNo") @RequestParam(required=true,value = "feeNo") String feeNo){
//        try {
//            Response<JSONObject> response = new Response<JSONObject>();
//            JSONObject result = orderInfoService.getOrderFeeInfo(orderNo, feeNo);
//            response.setCode(StringUtils.getCode(result.getString("code")));
//            response.setMsg(result.getString("msg"));
//
//            JSONObject data = JSONObject.parseObject(result.getString("data"));
//            response.setData(data);
//            return response;
//        } catch (Exception e) {
//            logger.error("获取订单费用信息错误",e);
//            return new Response("110", "系统异常，请联系管理员");
//        }
//    }

    @GetMapping("/orderInfo/getOrderExpressInfo")
    @RequiresPermissions(value = {"CustomerList:list","CustomInfo:list"} , logical = Logical.OR)
    @ApiOperation(value="获取订单物流/快递信息",notes="获取订单物流/快递信息")
    public
    @ResponseBody
    Response getOrderExpressInfo(@ApiParam(value="物流单号") @RequestParam(required=true,value = "deliveryOrderNo") String deliveryOrderNo){
        try {
            Response<JSONObject> response = new Response<JSONObject>();

            JSONObject result = orderInfoService.getOrderExpressInfo(deliveryOrderNo);
            response.setCode(StringUtils.getCode(result.getString("code")));
            response.setMsg(result.getString("msg"));

            JSONObject data = JSONObject.parseObject(result.getString("data"));
            response.setData(data);
            return response;
        } catch (Exception e) {
            logger.error("获取订单物流/快递信息错误",e);
            return new Response("110", "系统异常，请联系管理员");
        }
    }

    @GetMapping("/orderInfo/cancelOrder")
    @RequiresPermissions(value = {"CustomerList:list","CustomInfo:list"} , logical = Logical.OR)
    @ApiOperation(value="取消订单接口",notes="取消订单接口")
    public
    @ResponseBody
    Response cancelOrder(@ModelAttribute OrderHandleInfoDO orderHandleInfo, final HttpServletRequest request){
        try {
            orderHandleInfo.setHandleIpAddress(RequestUtils.getIpAddress(request));
            JSONObject result = orderInfoService.cancelOrder(orderHandleInfo);

            Response<String> response = new Response<String>();
            response.setCode(StringUtils.getCode(result.getString("code")));
            response.setMsg(result.getString("msg"));
            return response;
        } catch (Exception e) {
            logger.error("取消订单接口错误",e);
            return new Response("110", "系统异常，请联系管理员");
        }
    }


    @PostMapping("/orderInfo/delay")
    @RequiresPermissions(value = {"CustomerList:list","CustomInfo:list"} , logical = Logical.OR)
    @ApiOperation(value="订单延期",notes="订单延期")
    public
    @ResponseBody
    Response delayOrder(@RequestBody Request request){
        try {
            JSONObject result = orderInfoService.delayOrder(request);

            Response<String> response = new Response<String>();
            response.setCode(StringUtils.getCode(result.getString("code")));
            response.setMsg(result.getString("msg"));
            return response;
        } catch (Exception e) {
            logger.error("订单延期订单接口错误",e);
            return new Response("110", "系统异常，请联系管理员");
        }
    }



}
