package com.dafy.octopus.auth.api;

import com.dafy.common.po.Response;
import com.dafy.octopus.web.core.domain.OpLogType;
import com.dafy.octopus.auth.service.IUserService;
import com.dafy.octopus.web.core.utils.CommonUtils;
import com.dafy.octopus.web.core.utils.RequestUtils;
import com.dafy.octopus.auth.aop.DisableEditAdmin;
import com.dafy.octopus.web.core.aop.OpLogRecord;
import com.dafy.octopus.web.core.domain.Request;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 用户相关api
 * 登录、注册、权限等
 * Created by liaoxudong
 * Date:2018/1/30
 */

@Api(description = "用户登录/注册/获取权限等")
@RestController
@RequestMapping("user")
public class UserApi {
    private static final Logger logger = LoggerFactory.getLogger(UserApi.class);

    @Autowired
    private IUserService userService;

    @ApiOperation(value = "用户登录，登录调用")
    @ApiImplicitParam(name = "request",value = "{\n" +
            "\t\"username\":\"用户名/手机号码/邮箱账号,登录时注意加密盐\",\n" +
            "\t\"password\":\"e10adc3949ba59abbe56e057f20f883e\",\n" +
            "\t\"rememberMe\":\"true\"\n" +
            "}")
    @PostMapping("/login")
    @OpLogRecord(OpLogType.LOGIN)// 记录操作日志，通过AOP统一处理 需要增加HttpServletRequest request参数用于获取请求ip
    public Response login(@RequestBody Request req, HttpServletRequest request) {
        req.put("host", RequestUtils.getIpAddress(request));
        CommonUtils.assertHasParams(req, "username,password,rememberMe");
        return userService.login(req);
    }

    @ApiOperation(value = "获取用户信息，登录后调用，直接请求")
    @GetMapping("/getInfo")
    public Response getInfo(HttpServletRequest request) {
        return userService.getInfo();
    }

    /**
     * 登出
     *
     * @return
     */
    @ApiOperation(value = "退出登录，直接请求")
    @PostMapping("/logout")
    @OpLogRecord(OpLogType.LOGOUT)
    public Response logout(HttpServletRequest request) {
        return userService.logout();
    }

    /**
     * 获取用户列表
     *
     * @return
     */
    @ApiOperation(value = "用户列表，/list/用户id，头部带分页逻辑，需要用户列表(UserManage:list)权限")
    @ApiImplicitParams({@ApiImplicitParam(name = "header", value = "在http头部携带分页逻辑：{\n" +
                    "\t\"pagenum\":\"起始页\",\n" +
                    "\t\"pagesize\":\"每页显示多少条\"\n" +
                    "}"),
                    @ApiImplicitParam(name = "request", value = "检索条件：{\n" +
                            "\t\"condition\":\"工号/姓名/邮箱/手机号\",\n" +
                            "\t\"orgId\":\"部门id\"\n" +
                            "}")
    })
    @RequiresPermissions("UserManage:list")
    @GetMapping("/list")
    public Response list(@RequestHeader Map<String, Object> header,@RequestParam(value = "condition",required = false) String condition
            ,@RequestParam(value = "orgId",required = false) Long orgId) {
        Object pagenum = header.get("pagenum");
        Object pagesize = header.get("pagesize");
        /*int currentPage = Integer.parseInt(header.get("pagenum").toString());
        int pageCount = Integer.parseInt(header.get("pagesize").toString());*/
//        return userService.listUser(currentPage, pageCount);
        Request request = new Request();
        request.put("condition", condition);
        request.put("orgId", orgId);
        return userService.findUserInfos(pagenum == null?1:Integer.valueOf(pagenum.toString()), pagesize == null?1000:Integer.valueOf(pagesize.toString()), request);
    }

    /*@RequiresPermissions("role:list")
    @GetMapping("/getAllRoles")
    public Response getAllRoles() {
        return userService.getAllRoles();
    }*/

    @ApiOperation(value = "添加用户，需要添加用户权限")
    @ApiImplicitParam(name = "request",value = "{\n" +
            "\t\"userCode\":\"工号\",\n" +
            "\t\"username\":\"姓名\",\n" +
            "\t\"email\":\"635684783@qq.com\",\n" +
            "\t\"phoneNo\":\"18711858117\",\n" +
            "\t\"password\":\"任意加密,注意加密盐\",\n" +
            "\t\"orgId\":\"部门id\",\n" +
            "\t\"roleId\":\"角色id\",\n" +
            "\t\"status\":\"是否启用\"\n" +
            "}")
    @RequiresPermissions("UserManage:add")
    @PostMapping("/add")
    @OpLogRecord(OpLogType.ADD_USER)
    public Response add(@RequestBody Request req,HttpServletRequest request){
        CommonUtils.assertHasParams(req, "userCode,username,roleId,orgId,password");
        return userService.add(req);
    }

    @ApiOperation(value = "更新用户，需要更新用户权限")
    @ApiImplicitParam(name = "request",value = "{\n" +
            "\t\"userId\":\"用户id\",\n" +
            "\t\"userCode\":\"工号\",\n" +
            "\t\"username\":\"姓名\",\n" +
            "\t\"email\":\"635684783@qq.com\",\n" +
            "\t\"phoneNo\":\"18711858117\",\n" +
            "\t\"password\":\"任意加密,注意加密盐\",\n" +
            "\t\"orgId\":\"部门id\",\n" +
            "\t\"roleId\":\"角色id\",\n" +
            "\t\"status\":\"是否启用\"\n" +
            "}")
    @RequiresPermissions("UserManage:edit")
    @PutMapping("/update")
    @OpLogRecord(OpLogType.EDIT_USER)
    @DisableEditAdmin
    public Response update(@RequestBody Request req,HttpServletRequest request){
//        CommonUtils.assertHasParams(request, "username,phoneNo,roleId,email,password");
        return userService.update(req);
    }

    /*@ApiOperation(value = "删除用户，需要删除用户(user:delete)权限")
    @RequiresPermissions("user:delete")
    @DeleteMapping("/delete")
    public Response delete(@RequestBody User request) {
        return userService.delete(request);
    }*/

    @PutMapping("/updatePwd")
    @ApiOperation("单独修改密码")
    @OpLogRecord(OpLogType.CHANGE_PWD)
    @DisableEditAdmin
    @ApiImplicitParam(name = "request",value = "{\n" +
            "\t\"userId\":\"用户id\",\n" +
            "\t\"oldPwd\":\"原密码\",\n" +
            "\t\"newPwd\":\"新密码\"\n" +
            "}")
    public Response updatePwd(@RequestBody Request req,HttpServletRequest request){
        CommonUtils.assertHasParams(req, "userId,oldPwd,newPwd");
        return userService.updatePwd(req);
    }

    @PutMapping("/enable/{userId}/{status}")
    @RequiresPermissions("UserManage:enable")
    @ApiOperation("启用/禁用用户")
    @OpLogRecord(OpLogType.ENABLE_USER)
    @DisableEditAdmin
    public Response enable(@PathVariable("userId") long userId,@PathVariable("status") int status,HttpServletRequest request){
        logger.debug("用户启用禁用请求：{}", status);
        return userService.enable(userId,status);

    }

}
