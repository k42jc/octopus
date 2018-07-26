package com.dafy.octopus.rental.api.systemConfig;

import com.dafy.common.exception.ServerException;
import com.dafy.common.po.Response;
import com.dafy.octopus.rental.service.systemConfig.GoodsTypeService;
import com.dafy.octopus.rental.dto.DO.systemConfig.GoodsTypeDO;
import com.dafy.octopus.rental.dto.VO.systemConfig.GoodsTypeVO;
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
@Api(tags={"goodsType"})
public class GoodsTypeController {
    protected  final Logger logger = LoggerFactory.getLogger(DealStatusController.class);

    @Autowired
    private GoodsTypeService goodsTypeService;

    @PostMapping("/goodsType/addGoodsType")
    @RequiresPermissions(value = {"CommunicationSummerySetting:list","CustomInfo:list"}, logical = Logical.OR)
    public
    @ResponseBody
    Response addGoodsType(@ApiParam(value="商品类型名称") @RequestParam(required=true,value = "name") String name,
                                   @ApiParam(value="父id,如果是一级目录填0，或者不填") @RequestParam(required=true,value = "id") Long parentId){
        try {
            if (parentId == null) parentId = 0L;

            Response<Long> response = new Response<Long>();
            response.setData(goodsTypeService.addGoodsType(new GoodsTypeDO(parentId, name)));
            return response;
        } catch (Exception e) {
            logger.error("添加商品类型错误",e);

            if (e instanceof ServerException){
                return new Response(((ServerException) e).getCode(), ((ServerException) e).getDesc());
            }

            return new Response("110", "系统异常，请联系管理员");
        }
    }

    @PostMapping("/goodsType/updateGoodsType")
    @RequiresPermissions(value = {"CommunicationSummerySetting:list","CustomInfo:list"}, logical = Logical.OR)
    public
    @ResponseBody
    Response updateGoodsType(@ApiParam(value="商品类型名称") @RequestParam(required=true,value = "name") String name,
                                      @ApiParam(value="id") @RequestParam(required=true,value = "id") Long id){
        try {
            goodsTypeService.updateGoodsType(new GoodsTypeDO(name, id));
            return new Response();
        } catch (Exception e) {
            logger.error("更新商品类型错误",e);
            return new Response(e);
        }
    }

    @PostMapping("/goodsType/deleteGoodsType")
    @RequiresPermissions(value = {"CommunicationSummerySetting:list","CustomInfo:list"}, logical = Logical.OR)
    public
    @ResponseBody
    Response deleteGoodsType(@ApiParam(value="id") @RequestParam(required=true,value = "id") Long id){
        try {
            goodsTypeService.deleteGoodsType(id);
            return new Response();
        } catch (Exception e) {
            logger.error("删除商品类型错误",e);
            return new Response("110", "系统异常，请联系管理员");
        }
    }

    @GetMapping("/goodsType/getGoodsType")
    @RequiresPermissions(value = {"CommunicationSummerySetting:list","CustomInfo:list","CustomerList:list"}, logical = Logical.OR)
    public
    @ResponseBody
    Response getGoodsType(@ApiParam(value="id") @RequestParam(required=false,value = "id") Long id){
        try {
            if (id == null) id=0L;

            Response<List<GoodsTypeVO>> response = new Response<List<GoodsTypeVO>>();
            response.setData(goodsTypeService.getGoodsType(id));
            return response;
        } catch (Exception e) {
            logger.error("获取商品类型错误",e);
            return new Response("110", "系统异常，请联系管理员");
        }
    }

    @GetMapping("/goodsType/getGoodsTypeAll")
    @RequiresPermissions(value = {"CommunicationSummerySetting:list","CustomInfo:list","CustomerList:list"}, logical = Logical.OR)
    public
    @ResponseBody
    Response getGoodsTypeAll(@ApiParam(value="id") @RequestParam(required=false,value = "id") Long id){
        try {
            if (id == null) id=0L;

            Response<List<GoodsTypeVO>> response = new Response<List<GoodsTypeVO>>();
            response.setData(goodsTypeService.getGoodsTypeAll(id));
            return response;
        } catch (Exception e) {
            logger.error("获取所有商品类型错误",e);
            return new Response("110", "系统异常，请联系管理员");
        }
    }
}
