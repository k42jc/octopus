package com.dafy.octopus.rental.dto.VO.serverInfo;

import com.dafy.octopus.rental.dto.VO.BaseVO;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

public class ServerInfoVO extends BaseVO<Long> {
    @ApiModelProperty(value = "来源类型名称")
    private String sourceTypeName;
    @ApiModelProperty(value = "来源类型ID")
    private Integer sourceTypeId;
    @ApiModelProperty(value = "产品类型名称")
    private String productTypeName;
    @ApiModelProperty(value = "产品类型ID")
    private Integer productTypeId;
    @ApiModelProperty(value = "商品类型名称")
    private String goodsTypeName;
    @ApiModelProperty(value = "商品类型ID")
    private Long goodsTypeId;
    @ApiModelProperty(value = "客服处理记录")
    private String dealRecordContent;
    @ApiModelProperty(value = "处理状态名称")
    private String dealStatusName;
    @ApiModelProperty(value = "处理状态ID")
    private Integer dealStatusId;
    @ApiModelProperty(value = "用户ID")
    private String userId;
    @ApiModelProperty(value = "管理员ID/客服ID")
    private String handlerId;
    @ApiModelProperty(value = "案例编号")
    private String serverCode;
    @ApiModelProperty(value = "工单编号")
    private String serverOrderCode;
    @ApiModelProperty(value = "商品列表")
    private List<ServerInfoGoodsTypeVO> goodsTypeList;
    @ApiModelProperty(value = "创建时间", hidden = true)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createDate;
    @ApiModelProperty(value = "客服工号")
    private String workerCode;

    public String getWorkerCode() {
        return workerCode;
    }

    public void setWorkerCode(String workerCode) {
        this.workerCode = workerCode;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public List<ServerInfoGoodsTypeVO> getGoodsTypeList() {
        return goodsTypeList;
    }

    public void setGoodsTypeList(List<ServerInfoGoodsTypeVO> goodsTypeList) {
        this.goodsTypeList = goodsTypeList;
    }

    public String getHandlerId() {
        return handlerId;
    }

    public void setHandlerId(String handlerId) {
        this.handlerId = handlerId;
    }

    public String getServerCode() {
        return serverCode;
    }

    public void setServerCode(String serverCode) {
        this.serverCode = serverCode;
    }

    public String getServerOrderCode() {
        return serverOrderCode;
    }

    public void setServerOrderCode(String serverOrderCode) {
        this.serverOrderCode = serverOrderCode;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSourceTypeName() {
        return sourceTypeName;
    }

    public void setSourceTypeName(String sourceTypeName) {
        this.sourceTypeName = sourceTypeName;
    }

    public Integer getSourceTypeId() {
        return sourceTypeId;
    }

    public void setSourceTypeId(Integer sourceTypeId) {
        this.sourceTypeId = sourceTypeId;
    }

    public String getProductTypeName() {
        return productTypeName;
    }

    public void setProductTypeName(String productTypeName) {
        this.productTypeName = productTypeName;
    }

    public Integer getProductTypeId() {
        return productTypeId;
    }

    public void setProductTypeId(Integer productTypeId) {
        this.productTypeId = productTypeId;
    }

    public String getGoodsTypeName() {
        return goodsTypeName;
    }

    public void setGoodsTypeName(String goodsTypeName) {
        this.goodsTypeName = goodsTypeName;
    }

    public Long getGoodsTypeId() {
        return goodsTypeId;
    }

    public void setGoodsTypeId(Long goodsTypeId) {
        this.goodsTypeId = goodsTypeId;
    }

    public String getDealRecordContent() {
        return dealRecordContent;
    }

    public void setDealRecordContent(String dealRecordContent) {
        this.dealRecordContent = dealRecordContent;
    }

    public String getDealStatusName() {
        return dealStatusName;
    }

    public void setDealStatusName(String dealStatusName) {
        this.dealStatusName = dealStatusName;
    }

    public Integer getDealStatusId() {
        return dealStatusId;
    }

    public void setDealStatusId(Integer dealStatusId) {
        this.dealStatusId = dealStatusId;
    }
}
