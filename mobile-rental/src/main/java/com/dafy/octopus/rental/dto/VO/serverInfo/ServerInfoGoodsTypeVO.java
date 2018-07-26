package com.dafy.octopus.rental.dto.VO.serverInfo;

import com.dafy.octopus.rental.dto.VO.BaseVO;

public class ServerInfoGoodsTypeVO extends BaseVO<Long>{
    //商品ID
    private Long goodsId;
    //商品名称
    private String name;
    //商品分级序列
    private Integer levelIndex;
    //客户服务ID
    private Long serverInfoId;

    public Long getServerInfoId() {
        return serverInfoId;
    }

    public void setServerInfoId(Long serverInfoId) {
        this.serverInfoId = serverInfoId;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLevelIndex() {
        return levelIndex;
    }

    public void setLevelIndex(Integer levelIndex) {
        this.levelIndex = levelIndex;
    }
}
