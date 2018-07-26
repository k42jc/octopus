package com.dafy.octopus.rental.service.systemConfig;

import com.dafy.octopus.rental.dto.VO.systemConfig.SourceTypeVO;
import com.dafy.octopus.rental.dto.DO.systemConfig.SourceTypeDO;

import java.util.List;

public interface SourceTypeService {
    Integer addSourceType(String name);
    void updateSourceType(SourceTypeDO sourceTypeDO);
    void deleteSourceType(Integer id);
    List<SourceTypeVO> getAll();
}
