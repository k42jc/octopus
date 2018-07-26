package com.dafy.octopus.auth.mapper;

import com.dafy.dal.page.po.Page;
import com.dafy.dal.cache.RedisMybatisCache;
import com.dafy.octopus.web.core.dto.User;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
@CacheNamespace(implementation = RedisMybatisCache.class)
public interface UserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(User record);

    long insertSelective(User record);

    User selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    /**
     * 根据用户名、邮箱、电话号码查找对应用户
     * @param userName
     * @return
     */
    User findUser(@Param("userName") String userName);

    List<User> findUsers(Map<String, Object> map);

    List<User> findUserInfos(Page<User> user);

    @Update("update T_USER set last_login = #{date} where user_code = #{userCode}")
    void updateLastLoginTime(@Param("date") Date date,@Param("userCode") String userCode);
    @Select("select * from T_USER")
    List<User> findAll();
}