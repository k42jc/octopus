package com.dafy.octopus.rental.mapper.systemConfig;

import com.dafy.octopus.rental.dto.DO.systemConfig.DealStatusDO;
import com.dafy.octopus.rental.dto.VO.systemConfig.DealStatusVO;
import com.dafy.octopus.rental.mapper.IBaseMapper;

import java.util.List;

public interface DealStatusMapper extends IBaseMapper<DealStatusDO, DealStatusVO, Integer> {
    List<DealStatusVO> getAll();
}
