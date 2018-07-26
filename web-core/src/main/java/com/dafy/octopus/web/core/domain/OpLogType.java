package com.dafy.octopus.web.core.domain;

import com.dafy.common.util.JsonUtils;
import com.dafy.common.util.StringUtils;

/**
 * 操作类型
 * Created by liaoxudong
 * Date:2018/5/10
 */

public enum OpLogType {
    LOGIN("1","登录"),
    LOGOUT("2","退出登录"),
    ADD_USER("10","增加用户"),
    EDIT_USER("11","编辑用户"),
    DEL_USER("12","删除用户"),
    CHANGE_PWD("13","修改密码"),
    ENABLE_USER("14","启用/禁用用户"),
    ADD_ORG("21","增加部门"),
    EDIT_ORG("22","编辑部门"),
    DEL_ORG("23","删除部门"),
    ADD_ROLE("31","增加角色"),
    EDIT_ROLE("32","编辑角色"),
    DEL_ROLE("33","删除角色"),

    OTHER("99","其它")
    ;

    private String code;
    private String desc;

    OpLogType(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    @Override
    public String toString() {
        return JsonUtils.toJson(this);
    }

    public static OpLogType parse(String opType) {
        if (StringUtils.isEmpty(opType)) {
            return OTHER;
        }
        OpLogType[] values = values();
        for (OpLogType value : values) {
            if (value.code.equals(opType)) {
                return value;
            }
        }
        return OTHER;
    }
}
