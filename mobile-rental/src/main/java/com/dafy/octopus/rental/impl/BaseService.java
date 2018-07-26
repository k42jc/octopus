package com.dafy.octopus.rental.impl;

import com.dafy.octopus.rental.dto.DO.BaseDO;
import com.dafy.octopus.rental.mapper.IBaseMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

public class BaseService {
    protected static final Logger logger = LoggerFactory.getLogger(BaseService.class);

    /**
     * 保存数据（插入或更新）
     *
     * @param entity
     */
    @Transactional
    public   <D extends IBaseMapper,E extends BaseDO> boolean add(D dao, E entity) {
        boolean ret = false;
        //添加时默认写入创建时间
        entity.setCreateDate(new Date());
        int rowCount = dao.add(entity);
        if(rowCount == 1){
            ret = true;
        }
        return ret;
    }

    @Transactional
    public  <D extends IBaseMapper,E extends BaseDO> boolean update(D dao, E entity) {
        boolean ret = false;
        //默认写入更新时间
        if (entity.getUpdateDate() == null)entity.setUpdateDate(new Date());
        try {
            dao.update(entity);
            ret = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }
}
