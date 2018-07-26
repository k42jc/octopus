package com.dafy.octopus.web.core.dto;

import com.dafy.octopus.web.core.domain.OpLogType;

import java.io.Serializable;
import java.util.Date;

public class OpLog implements Serializable{
    private static final long serialVersionUID = -9005399569161984530L;
    private Long id;

    private String opNo;

    private Long userId;

    private String opType;

    private String opIp;

    private Date createTime;

    private Date updateTime;

    private String desc;

    public Long getUserId() {
        return userId;
    }

    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOpNo() {
        return opNo;
    }

    public void setOpNo(String opNo) {
        this.opNo = opNo == null ? null : opNo.trim();
    }

    public String getOpTypeDesc() {
        return OpLogType.parse(opType).getDesc();
    }

    public String getOpType(){
        return this.opType;
    }

    public void setOpType(String opType) {
        this.opType = opType == null ? null : opType.trim();
    }

    public String getOpIp() {
        return opIp;
    }

    public void setOpIp(String opIp) {
        this.opIp = opIp == null ? null : opIp.trim();
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc == null ? null : desc.trim();
    }
}