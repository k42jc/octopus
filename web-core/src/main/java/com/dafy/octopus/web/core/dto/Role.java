package com.dafy.octopus.web.core.dto;

import com.dafy.common.util.JsonUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Role implements Serializable{
    private static final long serialVersionUID = 7169958626429085236L;
    private Long id;

    private String code;

    private String roleName;

    private String status;

    private String desc;

//    private List<Long> existsPermissons = new ArrayList<>();

    private List<User> userList;

    private List<Permission> permissionList;

    private int userCount;

    private Date createTime;

    private Date updateTime;

    private String createTimeStr;
    private String updateTimeStr;

    private String permissions;

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }

    public String getCreateTimeStr() {
        return createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }

    public String getUpdateTimeStr() {
        return updateTimeStr;
    }

    public void setUpdateTimeStr(String updateTimeStr) {
        this.updateTimeStr = updateTimeStr;
    }

    public int getUserCount() {
        return userCount;
    }

    public void setUserCount(int userCount) {
        this.userCount = userCount;
    }

    //    public List<Long> getExistsPermissons() {
//        return existsPermissons;
//    }

//    public void setExistsPermissons(List<Long> existsPermissons) {
//        this.existsPermissons = existsPermissons;
//    }

//    public void addExistsPermisson(Long pId) {
//        this.existsPermissons.add(pId);
//    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public List<Permission> getPermissionList() {
        return permissionList;
    }

    public void setPermissionList(List<Permission> permissionList) {
        this.permissionList = permissionList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
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

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return JsonUtils.toJson(this);
    }
}