package com.dafy.octopus.auth.aop;

import java.lang.annotation.*;

/**
 * 拦截编辑、删除管理员信息，禁止操作
 * Created by liaoxudong
 * Date:2018/5/16
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DisableEditAdmin {
    EditType value() default EditType.USER;// user/role

    enum EditType{// 类型 区分用户管理员与管理员角色
        USER,ROLE
    }
}
