package com.dafy.octopus.rental.service.systemConfig;

import com.dafy.octopus.rental.dto.DO.systemConfig.GoodsTypeDO;
import com.dafy.octopus.rental.dto.VO.systemConfig.GoodsTypeVO;

import java.util.List;

public interface GoodsTypeService {
    Long addGoodsType(GoodsTypeDO goodsTypeDO);
    void updateGoodsType(GoodsTypeDO goodsTypeDO);
    void deleteGoodsType(Long id);
    //通过父ID获取子列表
    List<GoodsTypeVO> getGoodsType(Long parentId);

    //通过父ID获取子列表
    List<GoodsTypeVO> getGoodsTypeAll(Long parentId);
}
