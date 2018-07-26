package com.dafy.octopus.auth.mapper;

import com.dafy.dal.page.po.Page;
import com.dafy.octopus.web.core.dto.Permission;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Permission record);

    int insertSelective(Permission record);

    Permission selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Permission record);

    int updateByPrimaryKey(Permission record);

    List<Permission> getUserPermissions(Long userId);

    List<Permission> findAllPage(Page map);

    List<Permission> findAll();
}