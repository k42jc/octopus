package com.dafy.octopus.rental.dto.DO.systemConfig;

import com.dafy.octopus.rental.dto.DO.BaseDO;

import java.util.Date;

//客服记录来源类型
public class SourceTypeDO extends BaseDO<Integer> {
    private String name;

    public SourceTypeDO() {
    }

    public SourceTypeDO(String name, Date createDate) {
        this.name = name;
        super.setCreateDate(createDate);
    }

    public SourceTypeDO(String name, Integer id) {
        this.name = name;
        super.setId(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
