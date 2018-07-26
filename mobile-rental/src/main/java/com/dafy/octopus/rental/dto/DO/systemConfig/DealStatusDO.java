package com.dafy.octopus.rental.dto.DO.systemConfig;

import com.dafy.octopus.rental.dto.DO.BaseDO;

import java.util.Date;

//解决状态类型
public class DealStatusDO extends BaseDO<Integer> {
    private String name;

    public DealStatusDO() {
    }

    public DealStatusDO(String name, Date createDate) {
        this.name = name;
        super.setCreateDate(createDate);
    }

    public DealStatusDO(String name, Integer id) {
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
