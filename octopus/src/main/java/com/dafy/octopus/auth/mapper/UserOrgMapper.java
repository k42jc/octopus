package com.dafy.octopus.auth.mapper;

import com.dafy.octopus.web.core.dto.UserOrg;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserOrgMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserOrg record);

    int insertSelective(UserOrg record);

    UserOrg selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserOrg record);

    int updateByPrimaryKey(UserOrg record);

    @Update("update T_USER_ORGANIZATION set org_id = #{orgId} WHERE user_id = #{userId}")
    void updateByUserId(UserOrg userOrg);

    @Select("select * from T_USER_ORGANIZATION where org_id = #{orgId}")
    List<UserOrg> selectByOrgId(@Param("orgId") long orgId);

    @Delete("delete from T_USER_ORGANIZATION where user_id = #{userId}")
    void deleteByUserId(@Param("userId") Long id);
}