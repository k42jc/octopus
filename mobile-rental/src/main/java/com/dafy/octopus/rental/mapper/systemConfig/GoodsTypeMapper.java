package com.dafy.octopus.rental.mapper.systemConfig;

import com.dafy.octopus.rental.dto.VO.systemConfig.GoodsTypeVO;
import com.dafy.octopus.rental.mapper.IBaseMapper;
import com.dafy.octopus.rental.dto.DO.systemConfig.GoodsTypeDO;

import java.util.List;
import java.util.Map;

public interface GoodsTypeMapper extends IBaseMapper<GoodsTypeDO, GoodsTypeVO, Long> {
    List<GoodsTypeVO> getChildGoodsType(Long parentId);
    List<GoodsTypeVO> query(Map<String, Object> params);
}
