package com.dafy.octopus.auth.mapper;

import com.dafy.octopus.web.core.dto.RolePermission;
import com.dafy.dal.cache.RedisMybatisCache;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@CacheNamespace(implementation = RedisMybatisCache.class)
public interface RolePermissionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(RolePermission record);

    int inserts(@Param("records") List<RolePermission> records);

    int insertSelective(RolePermission record);

    RolePermission selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RolePermission record);

    int updateByPrimaryKey(RolePermission record);

    @Delete("delete from T_ROLE_PERMISSION where role_id = #{roleId}")
    void deleteByRoleId(@Param("roleId") Long id);
}