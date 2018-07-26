package com.dafy.octopus.worker.order.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 工单处理信息
 */
public class BillDealInfo implements Serializable {
    private static final long serialVersionUID = 6558403528329001245L;
    private Long id;

    private Long billId;

    private Long userId;

    private Long userDeptId;

    private String desc;
    // 问题定性 1 小问题 2 中问题 3 大问题
    private Long problem;

    private Long subProblem;

    private String result;

    private String reVisit;

    private Date reVisitTime;

    private String usedTime;

    private String attachUrl;

    private Date createTime;

    private Date updateTime;

    private String status;

    public Long getSubProblem() {
        return subProblem;
    }

    public void setSubProblem(Long subProblem) {
        this.subProblem = subProblem;
    }

    public Long getProblem() {
        return problem;
    }

    public void setProblem(Long problem) {
        this.problem = problem;
    }

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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc == null ? null : desc.trim();
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result == null ? null : result.trim();
    }

    public String getReVisit() {
        return reVisit;
    }

    public void setReVisit(String reVisit) {
        this.reVisit = reVisit == null ? null : reVisit.trim();
    }

    public Date getReVisitTime() {
        return reVisitTime;
    }

    public void setReVisitTime(Date reVisitTime) {
        this.reVisitTime = reVisitTime;
    }

    public String getUsedTime() {
        return usedTime;
    }

    public void setUsedTime(String usedTime) {
        this.usedTime = usedTime == null ? null : usedTime.trim();
    }

    public String getAttachUrl() {
        return attachUrl;
    }

    public void setAttachUrl(String attachUrl) {
        this.attachUrl = attachUrl == null ? null : attachUrl.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }
}