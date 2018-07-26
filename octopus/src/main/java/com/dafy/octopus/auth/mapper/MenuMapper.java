package com.dafy.octopus.auth.mapper;

import com.dafy.dal.page.po.Page;
import com.dafy.octopus.web.core.dto.Menu;
import com.dafy.dal.cache.RedisMybatisCache;
import org.apache.ibatis.annotations.CacheNamespace;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@CacheNamespace(implementation = RedisMybatisCache.class)
public interface MenuMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Menu record);

    int insertSelective(Menu record);

    Menu selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Menu record);

    int updateByPrimaryKey(Menu record);

    List<Menu> getAllMenus();

    List<Menu> getAllMenus(Page map);

    List<Menu> getUserMenus(Long userId);
}