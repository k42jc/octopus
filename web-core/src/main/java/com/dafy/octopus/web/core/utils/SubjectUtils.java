package com.dafy.octopus.web.core.utils;

import com.dafy.common.util.JsonUtils;
import com.dafy.common.util.StringUtils;
import com.dafy.octopus.web.core.domain.Constants;
import com.dafy.octopus.web.core.domain.RolePermissionContext;
import com.dafy.octopus.web.core.dto.User;
import com.dafy.octopus.web.core.shiro.session.RedisSessionDao;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * 获取当前登录用户
 * Created by liaoxudong
 * Date:2018/5/11
 */

public class SubjectUtils {

    /**
     * 从会话中获取当前登录的用户
     * @return
     */
    public static User getCurrentUser(){
        //从session获取用户信息
        Session session = SecurityUtils.getSubject().getSession();
        PrincipalCollection principals = SecurityUtils.getSubject().getPrincipals();
        Object user = session.getAttribute(Constants.USER_INFO_SESSION_KEY+ SecurityUtils.getSubject().getPrincipal());
        if (user == null) {
            return null;
        }
        User userInfo = JsonUtils.fromJson(user.toString(), User.class);
        return userInfo;
    }

    /**
     * 登出
     */
    public static void logout(){
        SecurityUtils.getSubject().logout();
    }

    /**
     * 当前登录用户是否存在某个权限，多个权限使用||或者&&分隔，分别表示或/并
     * @param permissionCode
     * @return
     */
    private static final String OR = "||";
    private static final String AND = "&&";
    public static boolean hasPermissions(String permissionCode) {
        if (StringUtils.isEmpty(permissionCode)) {
            return true;
        }
        // 获取当前用户存在的所有权限
        PrincipalCollection principals = SecurityUtils.getSubject().getPrincipals();
        RolePermissionContext context = RedisSessionDao.getRolePermission(principals);
        Set<String> ownPermissions = context.getPermissions();
        if (permissionCode.contains(OR)) {// 多个权限包含其一
            String[] permissions = permissionCode.split(OR);
            for (String permission : permissions) {
                if (ownPermissions.contains(permission)) {// 拥有一个权限即可
                    return true;
                }
            }
        } else if (permissionCode.contains(AND)) {// 满足所有权限
            String[] permissions = permissionCode.split(AND);
            List<String> strings = Arrays.asList(permissions);
            if(ownPermissions.containsAll(strings)){
                return true;
            }
        } else if(ownPermissions.contains(permissionCode)) {// 单独的一个权限
            return true;
        }
        return false;
    }
}
