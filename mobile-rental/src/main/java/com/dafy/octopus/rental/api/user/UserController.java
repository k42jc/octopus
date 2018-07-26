package com.dafy.octopus.rental.api.user;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dafy.common.po.Response;
import com.dafy.common.util.StringUtils;
import com.dafy.octopus.rental.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@Controller
@Api(tags={"userInfo"}) // Swagger UI 对应api的标题描述
public class UserController {
    protected  final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @GetMapping("/user/getUserList")
    @RequiresPermissions(value = {"CustomerList:list","CustomInfo:list"} , logical = Logical.OR)
    @ApiOperation(value="查询用户列表",notes="查询用户列表")
    public
    @ResponseBody
    Response getUserList(@ApiParam(value="姓名/身份证/电话号码") @RequestParam(required=false,value = "key") String key,
                                @ApiParam(value="注册时间，从xx开始 yyyy-MM-dd") @RequestParam(required=false,value = "startDate")@DateTimeFormat(pattern="yyyy-MM-dd")Date startDate,
                                @ApiParam(value="注册时间，到xx结束 yyyy-MM-dd") @RequestParam(required=false,value = "endDate")@DateTimeFormat(pattern="yyyy-MM-dd")Date endDate,
                                @ApiParam(value="分页大小") @RequestParam(required=false,value = "pageSize")int pageSize,
                                @ApiParam(value="当前页") @RequestParam(required=false,value = "currentPage")int currentPage){
        try {

            if (endDate != null) endDate = new DateTime(endDate).plusHours(23).plusMinutes(59).plusSeconds(59).toDate();

            Response<JSONObject> response = new Response<JSONObject>();
            JSONObject result = userService.queryUserList(key, startDate, endDate, pageSize, currentPage);

            response.setCode(StringUtils.getCode(result.getString("code")));
            response.setMsg(result.getString("msg"));
            response.setData(JSONObject.parseObject(result.getString("data")));
            return response;
        } catch (Exception e) {
            logger.error("查询用户列表错误",e);
            return new Response("110", "系统异常，请联系管理员");
        }
    }

    @GetMapping("/user/getUserDetailInfo")
    @RequiresPermissions(value = {"CustomerList:list","CustomInfo:list"} , logical = Logical.OR)
    @ApiOperation(value="获取客户详细信息",notes="获取客户详细信息")
    public
    @ResponseBody
    Response getUserDetailInfo(@ApiParam(value="用户ID:customerid") @RequestParam(required=false,value = "customerid") String customerid){
        try {
            Response<JSONObject> response = new Response<JSONObject>();
            JSONObject result = userService.getUserDetailInfo(customerid);

            response.setCode(StringUtils.getCode(result.getString("code")));
            response.setMsg(result.getString("msg"));
            response.setData(JSONObject.parseObject(result.getString("data")));
            return response;
        } catch (Exception e) {
            logger.error("获取客户详细信息错误",e);
            return new Response("110", "系统异常，请联系管理员");
        }
    }

    @GetMapping("/user/getUserBriefInfo")
    @RequiresPermissions(value = {"CustomerList:list","CustomInfo:list"} , logical = Logical.OR)
    @ApiOperation(value="获取客户简要信息",notes="获取客户简要信息")
    public
    @ResponseBody
    Response getUserBriefInfo(@ApiParam(value="用户ID:customerid") @RequestParam(required=false,value = "customerid") String customerid){
        try {
            Response<JSONObject> response = new Response<JSONObject>();
            JSONObject result = userService.getUserBriefInfo(customerid);

            response.setCode(StringUtils.getCode(result.getString("code")));
            response.setMsg(result.getString("msg"));
            response.setData(JSONObject.parseObject(result.getString("data")));
            return response;
        } catch (Exception e) {
            logger.error("获取客户详细信息错误",e);
            return new Response("110", "系统异常，请联系管理员");
        }
    }

    @GetMapping("/user/queryUserList")
    @RequiresPermissions(value = {"CustomerList:list","CustomInfo:list"} , logical = Logical.OR)
    @ApiOperation(value="模糊查询客户信息列表",notes="模糊查询客户信息列表")
    public
    @ResponseBody
    Response queryUserList(@ApiParam(value="查询字符串：key") @RequestParam(required=false,value = "key") String key){
        try {
            Response<JSONArray> response = new Response<JSONArray>();
            JSONObject result = userService.queryUserInfo(key);

            response.setCode(StringUtils.getCode(result.getString("code")));
            response.setMsg(result.getString("msg"));
            JSONObject data = JSONObject.parseObject(result.getString("data"));
            response.setData(JSONArray.parseArray(data.getString("list")));
            return response;
        } catch (Exception e) {
            logger.error("模糊查询客户信息列表错误",e);
            return new Response("110", "系统异常，请联系管理员");
        }
    }
}
