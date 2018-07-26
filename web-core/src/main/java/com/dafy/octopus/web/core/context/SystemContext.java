package com.dafy.octopus.web.core.context;

import com.dafy.cache.factory.CacheFactory;
import com.dafy.octopus.web.core.domain.Constants;
import com.dafy.octopus.web.core.dto.Organization;
import com.dafy.octopus.web.core.dto.User;
import com.dafy.octopus.web.core.utils.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 系统上下文 用于获取关键数据
 *
 * 使用redis中央缓存共享数据，因为只需要关键数据，用户/部门等
 *
 * Created by liaoxudong
 * Date:2018/6/6
 */

public final class SystemContext {
    private static final Logger logger = LoggerFactory.getLogger(SystemContext.class);
    // 大对象 ... 去掉本地缓存 不然无法及时响应用户中心的更改用户信息
//    public static final List<User> USERS_LOCAL_CACHE = new ArrayList<>();
//    public static final List<Organization> ORGS_LOCAL_CACHE = new ArrayList<>();
    // 获取那种类型数据
    public static final int LOAD_TYPE_USER = 0;
    public static final int LOAD_TYPE_ORG = 1;
    // 获取部门内所有用户
    public static final int LOAD_TYPE_ORGUSERS = 2;

    /**
     * 通过用户id获取用户信息
     * @param userId
     * @return
     */
    public static User getUserById(Long userId) {
        Object result = loadData(userId, LOAD_TYPE_USER);
        return result == null?null:(User)result;
    }

    /**
     * 获取部门数据
     * @param orgId
     * @return
     */
    public static Organization getOrgById(Long orgId) {
        Object result = loadData(orgId, LOAD_TYPE_ORG);
        return result == null?null:(Organization) result;
    }

    private static Object loadData(Long id, int loadTypeOrg) {
        if (id == null || id <= 0) {
            return null;
        }
        if (loadTypeOrg == LOAD_TYPE_USER) {// 获取用户数据
            List<User> users = loadUsers();
            if (users == null || users.isEmpty()) {
                return null;
            }
            for (User user : users) {
                if (id.longValue() == user.getId().longValue()) {
                    user.setPwd("");
                    return user;
                }
            }
        }
        if (loadTypeOrg == LOAD_TYPE_ORG) {// 获取部门数据
            /*if (ORGS_LOCAL_CACHE.isEmpty()) {
                synchronized (ORGS_LOCAL_CACHE) {
                    if (ORGS_LOCAL_CACHE.isEmpty()) {
                        ORGS_LOCAL_CACHE.addAll(CacheFactory.getObject(Constants.OCTOPUS_ALL_ORG_KEY, List.class));
                    }
                }
            }
            if (ORGS_LOCAL_CACHE.isEmpty()) {
                logger.error("获取全局用户数据失败");
                CommonUtils.throwException(ExceptionCode.DATA_COMPLETE_ERROR);
            }*/
            List<Organization> orgs = CacheFactory.getObject(Constants.OCTOPUS_ALL_ORG_KEY, List.class);
            int retry = 0;
            while (orgs == null && retry++ < 3) {
                try {
                    // 如果暂时拿不到数据 等0.3秒重试，重试三次
                    Thread.sleep(300);
                    orgs = CacheFactory.getObject(Constants.OCTOPUS_ALL_ORG_KEY, List.class);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            if(orgs == null){
                logger.error("获取不到部门数据！！！！！！！！！！！");
                CommonUtils.throwException("error","获取部门数据失败");
            }
            for (Organization organization : orgs) {
                if (id.longValue() == organization.getId().longValue()) {
                    return organization;
                }
            }
        }

        if (loadTypeOrg == LOAD_TYPE_ORGUSERS) {// 获取某部门下所有用户
            List<User> users = loadUsers();
            if (users == null || users.isEmpty()) {
                return null;
            }
            List<User> orgUsers = new ArrayList<>();
            for (User user : users) {
                Organization organization = user.getOrganization();
//                Organization parentOrg = organization.getParentOrg();
//                Long orgId =  parentOrg == null?organization.getId():parentOrg.getId();
                if (id.longValue() == organization.getId()) {// 获取顶级部门所有人
                    user.setPwd("");
                    orgUsers.add(user);
                }
            }
            return orgUsers;
        }
        return null;

    }

    /*private static void loadUsers() {
        if (USERS_LOCAL_CACHE.isEmpty()) {
            synchronized (USERS_LOCAL_CACHE) {
                if (USERS_LOCAL_CACHE.isEmpty()) {
                    USERS_LOCAL_CACHE.addAll(CacheFactory.getObject(Constants.OCTOPUS_ALL_USER_KEY, List.class));
                }
            }
        }
        if (USERS_LOCAL_CACHE.isEmpty()) {
            logger.error("获取全局用户数据失败");
            CommonUtils.throwException(ExceptionCode.DATA_COMPLETE_ERROR);
        }
    }*/
    // FIXME 暂时使用缓存共享数据 如果后面需要迭代 请使用RPC方案代替
    private static List<User> loadUsers(){
        List<User> users = CacheFactory.getObject(Constants.OCTOPUS_ALL_USER_KEY, List.class);
        int retry = 0;
        while (users == null && retry++ < 3) {
            try {
                // 如果暂时拿不到数据 等0.3秒重试
                Thread.sleep(300);
                users = CacheFactory.getObject(Constants.OCTOPUS_ALL_USER_KEY, List.class);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (users == null) {
            CommonUtils.throwException("error","获取用户数据失败");
        }
        return users;
    }

    /**
     * 获取部门下的所有用户列表
     * @param orgId
     * @return
     */
    public static List<User> getUsersByOrgId(Long orgId) {
        Object result = loadData(orgId, LOAD_TYPE_ORGUSERS);
        return result == null?null:(List<User>) result;
    }
    /*private static User loadLocalCache(Long userId,int loadType) {
        if (loadType == LOAD_TYPE_USER) {
            if (USERS_LOCAL_CACHE.isEmpty()) {
                return null;
            }
        }
    }*/
    }
