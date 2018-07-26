package com.dafy.common.exception;

/**
 * 系统异常代码
 * Created by liaoxudong
 * Date:2017/10/27
 */
public enum ExceptionCode {
    SYSTEM_EXCEPTION("error","系统错误"),
    DEFAULT_EXCEPTION("101", "程序异常"),
    PARAM_ERROR("102", "参数错误"),
    SYSTEM_ERROR("110", "系统异常，请联系管理员"),
    DATA_COMPLETE_ERROR("999","数据完整性异常，请联系管理员")
    ;


    private String code;
    private String desc;

    ExceptionCode(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}
