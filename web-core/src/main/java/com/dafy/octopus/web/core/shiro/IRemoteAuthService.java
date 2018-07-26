package com.dafy.octopus.web.core.shiro;

import com.dafy.octopus.web.core.domain.RolePermissionContext;

import java.io.Serializable;

/**
 * 认证接口
 * Created by liaoxudong
 * Date:2018/4/16
 */

@Deprecated
public interface IRemoteAuthService<T> {
    T getSession(String appKey, Serializable sessionId);
    Serializable createSession(String appKey,T session);
    void updateSession(String appKey, T session);
    void deleteSession(String appKey, T session);
    RolePermissionContext getPermissions(String appKey, String userName);
}
