package com.dafy.octopus.web.core.dto;


import com.dafy.common.po.Tree;

import java.io.Serializable;
import java.util.Date;

/**
 * 菜单对象
 */
public class Menu extends Tree<Menu> implements Serializable{

    private static final long serialVersionUID = -5355101492941026333L;
    private String mCode;

    private String mName;

    private String mIcon;

    private String mUrl;

    private Integer mOrder;

    private Date createTime;

    private Date updateTime;

    private String desc;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }


    public String getmCode() {
        return mCode;
    }

    public void setmCode(String mCode) {
        this.mCode = mCode == null ? null : mCode.trim();
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName == null ? null : mName.trim();
    }

    public String getmIcon() {
        return mIcon;
    }

    public void setmIcon(String mIcon) {
        this.mIcon = mIcon == null ? null : mIcon.trim();
    }

    public String getmUrl() {
        return mUrl;
    }

    public void setmUrl(String mUrl) {
        this.mUrl = mUrl == null ? null : mUrl.trim();
    }

    public Integer getmOrder() {
        return mOrder;
    }

    public void setmOrder(Integer mOrder) {
        this.mOrder = mOrder;
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

}