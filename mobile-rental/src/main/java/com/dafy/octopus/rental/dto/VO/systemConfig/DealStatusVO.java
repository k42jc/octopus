package com.dafy.octopus.rental.dto.VO.systemConfig;

import com.dafy.octopus.rental.dto.VO.BaseVO;

public class DealStatusVO extends BaseVO<Integer> {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
