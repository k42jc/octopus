package com.dafy.octopus.auth.mapper;


import com.dafy.dal.page.po.Page;
import com.dafy.octopus.web.core.dto.Role;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface RoleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

//    @Select("select r.* from T_ROLE r inner join T_USER_ROLE ur on r.id=ur.role_id where ur.user_id=#{userId}")
    List<Role> getRoleByUserId(@Param("userId") Long userId);

    List<Role> getAllRoles();

    List<Role> findRoleInfos(Map<String, Object> map);
    List<Role> findRoles(Page<Role> page);
}