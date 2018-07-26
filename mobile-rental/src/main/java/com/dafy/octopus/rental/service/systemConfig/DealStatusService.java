package com.dafy.octopus.rental.service.systemConfig;

import com.dafy.octopus.rental.dto.DO.systemConfig.DealStatusDO;
import com.dafy.octopus.rental.dto.VO.systemConfig.DealStatusVO;

import java.util.List;

public interface DealStatusService {
    Integer addDealStatus(String name);
    void updateDealStatus(DealStatusDO dealStatusDO);
    void deleteDealStatus(Integer id);
    List<DealStatusVO> getAll();
}
