package com.dafy.octopus.rental.mapper.systemConfig;

import com.dafy.octopus.rental.dto.DO.systemConfig.ProductTypeDO;
import com.dafy.octopus.rental.dto.VO.systemConfig.ProductTypeVO;
import com.dafy.octopus.rental.mapper.IBaseMapper;

import java.util.List;

public interface ProductTypeMapper extends IBaseMapper<ProductTypeDO, ProductTypeVO, Integer>{
    List<ProductTypeVO> getAll();
}
