package com.dafy.octopus.rental.dto.DO.systemConfig;

import com.dafy.octopus.rental.dto.DO.BaseDO;

public class GoodsTypeDO extends BaseDO<Long> {
    //父ID，一级目录的parentId为0
    private Long parentId;
    //原名称
    private String srcName;
    //当前名称
    private String curName;

    public GoodsTypeDO(Long parentId, String curName) {
        this.parentId = parentId;
        this.curName = curName;
    }

    public GoodsTypeDO(String curName, Long id) {
        this.curName = curName;
        super.setId(id);
    }

    public GoodsTypeDO() {
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getSrcName() {
        return srcName;
    }

    public void setSrcName(String srcName) {
        this.srcName = srcName;
    }

    public String getCurName() {
        return curName;
    }

    public void setCurName(String curName) {
        this.curName = curName;
    }
}
