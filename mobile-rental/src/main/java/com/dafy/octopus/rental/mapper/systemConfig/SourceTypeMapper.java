package com.dafy.octopus.rental.mapper.systemConfig;

import com.dafy.octopus.rental.dto.VO.systemConfig.SourceTypeVO;
import com.dafy.octopus.rental.dto.DO.systemConfig.SourceTypeDO;
import com.dafy.octopus.rental.mapper.IBaseMapper;

import java.util.List;

public interface SourceTypeMapper extends IBaseMapper<SourceTypeDO, SourceTypeVO, Integer> {
    List<SourceTypeVO> getAll();
}
