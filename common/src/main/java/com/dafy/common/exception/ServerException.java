package com.dafy.common.exception;

/**
 * 服务器异常
 * Created by liaoxudong
 * Date:2017/10/27
 */
public class ServerException extends RuntimeException {
    private String code;
    private String desc;

    public ServerException(){}

    public ServerException(String code, String desc) {
        super(code + ":" + desc);
        this.code = code;
        this.desc = desc;
    }

    public ServerException(ExceptionCode exceptionCode) {
        super(exceptionCode.getCode() + ":" + exceptionCode.getDesc());
        this.code = exceptionCode.getCode();
        this.desc = exceptionCode.getDesc();
    }

    public ServerException(ExceptionCode exceptionCode, Throwable cause) {
        super(exceptionCode.getCode() + ":" + exceptionCode.getDesc(), cause);
        this.code = exceptionCode.getCode();
        this.desc = exceptionCode.getDesc();
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

    @Override
    public String toString() {
        return "【"+this.getCode() + ":" + this.getDesc()+ "】";
    }
}
