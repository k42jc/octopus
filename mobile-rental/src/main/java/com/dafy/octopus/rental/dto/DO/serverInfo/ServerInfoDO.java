package com.dafy.octopus.rental.dto.DO.serverInfo;

import com.dafy.octopus.rental.dto.DO.BaseDO;
import io.swagger.annotations.ApiModelProperty;

/**
 * 客服服务记录
 */
public class ServerInfoDO extends BaseDO<Long>{
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
    @ApiModelProperty(value = "客服工号")
    private String workerCode;

    public String getWorkerCode() {
        return workerCode;
    }

    public void setWorkerCode(String workerCode) {
        this.workerCode = workerCode;
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

    public String getHandlerId() {
        return handlerId;
    }

    public void setHandlerId(String handlerId) {
        this.handlerId = handlerId;
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
