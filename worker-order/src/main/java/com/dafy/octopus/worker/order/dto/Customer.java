package com.dafy.octopus.worker.order.dto;

import java.io.Serializable;
import java.util.Date;

public class Customer  implements Serializable {
    private static final long serialVersionUID = 7159539815281411299L;
    private Long id;

    private String name;

    private Integer sex;

    private String phoneno;

    private String connectUser;

    private String connectUserPhone;

    private Date callTime;

    private String desc;

    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno == null ? null : phoneno.trim();
    }

    public String getConnectUser() {
        return connectUser;
    }

    public void setConnectUser(String connectUser) {
        this.connectUser = connectUser == null ? null : connectUser.trim();
    }

    public String getConnectUserPhone() {
        return connectUserPhone;
    }

    public void setConnectUserPhone(String connectUserPhone) {
        this.connectUserPhone = connectUserPhone == null ? null : connectUserPhone.trim();
    }

    public Date getCallTime() {
        return callTime;
    }

    public void setCallTime(Date callTime) {
        this.callTime = callTime;
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