package com.dafy.common.po;


import com.dafy.common.exception.ServerException;
import com.dafy.common.constant.Constants;
import com.dafy.common.exception.ExceptionCode;
import com.dafy.common.util.JsonUtils;

/**
 * 通用返回对象
 * Created by liaoxudong
 * Date:2017/10/27
 */

public class Response<T>{


    // 返回码
    private String code;
    // 返回描述
    private String msg;
    // 内部数据 改为枚举约束
    private T data;

    public Response() {// 默认返回成功
        this.code = Constants.SUCCESS;
        this.msg = Constants.SUCCESS_MSG;
    }

    public Response(Object code, Object msg) {
        this.code = String.valueOf(code);
        this.msg = String.valueOf(msg);
    }

    public Response(Exception exception){
        if (exception instanceof ServerException){
            this.setCode(((ServerException) exception).getCode());
            this.setMsg(((ServerException) exception).getDesc());
        }else if (exception.getMessage() != null && exception.getMessage().startsWith(ServerException.class.getName())){
            String message = exception.getMessage().split("\\n")[0];
            String[] data = message.split(":");
            if (data.length < 3){
                //未知异常
                setCode(ExceptionCode.SYSTEM_ERROR.getCode());
                setMsg(ExceptionCode.SYSTEM_ERROR.getDesc());
            }else {
                setCode(data[1]);
                setMsg(message.substring(message.indexOf(":", ServerException.class.getName().length() + 1) + 1));
            }

        }else {
            setCode(ExceptionCode.SYSTEM_ERROR.getCode());
            setMsg(ExceptionCode.SYSTEM_ERROR.getDesc());
        }

    }



    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
//        return ReflectionToStringBuilder.toString(this);
//        return "【code:" + this.getCode()+",msg:"+this.getMsg()+",data:"+this.getData()+"】";
        return JsonUtils.toJson(this);
    }

}
