package com.dafy.octopus.web.core.aop;

import com.dafy.octopus.web.core.domain.OpLogType;

import java.lang.annotation.*;

/**
 * 操作日志拦截实现
 * Created by liaoxudong
 * Date:2018/5/10
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OpLogRecord {

    OpLogType value();// 需要指定操作类型，且需要在方法参数的首个参数指定HttpServerRequest
}
