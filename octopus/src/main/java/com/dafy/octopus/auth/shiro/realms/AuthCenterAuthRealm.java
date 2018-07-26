package com.dafy.octopus.auth.shiro.realms;

import com.dafy.common.util.JsonUtils;
import com.dafy.octopus.web.core.domain.RolePermissionContext;
import com.dafy.octopus.web.core.dto.User;
import com.dafy.octopus.web.core.domain.Constants;
import com.dafy.octopus.auth.service.IUserService;
import com.dafy.octopus.web.core.shiro.session.RedisSessionDao;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import java.util.List;

import static com.dafy.octopus.web.core.domain.ResponseCode.USER_NOT_AVALIBLE;
import static com.dafy.octopus.web.core.domain.ResponseCode.USER_NOT_EXISTS;

/**
 *
 * Created by liaoxudong
 * Date:2018/4/11
 */

public abstract class AuthCenterAuthRealm extends AuthorizingRealm {

    private IUserService userService;

    public AuthCenterAuthRealm(CredentialsMatcher credentialsMatcher) {
        super(credentialsMatcher);
    }

    protected void setUserService(IUserService userService) {
        this.userService = userService;
    }

    /**
     * 授权处理 @RequirePermission()的AOP调用
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
//        Session session = SecurityUtils.getSubject().getSession();
       /* List<Permission> permissionList = (List)session.getAttribute(Constants.USER_PERMISSION_SESSION_KEY);
        List<Role> roleList = (List)session.getAttribute(Constants.USER_ROLE_SESSION_KEY);*/
        RolePermissionContext rolePermissionContext = RedisSessionDao.getRolePermission(principalCollection);
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        /*Set<String> permissions = new HashSet<>();
        rolePermissionContext.getPermissions().forEach(p -> {
            permissions.add(p.getpCode());
        });
        Set<String> roles = new HashSet<>();
        roleList.forEach(p -> {
            roles.add(p.getCode());
        });*/
        authorizationInfo.addRoles(rolePermissionContext.getRoles());
        authorizationInfo.addStringPermissions(rolePermissionContext.getPermissions());
        return authorizationInfo;
    }


    /**
     * 认证处理 subject.login()调用
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String loginName = (String) authenticationToken.getPrincipal();
        // 获取用户密码
//        String password = new String((char[]) authenticationToken.getCredentials());
        User user = userService.getUser(loginName);
        if (user == null) {
            throw new UnknownAccountException(USER_NOT_EXISTS.toString());//用户不存在
        }
        if(0 == user.getStatus()) {
            throw new LockedAccountException(USER_NOT_AVALIBLE.toString()); //账户未启用
        }
        /*if (!password.equals(user.getPwd())) {
            throw new ServerException(ResponseCode.LOGIN_ERROR.getCode(),ResponseCode.LOGIN_ERROR.getDesc());
        }*/
        //交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配，如果觉得人家的不好可以自定义实现
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
//                userNames(user),
                user.getUserCode(),
                user.getPwd(),
                ByteSource.Util.bytes(user.getSalt()), //
                getName()
        );
        //session中不需要保存密码
        user.setPwd("");
        Session session = SecurityUtils.getSubject().getSession();
        session.setAttribute(Constants.USER_INFO_SESSION_KEY + /*JsonUtils.toJson(userNames(user))*/user.getUserCode(), JsonUtils.toJson(user));
        return authenticationInfo;
    }



    protected abstract List<String> userNames(User user);

}
