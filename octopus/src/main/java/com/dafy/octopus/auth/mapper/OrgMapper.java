package com.dafy.octopus.auth.mapper;

import com.dafy.dal.page.po.Page;
import com.dafy.octopus.web.core.dto.Organization;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrgMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Organization record);

    int insertSelective(Organization record);

    Organization selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Organization record);

    int updateByPrimaryKey(Organization record);

    List<Organization> findOrgs(Page<Organization> page);

    @Select("select * from T_ORGANIZATION")
    List<Organization> findAll();
}