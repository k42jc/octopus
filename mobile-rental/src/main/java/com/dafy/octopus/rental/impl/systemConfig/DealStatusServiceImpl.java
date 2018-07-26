package com.dafy.octopus.rental.impl.systemConfig;

import com.dafy.octopus.rental.dto.DO.systemConfig.DealStatusDO;
import com.dafy.octopus.rental.dto.VO.systemConfig.DealStatusVO;
import com.dafy.octopus.rental.impl.BaseService;
import com.dafy.octopus.rental.mapper.systemConfig.DealStatusMapper;
import com.dafy.octopus.rental.service.systemConfig.DealStatusService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class DealStatusServiceImpl extends BaseService implements DealStatusService {
    @Resource
    private DealStatusMapper dealStatusMapper;

    @Override
    @Transactional
    public Integer addDealStatus(String name) {
        DealStatusDO dealStatus = new DealStatusDO(name, new Date());
        add(dealStatusMapper, dealStatus);
        return dealStatus.getId();
    }

    @Override
    @Transactional
    public void updateDealStatus(DealStatusDO dealStatusDO) {
        update(dealStatusMapper, dealStatusDO);
    }

    @Override
    @Transactional
    public void deleteDealStatus(Integer id) {
        dealStatusMapper.delete(id);
    }

    @Override
    public List<DealStatusVO> getAll() {

        return dealStatusMapper.getAll();
    }
}
