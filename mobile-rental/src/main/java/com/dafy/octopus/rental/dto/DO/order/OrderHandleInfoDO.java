package com.dafy.octopus.rental.dto.DO.order;

import com.dafy.octopus.rental.dto.DO.BaseDO;
import io.swagger.annotations.ApiModelProperty;

public class OrderHandleInfoDO extends BaseDO<Long> {
    @ApiModelProperty(value = "用户姓名")
    private String userName;
    @ApiModelProperty(value = "订单ID")
    private String orderId;
    @ApiModelProperty(value = "用户手机号")
    private String userPhone;
    @ApiModelProperty(value = "客服工号")
    private String workerCode;
    @ApiModelProperty(value = "客服ID")
    private String handlerId;
    @ApiModelProperty(value = "操作内容/操作类型", hidden = true)
    private String handleTypeName;
    @ApiModelProperty(value = "操作原因")
    private String handleReason;
    @ApiModelProperty(value = "ip地址", hidden = true)
    private String handleIpAddress;
    @ApiModelProperty(value = "用户ID")
    private String userId;


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
