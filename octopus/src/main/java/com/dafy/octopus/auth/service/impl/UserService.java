package com.dafy.octopus.auth.service.impl;

import com.dafy.cache.factory.CacheFactory;
import com.dafy.common.po.Response;
import com.dafy.common.thread.pool.ThreadPoolFactory;
import com.dafy.common.util.JsonUtils;
import com.dafy.common.util.StringUtils;
import com.dafy.dal.page.po.Page;
import com.dafy.octopus.auth.mapper.OrgMapper;
import com.dafy.octopus.auth.mapper.UserMapper;
import com.dafy.octopus.auth.mapper.UserOrgMapper;
import com.dafy.octopus.auth.mapper.UserRoleMapper;
import com.dafy.octopus.auth.service.IMenuService;
import com.dafy.octopus.auth.service.IPermissionService;
import com.dafy.octopus.auth.service.IRoleService;
import com.dafy.octopus.auth.service.IUserService;
import com.dafy.octopus.auth.shiro.session.CusSessionDao;
import com.dafy.octopus.web.core.context.ApplicationContextProvider;
import com.dafy.octopus.web.core.domain.Constants;
import com.dafy.octopus.web.core.domain.Request;
import com.dafy.octopus.web.core.domain.ResponseCode;
import com.dafy.octopus.web.core.domain.RolePermissionContext;
import com.dafy.octopus.web.core.dto.*;
import com.dafy.octopus.web.core.utils.CommonUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static com.dafy.octopus.web.core.domain.ResponseCode.PWD_CHANGE_FAILURE_ERROR;

/**
 * 登录操作
 * Created by liaoxudong
 * Date:2018/1/30
 */

@Service
public class UserService implements IUserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private IPermissionService permissionService;

    @Autowired
    private IMenuService menuService;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private UserOrgMapper userOrgMapper;

    @Autowired
    private OpLogSevice opLogSevice;

    @Override
    public Response login(Request request) {
        String username = request.getString("username");
        String password = request.getString("password");
        boolean rememberMe = request.getBoolean("rememberMe");
        String host = request.getString("host");
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            return CommonUtils.buildSuccessResp("当前为已登录状态，请无重复登录");
        }
        UsernamePasswordToken token = new UsernamePasswordToken(username,password,rememberMe,host);
//        try{
            subject.login(token);
        /*} catch (AuthenticationException e) {
            logger.error("登录失败：",e);
            CommonUtils.throwException(ResponseCode.LOGIN_ERROR);
        }*/
        // 更新最后登录时间
        userMapper.updateLastLoginTime(new Date(),username);
        String loginToken = StringUtils.getUUID();
        return CommonUtils.buildResp(ResponseCode.SUCCESS);
    }

    @Override
    public User getUser(String userName) {
        return userMapper.findUser(userName);
    }

    @Override
    public Response getInfo() {
        //从session获取用户信息
        Session session = SecurityUtils.getSubject().getSession();
        PrincipalCollection principals = SecurityUtils.getSubject().getPrincipals();
        Object user = session.getAttribute(Constants.USER_INFO_SESSION_KEY+ SecurityUtils.getSubject().getPrincipal());
        if (user == null) {
            CommonUtils.throwException(ResponseCode.LOGIN_EXPIRE);
        }
        User userInfo = JsonUtils.fromJson(user.toString(), User.class);
//        userInfo = userMapper.selectByPrimaryKey(1L);
//        String username = userInfo.getUserName();
        Map<String, Object> resultMap = new HashMap<>();
        List<Permission> permissionList = permissionService.getUserPermissions(userInfo.getId());
        List<Role> roleList = roleService.getRoleByUserId(userInfo.getId());
        // 获取用户基本信息
        resultMap.put("userInfo", userInfo);
        /*List<Object> permissions = new ArrayList<>(permissionList.size());
        permissionList.forEach(p -> {
            permissions.add(p.getpCode());
        });*/
        // 获取用户菜单列表
        resultMap.put("menuTrees", menuService.getUserMenus(userInfo.getId()));
        //将用户信息放入session中
        // 将权限、角色信息加入session
        /*session.setAttribute(USER_PERMISSION_SESSION_KEY, permissionList);
        session.setAttribute(USER_ROLE_SESSION_KEY, roleList);
        session.setAttribute(USER_INFO_SESSION_KEY, userInfo);*/
//        response.setData(userPermission);
        RolePermissionContext rolePermissionContext = new RolePermissionContext();
        Set<String> roles = new HashSet<>();
        roleList.forEach(role -> {
            roles.add(role.getCode());
        });
        Set<String> permissions = new HashSet<>();
        permissionList.forEach(role -> {
            permissions.add(role.getpCode());
        });
        rolePermissionContext.setRoles(roles);
        rolePermissionContext.setPermissions(permissions);
        CusSessionDao.putRolePermissions(rolePermissionContext);
//        CacheFactory.putHKey(Constants.ROLE_PERMISSION_SESSION_KEY,SecurityUtils.getSubject().getPrincipals().asList().toString(),rolePermissionContext);
//        session.setAttribute(+, rolePermissionContext);
        // 角色信息
        List<Map<String, Object>> roles2 = new ArrayList<>(roleList.size());
        roleList.forEach(r -> {
            Map<String,Object> role = new HashMap<>(4);
            role.put("id", r.getId());
            role.put("code", r.getCode());
            role.put("roleName", r.getRoleName());
            roles2.add(role);
        });
        resultMap.put("roles", roles2);
        // 获取用户权限列表
        resultMap.put("permissions", permissions);// 所拥有的所有权限
        return CommonUtils.buildSuccessResp(resultMap);
    }

    @Override
    public Response logout() {
        try {
            Subject currentUser = SecurityUtils.getSubject();
            currentUser.logout();
        } catch (Exception e) {
        }
        return CommonUtils.buildResp(ResponseCode.SUCCESS);
    }

   /* @Override
    public Response listUser(int currentPage, int pageSize) {
        Page page = new Page<>(currentPage, pageSize);
        Map<String, Object> map = new HashMap<>();
        map.put("page", page);
        userMapper.findUsers(map);
        return CommonUtils.buildSuccessResp(map.get("page"));
    }*/

    @Override
    public Response findUserInfos(int currentPage, int pageSize, Request request) {
        Page page = new Page<>(currentPage, pageSize);
//        Map<String, Object> map = new HashMap<>();
//        map.put("page", page);
//        map.put("request", request);
        page.setParams(request);
        userMapper.findUserInfos(page);
//        Page<User> users = (Page) map.get("page");
        List<User> userList = page.getData();
        if (userList != null && !userList.isEmpty()) {
            userList.forEach(user -> {
                user.setPwd("");//不把密码返回回去
            });
        }
        return CommonUtils.buildSuccessResp(page);
    }

    @Override
    public Response getAllRoles() {
        List<Role> allRoles = roleService.getAllRoles();
        return CommonUtils.buildSuccessResp(allRoles);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Response add(Request request) {
        User user = new User();
        user.setUserCode(request.getString("userCode"));
        user.setUserName(request.getString("username"));
        user.setEmail(request.getString("email"));
        String phoneNo = request.getString("phoneNo");
        user.setPhoneNo(StringUtils.isEmpty(phoneNo)?null:phoneNo);
        user.setStatus(request.getBoolean("status")?1:0);
        // 默认密码123456
//        DigistUtils.getMD5String(user.getPhoneNo()+"123456");
        String password = request.getString("password");
        String salt = StringUtils.getUUID();
        user.setSalt(salt);
        // 使用shiro对应的加密机制
        SimpleHash md5 = new SimpleHash("MD5", password,salt , 2);
        user.setPwd(md5.toHex());
        userMapper.insertSelective(user);
        // 设置部门关系
        long orgId = request.getLong("orgId");
        UserOrg userOrg = new UserOrg();
        userOrg.setUserId(user.getId());
        userOrg.setOrgId(orgId);
        // 增加用户与部门关系表
        userOrgMapper.insertSelective(userOrg);
        // 设置角色关系
        UserRole userRole = new UserRole();
        userRole.setUserId(user.getId());
        userRole.setRoleId(request.getLong("roleId"));
        userRoleMapper.insertSelective(userRole);
        // 更新用户数据缓存
        updateCachedUserData();
        return CommonUtils.buildSuccessResp();
    }

    private void updateCachedUserData() {
        ThreadPoolFactory.submit(() -> {
            List<User> users = userMapper.findUserInfos(new Page<>(1,10000));
            logger.info("更新用户数据到中央缓存");
            // 过滤掉被禁用的用户
            CacheFactory.putObject(Constants.OCTOPUS_ALL_USER_KEY, users.parallelStream().filter(user -> user.getStatus() == 1).collect(Collectors.toList()));
        });
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Response update(Request request) {
        long id = request.getLong("userId");
        String userCode = request.getString("userCode");
        String username = request.getString("username");
        String email = request.getString("email");
        String phoneNo = request.getString("phoneNo");
        long roleId = request.getLong("roleId");
        long orgId = request.getLong("orgId");
        String password = request.getString("password");
        boolean status = request.getBoolean("status");
        User user = new User();
        user.setId(id);
//        user.setPwd(StringUtils.isEmpty(password)?null:password);
//        user.setNickName(nickname);
        user.setUserCode(userCode);
        user.setStatus(status?1:0);
        // 将会话中的用户信息更新 修复更新用户信息时启用禁用用户
        CacheFactory.putObject(Constants.REFRESH_USER_INFO_SESSION_KEY+id,user);
        if (!StringUtils.isEmpty(password)) {// 密码不为空时才更新密码
            // 找到原用户密码盐
            User existUser = userMapper.selectByPrimaryKey(id);
            // 使用shiro对应的加密机制
            SimpleHash md5 = new SimpleHash("MD5", password,existUser.getSalt() , 2);
            user.setPwd(md5.toHex());
        }
        user.setUserName(username);
        user.setEmail(email);
        user.setPhoneNo(phoneNo);
        userMapper.updateByPrimaryKeySelective(user);
        // 更新用户角色信息
        UserRole userRole = new UserRole();
        userRole.setUserId(id);
        userRole.setRoleId(roleId);
        userRoleMapper.updateByUserId(userRole);
        // 更新用户部门信息
        UserOrg userOrg = new UserOrg();
        userOrg.setUserId(id);
        userOrg.setOrgId(orgId);
        userOrgMapper.updateByUserId(userOrg);
        // 更新用户数据缓存
        updateCachedUserData();
        return CommonUtils.buildSuccessResp();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Response delete(User request) {
        userMapper.deleteByPrimaryKey(request.getId());
        userRoleMapper.deleteByUserId(request.getId());
        userOrgMapper.deleteByUserId(request.getId());
        // 更新用户数据缓存
        updateCachedUserData();
        return CommonUtils.buildSuccessResp();
    }

    @Override
    public Response updatePwd(Request request) {
        long userId = request.getLong("userId");
        String oldPwd = request.getString("oldPwd");
        String newPwd = request.getString("newPwd");
        // 先判断旧密码是否正确
        //从session获取用户信息
        Session session = SecurityUtils.getSubject().getSession();
        Object user = session.getAttribute(Constants.USER_INFO_SESSION_KEY+SecurityUtils.getSubject().getPrincipal());
        if (user == null) {
            CommonUtils.throwException(ResponseCode.LOGIN_EXPIRE);
        }
        User userInfo = JsonUtils.fromJson(user.toString(), User.class);
        // 使用shiro对应的加密机制
        SimpleHash md5 = new SimpleHash("MD5", oldPwd,userInfo.getSalt() , 2);
        User existUser = userMapper.selectByPrimaryKey(userId);
        if (StringUtils.isEmpty(existUser.getPwd()) || !existUser.getPwd().equals(md5.toHex())) {
            // 密码不匹配
            return CommonUtils.buildResp(PWD_CHANGE_FAILURE_ERROR);
        }
        // 更改密码
        User changePwd = new User();
        changePwd.setId(userId);
        // 使用shiro对应的加密机制
        md5 = new SimpleHash("MD5",newPwd ,userInfo.getSalt() , 2);
        changePwd.setPwd(md5.toHex());
        changePwd.setStatus(1);
        userMapper.updateByPrimaryKeySelective(changePwd);
        return CommonUtils.buildSuccessResp();
    }

    @Override
    public Response enable(long userId, int status) {
        // 启用禁用
        User enable = new User();
        enable.setId(userId);
        enable.setStatus(status);
        userMapper.updateByPrimaryKeySelective(enable);
        // 将会话中的用户信息更新
        CacheFactory.putObject(Constants.REFRESH_USER_INFO_SESSION_KEY+userId,enable);
        // 更新用户数据缓存
        updateCachedUserData();
        return CommonUtils.buildSuccessResp();
    }
}
