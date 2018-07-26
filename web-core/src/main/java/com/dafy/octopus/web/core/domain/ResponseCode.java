package com.dafy.octopus.web.core.domain;

/**
 * Created by liaoxudong
 * Date:2018/1/30
 */

public enum ResponseCode {
    SUCCESS("00","成功"),
//    LOGIN_SUCCESS("10","登录成功"),
    LOGIN_ERROR("11","用户名或密码错误"),
    LOGIN_EXPIRE("12","登录已过期，请重新登录"),

    PWD_RETRY_TIMES("13","用户名或密码错误，剩余重试次数：%d"),
    PWD_ERROR_OUTOF_LIMIT("14","账户已被锁定，请%d分钟后重试"),
    USER_NOT_EXISTS("15","用户不存在"),
    USER_NOT_AVALIBLE("16","用户未启用"),

    PWD_CHANGE_FAILURE_ERROR("20","修改密码失败，原密码不匹配"),


    ROLE_EXIST_USER("30","当前角色存在绑定用户，不允许删除"),
    ORG_EXIST_USER("31","当前部门存在绑定用户，不允许删除"),

    ROLE_PERMISSION_CHANGED("12","当前角色权限被修改，请重新登录"),

    DISABLE_EDIT_ADMIN("40", "禁止删除/编辑管理员信息"),

    NO_PERMISSION("41", "无权限"),

    ;

    private String code;
    private String desc;

    ResponseCode(String code, String desc) {
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


    @Override
    public String toString() {
        return this.getCode() + ":" + this.getDesc();
    }
}
