package com.dafy.octopus.rental.dto.VO;

import com.dafy.octopus.rental.common.JsonMapper;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class BaseVO<T> implements Serializable {
    protected static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    private T id;

    public BaseVO() {
    }

    public BaseVO(T id) {
        this.id = id;
    }

    public T getId() {
        return id;
    }

    public void setId(T id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return new JsonMapper().toJson(this);
    }
}
