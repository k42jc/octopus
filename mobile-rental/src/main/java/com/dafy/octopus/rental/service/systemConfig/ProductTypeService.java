package com.dafy.octopus.rental.service.systemConfig;

import com.dafy.octopus.rental.dto.VO.systemConfig.ProductTypeVO;
import com.dafy.octopus.rental.dto.DO.systemConfig.ProductTypeDO;

import java.util.List;

public interface ProductTypeService {
    Integer addProductType(String name);
    void updateProductType(ProductTypeDO productTypeDO);
    void deleteProductType(Integer id);
    List<ProductTypeVO> getAll();
}
