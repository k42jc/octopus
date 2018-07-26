package com.dafy.octopus.worker.order.dto;

import java.io.Serializable;
import java.util.Date;

public class BillOpLog implements Serializable {
    private static final long serialVersionUID = 4537772226967092529L;
    private Long id;

    private Long billId;

    private Long dealType;

    private Long userId;

    private Long userDeptId;

    private String nextDeal;

    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBillId() {
        return billId;
    }

    public void setBillId(Long billId) {
        this.billId = billId;
    }

    public Long getDealType() {
        return dealType;
    }

    public void setDealType(Long dealType) {
        this.dealType = dealType;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUserDeptId() {
        return userDeptId;
    }

    public void setUserDeptId(Long userDeptId) {
        this.userDeptId = userDeptId;
    }

    public String getNextDeal() {
        return nextDeal;
    }

    public void setNextDeal(String nextDeal) {
        this.nextDeal = nextDeal == null ? null : nextDeal.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}