package com.dafy.octopus.rental.dto.DO;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

public class BaseDO<T>  implements Serializable {
    protected static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id",hidden = true)
    private T id;
    @ApiModelProperty(value = "创建时间", hidden = true)
    private Date createDate;
    @ApiModelProperty(value = "更新时间", hidden = true)
    private Date updateDate;

    public T getId() {
        return id;
    }

    public void setId(T id) {
        this.id = id;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

}
