package com.dafy.octopus.web.core.shiro.realm;

import com.dafy.octopus.web.core.domain.RolePermissionContext;
import com.dafy.octopus.web.core.shiro.session.RedisSessionDao;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * 当前授权认证器
 *
 * 使用redis中央缓存实现分布式权限控制
 *
 * 用于扩展项目的公用授权认证
 */
public class PluginAuthorizingRealm extends AuthorizingRealm {
    private String appKey;

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = (String) principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
//        RolePermissionContext context = remoteAuthService.getPermissions(appKey, username);
        RolePermissionContext context = RedisSessionDao.getRolePermission(principals);
                authorizationInfo.setRoles(context.getRoles());
        authorizationInfo.setStringPermissions(context.getPermissions());
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //do nothing 只有授权服务器才有登录操作
        throw new IllegalStateException();
    }
}
