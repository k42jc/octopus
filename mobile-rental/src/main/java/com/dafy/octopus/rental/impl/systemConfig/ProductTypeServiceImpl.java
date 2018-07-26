package com.dafy.octopus.rental.impl.systemConfig;

import com.dafy.octopus.rental.dto.DO.systemConfig.ProductTypeDO;
import com.dafy.octopus.rental.dto.VO.systemConfig.ProductTypeVO;
import com.dafy.octopus.rental.impl.BaseService;
import com.dafy.octopus.rental.mapper.systemConfig.ProductTypeMapper;
import com.dafy.octopus.rental.service.systemConfig.ProductTypeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class ProductTypeServiceImpl extends BaseService implements ProductTypeService {

    @Resource
    private ProductTypeMapper productTypeMapper;

    @Override
    @Transactional
    public Integer addProductType(String name) {
        ProductTypeDO productType = new ProductTypeDO(name, new Date());
        add(productTypeMapper, productType);
        return productType.getId();
    }

    @Override
    @Transactional
    public void updateProductType(ProductTypeDO productTypeDO) {
        update(productTypeMapper, productTypeDO);
    }

    @Override
    @Transactional
    public void deleteProductType(Integer id) {
        productTypeMapper.delete(id);
    }

    @Override
    public List<ProductTypeVO> getAll() {
        return productTypeMapper.getAll();
    }
}
