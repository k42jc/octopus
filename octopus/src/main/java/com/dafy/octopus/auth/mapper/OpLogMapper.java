package com.dafy.octopus.auth.mapper;

import com.dafy.dal.page.po.Page;
import com.dafy.octopus.web.core.dto.OpLog;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OpLogMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OpLog record);

    int insertSelective(OpLog record);

    OpLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OpLog record);

    int updateByPrimaryKeyWithBLOBs(OpLog record);

    int updateByPrimaryKey(OpLog record);

    List<OpLog> selectList(Page page);
}