package com.dafy.octopus.rental.dto.VO.systemConfig;

import com.dafy.octopus.rental.dto.VO.BaseVO;

import java.util.List;

public class GoodsTypeVO extends BaseVO<Long>{
    //父ID，一级目录的parentId为0
    private Long parentId;
    //原名称
    private String srcName;
    //当前名称
    private String curName;

    private List<GoodsTypeVO> childList;

    public List<GoodsTypeVO> getChildList() {
        return childList;
    }

    public void setChildList(List<GoodsTypeVO> childList) {
        this.childList = childList;
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
