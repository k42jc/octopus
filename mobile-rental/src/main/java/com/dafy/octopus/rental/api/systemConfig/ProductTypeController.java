package com.dafy.octopus.rental.api.systemConfig;

import com.dafy.common.po.Response;
import com.dafy.octopus.rental.dto.DO.systemConfig.ProductTypeDO;
import com.dafy.octopus.rental.dto.VO.systemConfig.ProductTypeVO;
import com.dafy.octopus.rental.service.systemConfig.ProductTypeService;
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
@Api(tags={"productType"})
public class ProductTypeController {

    protected  final Logger logger = LoggerFactory.getLogger(ProductTypeController.class);
    
    @Autowired 
    private ProductTypeService productTypeService;
    
    @PostMapping("/productType/addProductType")
    @RequiresPermissions(value = {"CommunicationSummerySetting:list","CustomInfo:list"}, logical = Logical.OR)
    public
    @ResponseBody
    Response addProductType(@ApiParam(value="产品类型名称") @RequestParam(required=true,value = "name") String name){
        try {
            Response<Integer> response = new Response<Integer>();
            response.setData(productTypeService.addProductType(name));
            return response;
        } catch (Exception e) {
            logger.error("添加产品类型错误",e);
            return new Response("110", "系统异常，请联系管理员");
        }
    }

    @PostMapping("/productType/updateProductType")
    @RequiresPermissions(value = {"CommunicationSummerySetting:list","CustomInfo:list"}, logical = Logical.OR)
    public
    @ResponseBody
    Response updateProductType(@ApiParam(value="产品类型名称") @RequestParam(required=true,value = "name") String name,
                                     @ApiParam(value="id") @RequestParam(required=true,value = "id") Integer id){
        try {
            productTypeService.updateProductType(new ProductTypeDO(name, id));
            return new Response();
        } catch (Exception e) {
            logger.error("更新产品类型错误",e);
            return new Response("110", "系统异常，请联系管理员");
        }
    }

    @PostMapping("/productType/deleteProductType")
    @RequiresPermissions(value = {"CommunicationSummerySetting:list","CustomInfo:list"}, logical = Logical.OR)
    public
    @ResponseBody
    Response deleteProductType(@ApiParam(value="id") @RequestParam(required=true,value = "id") Integer id){
        try {
            productTypeService.deleteProductType(id);
            return new Response();
        } catch (Exception e) {
            logger.error("删除产品类型错误",e);
            return new Response("110", "系统异常，请联系管理员");
        }
    }

    @GetMapping("/productType/getAll")
    @RequiresPermissions(value = {"CommunicationSummerySetting:list","CustomInfo:list","CustomerList:list"}, logical = Logical.OR)
    public
    @ResponseBody
    Response getAll(){
        try {
            Response<List<ProductTypeVO>> response = new Response<List<ProductTypeVO>>();
            response.setData(productTypeService.getAll());
            return response;
        } catch (Exception e) {
            logger.error("获取产品类型错误",e);
            return new Response("110", "系统异常，请联系管理员");
        }
    }
}
