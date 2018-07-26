package com.dafy.octopus.rental.dto.VO.order;

import com.dafy.octopus.rental.dto.VO.BaseVO;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class OrderHandleInfoVO extends BaseVO<Long>{
    @ApiModelProperty(value = "用户姓名")
    private String userName;
    @ApiModelProperty(value = "用户手机号")
    private String userPhone;
    @ApiModelProperty(value = "客服工号")
    private String workerCode;
    @ApiModelProperty(value = "客服ID")
    private String handlerId;
    @ApiModelProperty(value = "操作内容/操作类型")
    private String handleTypeName;
    @ApiModelProperty(value = "操作原因")
    private String handleReason;
    @ApiModelProperty(value = "ip地址")
    private String handleIpAddress;
    @ApiModelProperty(value = "订单ID")
    private String orderId;
    @ApiModelProperty(value = "用户ID")
    private String userId;
    @ApiModelProperty(value = "创建时间", hidden = true)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getWorkerCode() {
        return workerCode;
    }

    public void setWorkerCode(String workerCode) {
        this.workerCode = workerCode;
    }

    public String getHandlerId() {
        return handlerId;
    }

    public void setHandlerId(String handlerId) {
        this.handlerId = handlerId;
    }

    public String getHandleTypeName() {
        return handleTypeName;
    }

    public void setHandleTypeName(String handleTypeName) {
        this.handleTypeName = handleTypeName;
    }

    public String getHandleReason() {
        return handleReason;
    }

    public void setHandleReason(String handleReason) {
        this.handleReason = handleReason;
    }

    public String getHandleIpAddress() {
        return handleIpAddress;
    }

    public void setHandleIpAddress(String handleIpAddress) {
        this.handleIpAddress = handleIpAddress;
    }
}
