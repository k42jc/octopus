package com.dafy.octopus.rental.impl.systemConfig;

import com.dafy.octopus.rental.dto.VO.systemConfig.SourceTypeVO;
import com.dafy.octopus.rental.impl.BaseService;
import com.dafy.octopus.rental.mapper.systemConfig.SourceTypeMapper;
import com.dafy.octopus.rental.service.systemConfig.SourceTypeService;
import com.dafy.octopus.rental.dto.DO.systemConfig.SourceTypeDO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class SourceTypeServiceImpl extends BaseService implements SourceTypeService {

    @Resource
    private SourceTypeMapper sourceTypeMapper;

    @Override
    @Transactional
    public Integer addSourceType(String name) {
        SourceTypeDO sourceType = new SourceTypeDO(name, new Date());
        add(sourceTypeMapper, sourceType);
        return sourceType.getId();
    }

    @Override
    @Transactional
    public void updateSourceType(SourceTypeDO sourceTypeDO) {
        update(sourceTypeMapper, sourceTypeDO);
    }

    @Override
    @Transactional
    public void deleteSourceType(Integer id) {
        sourceTypeMapper.delete(id);
    }

    @Override
    public List<SourceTypeVO> getAll() {
        return sourceTypeMapper.getAll();
    }
}
