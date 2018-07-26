package com.dafy.octopus.auth.mapper;

import com.dafy.octopus.web.core.dto.App;

public interface AppMapper {
    int deleteByPrimaryKey(Long id);

    int insert(App record);

    int insertSelective(App record);

    App selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(App record);

    int updateByPrimaryKey(App record);
}