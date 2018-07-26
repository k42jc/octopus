package com.dafy.octopus.rental.api.order;

import com.dafy.common.po.Response;
import com.dafy.octopus.rental.dto.VO.order.OrderHandleInfoVO;
import com.dafy.octopus.rental.service.order.OrderHandleInfoService;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

@Controller
@Api(tags={"orderHandleInfo"})
public class OrderHandleInfoController {
    @Autowired
    private OrderHandleInfoService orderHandleInfoService;

    protected  final Logger logger = LoggerFactory.getLogger(OrderHandleInfoController.class);

    @PostMapping("/orderHandleInfo/queryOrderHandleInfoList")
    @RequiresPermissions(value = {"CustomerList:list","CustomInfo:list","CustomerList:list"} , logical = Logical.OR)
    @ApiOperation(value="查询操作信息列表",notes="查询操作信息列表")
    public
    @ResponseBody
    Response queryOrderHandleInfoList(@ApiParam(value="用户userId") @RequestParam(required=false,value = "userId") String userId,
                                      @ApiParam(value="姓名/手机号") @RequestParam(required=false,value = "key") String key,
                                      @ApiParam(value="开始时间 格式：yyyy-MM-dd") @RequestParam(required=false,value = "startDate")@DateTimeFormat(pattern="yyyy-MM-dd") Date startDate,
                                      @ApiParam(value="开始时间 格式：yyyy-MM-dd") @RequestParam(required=false,value = "endDate")@DateTimeFormat(pattern="yyyy-MM-dd") Date endDate){
        try {
            if (endDate != null) endDate = new DateTime(endDate).plusHours(23).plusMinutes(59).plusSeconds(59).toDate();

            Response<List<OrderHandleInfoVO>> response = new Response<List<OrderHandleInfoVO>>();
            response.setData(orderHandleInfoService.queryOrderHandleInfoList(userId, key, startDate, endDate));
            return response;
        } catch (Exception e) {
            logger.error("查询操作信息列表错误",e);
            return new Response("110", "系统异常，请联系管理员");
        }
    }
}
