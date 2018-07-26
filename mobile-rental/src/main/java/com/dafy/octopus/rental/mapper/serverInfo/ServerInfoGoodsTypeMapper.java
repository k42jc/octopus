package com.dafy.octopus.rental.mapper.serverInfo;

import com.dafy.octopus.rental.dto.VO.serverInfo.ServerInfoGoodsTypeVO;
import com.dafy.octopus.rental.dto.DO.serverInfo.ServerInfoGoodsTypeDO;
import com.dafy.octopus.rental.mapper.IBaseMapper;

import java.util.List;

public interface ServerInfoGoodsTypeMapper extends IBaseMapper<ServerInfoGoodsTypeDO, ServerInfoGoodsTypeVO, Long> {
    List<ServerInfoGoodsTypeVO> getByServerInfoId(Long serverInfoId);
}
