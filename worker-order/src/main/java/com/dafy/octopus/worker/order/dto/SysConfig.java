package com.dafy.octopus.worker.order.dto;

import java.io.Serializable;
import java.util.Date;

public class SysConfig implements Serializable{
    private static final long serialVersionUID = -656470703292698259L;
    private Long id;

    private Long pid;

    private String type;

    private String code;

    private String name;

    private String order;

    private String status;

    private String desc;

    private Date createTime;

    public SysConfig() {

    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public SysConfig(String type, String name, String order, String status) {
        this.type = type;
        this.name = name;
        this.order = order;
        this.status = status;
    }

    public SysConfig(Long id, String type, String name, String order, String status) {
        this.type = type;
        this.id = id;
        this.name = name;
        this.order = order;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order == null ? null : order.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc == null ? null : desc.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}