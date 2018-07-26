package com.dafy.octopus.auth.mapper;

import com.dafy.octopus.web.core.dto.UserRole;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface UserRoleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserRole record);

    int insertSelective(UserRole record);

    UserRole selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserRole record);

    int updateByPrimaryKey(UserRole record);

    @Update("update T_USER_ROLE set role_id=#{roleId} where user_id=#{userId}")
    void updateByUserId(UserRole userRole);

    @Delete("delete from T_USER_ROLE where user_id = #{userId}")
    void deleteByUserId(@Param("userId") Long id);

    @Delete("delete from T_USER_ROLE where role_id = #{roleId}")
    void deleteByRoleId(@Param("roleId") Long id);

    @Select("select * from T_USER_ROLE where role_id = #{roleId}")
    List<UserRole> selectByRoleId(@Param("roleId") Long id);

    @Select("select u.* from T_USER u where u.id in(select user_id from T_USER_ROLE where role_id = #{roleId})")
    List<Map<String,Object>>selectUserInfosByRoleId(@Param("roleId") Long id);
}