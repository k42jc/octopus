package com.dafy.octopus.web.core.domain;

import com.dafy.common.util.JsonUtils;

import java.io.Serializable;
import java.util.Set;

/**
 * 认证权限上下文
 *
 * 维护对应的角色与权限信息
 *
 * Created by liaoxudong
 * Date:2018/4/16
 */

public class RolePermissionContext implements Serializable {
    private Set<String> roles;
    private Set<String> permissions;

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public Set<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<String> permissions) {
        this.permissions = permissions;
    }


    @Override
    public String toString() {
        return JsonUtils.toJson(this);
    }
}