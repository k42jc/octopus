package com.dafy.octopus.worker.order.api;

import com.dafy.common.po.Response;
import com.dafy.common.util.StringUtils;
import com.dafy.octopus.web.core.context.SystemContext;
import com.dafy.octopus.web.core.domain.Request;
import com.dafy.octopus.web.core.dto.Organization;
import com.dafy.octopus.web.core.dto.User;
import com.dafy.octopus.web.core.utils.CommonUtils;
import com.dafy.octopus.web.core.utils.SubjectUtils;
import com.dafy.octopus.worker.order.service.IBillService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 工单接口api
 * Created by liaoxudong
 * Date:2018/6/4
 */

@RestController
@RequestMapping("/bill")
@Api(description = "工单相关api")
public class BillApi {
    private static final Logger logger = LoggerFactory.getLogger(BillApi.class);
    @Autowired
    private IBillService billService;

    @RequiresPermissions("WorkOrder:create")
    @PostMapping("/create")
    @ApiOperation(value = "创建工单，需要创建工单权限")
    public Response create(@RequestBody Request request){
        logger.info("创建工单：{}",request);
        return billService.create(request);
    }

    @GetMapping("/count/{type}")
    @ApiOperation(value = "我的待办工单数:type=upcoming")
    public Response preDealCount(@PathVariable("type") String type){
        logger.info("获取数量：{}",type);
        return billService.count(type);
    }



    @GetMapping("/list")
    @ApiOperation("获取工单列表")
    @ApiImplicitParam(name = "header", value = "在http头部携带分页逻辑：{\n" +
            "\t\"pagenum\":\"页码\",\n" +
            "\t\"pagesize\":\"页容量\"\n" +
            "}")
    @ResponseBody
    public Response list(@RequestHeader Map<String,Object> header,
                         @RequestParam(value = "onlyMime",required = false) boolean onlyMime,// 获取我的工单
                         @RequestParam(value = "upcoming",required = false) boolean upcoming,// 获取我的待办工单
                         @RequestParam(value = "createTimeStart",required = false) String createTimeStart,
                         @RequestParam(value = "createTimeEnd",required = false) String createTimeEnd,
                         @RequestParam(value = "condition",required = false) String condition,
                         @RequestParam(value = "urgent",required = false) Long urgent,
                         @RequestParam(value = "billType",required = false) Long billType,
                         @RequestParam(value = "bizType",required = false) Long bizType,
                         @RequestParam(value = "bizDetailType",required = false) Long bizDetailType,
                         @RequestParam(value = "billStatus",required = false) List<Long> billStatus){
        logger.info("获取工单列表");
        Request request = new Request();
        request.put("pagenum", header.get("pagenum"));
        request.put("pagesize", header.get("pagesize"));
        if (!StringUtils.isEmpty(createTimeStart)) {
            request.put("createTimeStart", createTimeStart);
        }
        if (!StringUtils.isEmpty(createTimeEnd)) {
            request.put("createTimeEnd", createTimeEnd);
        }
        if (!StringUtils.isEmpty(condition)) {
            request.put("condition", condition);
        }
        if (urgent != null && urgent != 0L) {
            request.put("urgent", urgent);
        }
        if (billType != null && billType != 0L) {
            request.put("billType", billType);
        }
        if (bizType != null && bizType != 0L) {
            request.put("bizType", bizType);
        }
        if (bizDetailType != null && bizDetailType != 0L) {
            request.put("bizDetailType", bizDetailType);
        }
        if (billStatus != null && !billStatus.isEmpty()) {
            /*StringBuilder builder = new StringBuilder();
            for (Long bs : billStatus) {
                builder.append(bs).append(",");
            }
            builder.deleteCharAt(builder.length() - 1);*/
            request.put("billStatus", billStatus);
        }
        if (onlyMime) {// 只有在只查询我的工单时传入true
            request.put("userId", SubjectUtils.getCurrentUser().getId());
        }
        request.put("upcoming", upcoming);
        Response list = billService.list(request);
        return list;
    }

    @GetMapping("/detail/{billId}")
    @ApiOperation("获取工单详情")
    public Response detail(@PathVariable("billId") Long billId){
        return billService.detail(billId);
    }

    /**
     * 工单更新处理人，包括三种操作：</br>
     *      释放 委托 回退
     *
     *
     */
    @PutMapping("/updateHandlerMan")
    @ApiOperation("释放、委托、回退、关单、催办操作")
    @ApiImplicitParam( name = "request",value = "" +
            "     *        -type 处理类型：0释放审核中为待审核，1释放处理中为待处理，2委托，3回退，4关单,5催办\n" +
            "     *        -billId 工单id\n" +
            "     *        -dealUserId 当类型为委托时请传入此参数，表示目标委托人")
    public Response updateHandlerMan(@RequestBody Request request){
        CommonUtils.assertHasParams(request, "type,billId");
        return billService.updateHandlerMan(request);
    }


    /**
     * 工单更新处理人，包括三种操作：</br>
     *      释放 委托 回退
     *
     *
     */
    @PutMapping("/handler")
    @ApiOperation("审批、处理操作")
    @ApiImplicitParam( name = "request",value = "" +
            "     *        -type 处理类型：1审批，2处理，3审批暂存，4处理暂存\n" +
            "     *        -billId 工单id \n" +
            "     *        -data 根据处理方式不同传递不同值")
    public Response handler(@RequestBody Request request){
        CommonUtils.assertHasParams(request, "type,billId");
        return billService.handler(request);
    }


    @PostMapping("/addRemark")
    @ApiOperation("添加备注")
    @ApiImplicitParam( name = "request",value = "" +
            "     *        -billId 工单id\n" +
            "     *        -remark 备注内容 ")
    public Response addRemark(@RequestBody Request request){
        CommonUtils.assertHasParams(request, "billId");
        return billService.addRemark(request);
    }

    @RequiresPermissions("WorkOrder:export")
    @GetMapping("/export")
    @ApiOperation("导出按条件查询出来的所有数据")
    public Response export(@RequestParam(value = "onlyMime",required = false) boolean onlyMime,// 获取我的工单
                           @RequestParam(value = "upcoming",required = false) boolean upcoming,// 获取我的待办工单
                              @RequestParam(value = "createTimeStart",required = false) String createTimeStart,
                              @RequestParam(value = "createTimeEnd",required = false) String createTimeEnd,
                              @RequestParam(value = "condition",required = false) String condition,
                              @RequestParam(value = "urgent",required = false) Long urgent,
                              @RequestParam(value = "billType",required = false) Long billType,
                              @RequestParam(value = "bizType",required = false) Long bizType,
                              @RequestParam(value = "bizDetailType",required = false) Long bizDetailType,
                              @RequestParam(value = "billStatus",required = false) List<Long> billStatus) throws FileNotFoundException {
        logger.info("获取工单列表");
        Request request = new Request();
        if (!StringUtils.isEmpty(createTimeStart)) {
            request.put("createTimeStart", createTimeStart);
        }
        if (!StringUtils.isEmpty(createTimeEnd)) {
            request.put("createTimeEnd", createTimeEnd);
        }
        if (!StringUtils.isEmpty(condition)) {
            request.put("condition", condition);
        }
        if (urgent != null && urgent != 0L) {
            request.put("urgent", urgent);
        }
        if (billType != null && billType != 0L) {
            request.put("billType", billType);
        }
        if (bizType != null && bizType != 0L) {
            request.put("bizType", bizType);
        }
        if (bizDetailType != null && bizDetailType != 0L) {
            request.put("bizDetailType", bizDetailType);
        }
        if (billStatus != null && !billStatus.isEmpty()) {
            request.put("billStatus", billStatus);
        }
        if (onlyMime) {// 只有在只查询我的工单时传入true
            request.put("userId", SubjectUtils.getCurrentUser().getId());
        }
        request.put("upcoming", upcoming);
        return billService.export(request);
//        File file = new File("D:\\Users\\liaoxudong\\Desktop\\导出模板.xls");
//        return new FileInputStream(file);
    }

    /**
     * 获取委托用户列表
     *
     * @return
     */
    @RequiresPermissions("WorkOrder:intrust")
    @GetMapping("/refUserList")
    @ApiOperation("获取工单委托用户列表，只会获取到当前部门所有人")
    public Response getRefUserList() {
        Organization organization = SubjectUtils.getCurrentUser().getOrganization();
//        Organization parentOrg = organization.getParentOrg();
//        Long orgId =  parentOrg == null?organization.getId():parentOrg.getId();
        List<User> users = SystemContext.getUsersByOrgId(organization.getId());
        return CommonUtils.buildSuccessResp(users);
    }


    /**
     * 获取委托用户列表
     *
     * @return
     */
    @RequiresPermissions("WorkOrder:intrust")
    @GetMapping("/getRefUser")
    @ApiOperation("根据工号/姓名/电话过滤目标委托人")
    public Response getRefUser(@RequestParam(required = false) String condition) {
        Organization organization = SubjectUtils.getCurrentUser().getOrganization();
//        Organization parentOrg = organization.getParentOrg();
//        Long orgId =  parentOrg == null?organization.getId():parentOrg.getId();
        Long orgId =  organization.getId();
        List<User> users = SystemContext.getUsersByOrgId(orgId);
        if (StringUtils.isEmpty(condition)) {
            return CommonUtils.buildSuccessResp(users);
        }
        if(users != null){
            List<User> resultList = new ArrayList<>();
            users.forEach(user -> {
                user.setRoleList(null);
                user.setPermissionList(null);
                if (user.getUserName().contains(condition) || user.getUserCode().contains(condition) || user.getPhoneNo().contains(condition)) {
                    resultList.add(user);
                }
            });
            return CommonUtils.buildSuccessResp(resultList);
        }
        return CommonUtils.buildSuccessResp(users);
    }

    @PostMapping("/onStatusChange")
    @ApiOperation("在点击待审核/待处理时调用此接口，校验工单状态，如果无权限、不是当前处理人则返回错误，如果条件符合，则返回暂存信息")
    @ApiImplicitParam( name = "request",value = "" +
            "     *        -type 类型：1 更改为审核中 2 更改为处理中\n" +
            "     *        -billId 工单id ")
    public Response statusChange(@RequestBody Request request){
        return billService.changeStatus(request);
    }

    @PutMapping("/updateAttach")
    @ApiOperation("更新工单附件信息")
    @ApiImplicitParam( name = "request",value = "" +
            "     *        -type 类型：1 更改工单附件信息 2 更改工单审批附件信息 3 更改工单处理附件信息\n" +
            "     *        -data:{id:工单id/审核id/处理id,url:\"当前附件地址，多个以逗号分隔\"} ")
    public Response updateAttach(@RequestBody Request request){
        return billService.updateAttach(request);
    }


}
