package com.dafy.octopus.worker.order.exception;

import com.dafy.common.constant.Constants;
import com.dafy.common.exception.ServerException;
import com.dafy.common.po.Response;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


/**
 * 统一异常处理
 * 统一返回json数据
 *
 * 20180411
 * http状态码统一更改为返回200
 * 细化异常分类，某些异常采用http对应的状态码
 * Created by liaoxudong
 * Date:2017/10/27
 */

@ControllerAdvice
public class ExceptionHandlerCenter {
    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandlerCenter.class);
    /**
     * 自定义异常处理
     *
     * @param serverException
     * @return
     */
    @ExceptionHandler(ServerException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Response handleException(ServerException serverException) {
        logger.warn("自定义异常处理:{}", serverException);
        Response resp = new Response();
        resp.setCode(serverException.getCode());
        resp.setMsg(serverException.getDesc());
        return resp;
    }

    /**
     * shiro授权失败
     * @param e
     * @return
     */
    @ExceptionHandler(AuthorizationException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Response handleException(AuthorizationException e) {
        logger.warn("授权失败：",e);
        Response resp = new Response(HttpStatus.FORBIDDEN.value(),"无权限操作");
        return resp;
    }

    /**
     * shiro认证失败
     * @param e
     * @return
     */
    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Response handleException(AuthenticationException e) {
        logger.warn("登录信息校验失败：",e);
        Response resp;
        String errorMsg = e.getMessage();
        if (errorMsg != null && errorMsg.contains(":")) {
            String[] split = errorMsg.split(":");
            resp = new Response(split[0],split[1]);
        }else{
            resp = new Response(HttpStatus.UNAUTHORIZED.value(),"用户名或密码错误");
        }

        return resp;
    }

    /**
     * shiro认证失败
     * @param e
     * @return
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Response handleException(HttpMessageNotReadableException e) {
        logger.warn("请求参数失败：",e);
        Response resp = new Response(HttpStatus.PRECONDITION_FAILED.value(),"请求参数失败，请检查");
        return resp;
    }

    /**
     * 不支持当前方法
     * @param e
     * @return
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Response handleException(HttpRequestMethodNotSupportedException e) {
        logger.warn("请求方法错误：",e);
        Response resp = new Response(HttpStatus.METHOD_NOT_ALLOWED.value(),e.getMessage());
        return resp;
    }

    /**
     * 其它异常处理
     *
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Response handleException(Exception exception) {
        logger.error("服务器异常："+exception.getMessage(), exception);
        Response resp = new Response();
        resp.setCode(Constants.ERROR);
        resp.setMsg(exception.getMessage());
        return resp;
    }

}
