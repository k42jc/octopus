package com.dafy.octopus.rental.dto.VO.systemConfig;

import com.dafy.octopus.rental.dto.VO.BaseVO;

public class ProductTypeVO extends BaseVO<Integer> {
    //产品名称
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
