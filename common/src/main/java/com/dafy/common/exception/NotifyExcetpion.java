package com.dafy.common.exception;

/**
 * 消息通知类异常
 * Created by liaoxudong
 * Date:2017/11/17
 */

public class NotifyExcetpion extends Exception{

    private String code;
    private String msg;

    public NotifyExcetpion(String code, String msg) {
        super(code+":"+msg);
        this.code = code;
        this.msg = msg;
    }

    public NotifyExcetpion(ExceptionCode exceptionCode) {
        super(exceptionCode.getCode()+":"+ exceptionCode.getDesc());
        this.code = exceptionCode.getCode();
        this.msg = exceptionCode.getDesc();
    }


}
