package com.dafy.octopus.rental.dto.DO.systemConfig;

import com.dafy.octopus.rental.dto.DO.BaseDO;

import java.util.Date;

//产品类型
public class ProductTypeDO extends BaseDO<Integer> {
    //产品名称
    private String name;

    public ProductTypeDO() {
    }

    public ProductTypeDO(String name, Date createDate) {
        this.name = name;
        super.setCreateDate(createDate);
    }

    public ProductTypeDO(String name, Integer id) {
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
