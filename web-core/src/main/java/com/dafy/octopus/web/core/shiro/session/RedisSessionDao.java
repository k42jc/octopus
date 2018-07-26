package com.dafy.octopus.web.core.shiro.session;

import com.dafy.cache.factory.CacheFactory;
import com.dafy.octopus.web.core.domain.RolePermissionContext;
import com.dafy.octopus.web.core.utils.CommonUtils;
import com.dafy.octopus.web.core.domain.Constants;
import com.dafy.octopus.web.core.domain.ResponseCode;
import com.dafy.octopus.web.core.utils.SubjectUtils;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.ValidatingSession;
import org.apache.shiro.session.mgt.eis.CachingSessionDAO;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/**
 * 使用redis管理会话 便于分布式扩展
 *
 * 增加本地缓存，解决同一次请求多次请求相同session问题，减少/避免redis中央缓存大量操作
 *
 *
 * Created by liaoxudong
 * Date:2018/4/16
 */

public class RedisSessionDao extends CachingSessionDAO{
    private static final Logger logger = LoggerFactory.getLogger(RedisSessionDao.class);
    public static final String SESSION_KEY = "SHIRO:SESSION";

    /**
     * guava Cache实现：
     * 存在缓存，则LOCAL_CACHE.get()/getUnchecked()返回
     * 否则调用load从redis中央缓存加载，减少不必要消耗
     */
    private static final LoadingCache<String, Session> LOCAL_CACHE = CacheBuilder
            .newBuilder()
            .maximumSize(1000)
            .expireAfterAccess(Constants.SESSION_TIME_OUT, TimeUnit.SECONDS)// 过期时间与会话超时时间相同
            .build(
                    new CacheLoader<String, Session>() {
                        @Override
                        public Session load(String sessionId) throws Exception {
                            logger.debug("从redis读取会话：{}", sessionId);
                            return CacheFactory.getHValue(SESSION_KEY, sessionId, Session.class);
                        }
                    }
            );
//    private static final ThreadLocal<Session> TO_UPDATE_SESSION = new ThreadLocal<>();
    // 用于保存
    @Override
    protected void doUpdate(Session session) {
        if(session instanceof ValidatingSession && !((ValidatingSession)session).isValid()) {
            return; //如果会话过期/停止没必要再更新了
        }
        logger.debug("更新会话");
//        LOCAL_CACHE.put(session.getId().toString(),session);
        LOCAL_CACHE.invalidate(session.getId().toString());
        CacheFactory.putHKey(SESSION_KEY,session.getId().toString(),session,getTimeout());
    }

    @Override
    protected void doDelete(Session session) {
        logger.debug("删除会话");
        LOCAL_CACHE.invalidate(session.getId().toString());// 清除本地缓存
        CacheFactory.deleteHObject(SESSION_KEY,session.getId().toString());
    }

    @Override
    protected Serializable doCreate(Session session) {
//        session = SessionWrapper.unWrapper(session);
        logger.debug("创建会话");
        Serializable sessionId = generateSessionId(session);
        assignSessionId(session, sessionId);
        // 使用redis缓存过期机制控制会话过期时间
        CacheFactory.putHKey(SESSION_KEY,sessionId.toString(),session,getTimeout());
        return session.getId();
    }

    /**
     * 用于本地缓存角色权限信息 本地缓存在管理员中途修改某角色权限时无法刷新的问题 弃用
     *
     */
//    private static final Map<String, RolePermissionContext> ROLE_PERMISSION_CONTEXT_CACHE = new HashMap<>();

    public static void clearRolePermissions(String username){
        // 清除本地缓存
//        ROLE_PERMISSION_CONTEXT_CACHE.clear();
        CacheFactory.deleteHObject(Constants.ROLE_PERMISSION_SESSION_KEY,username);
    }

    /**
     * 用于登录后存储角色权限信息
     * @param rolePermissionContext
     */
    public static void putRolePermissions(RolePermissionContext rolePermissionContext) {
        String principal = SecurityUtils.getSubject().getPrincipal().toString();
        /*String key = Constants.ROLE_PERMISSION_SESSION_KEY + principal;
        ROLE_PERMISSION_CONTEXT_CACHE.put(key,rolePermissionContext);*/
        CacheFactory.putHKey(Constants.ROLE_PERMISSION_SESSION_KEY, principal, rolePermissionContext);
    }

    public static RolePermissionContext getRolePermission(PrincipalCollection principals){
        String key = Constants.ROLE_PERMISSION_SESSION_KEY + principals.getPrimaryPrincipal();
        /*RolePermissionContext localCache = ROLE_PERMISSION_CONTEXT_CACHE.get(key);
        if (localCache != null) {
            return localCache;
        }else{*/
            RolePermissionContext hValue = CacheFactory.getHValue(Constants.ROLE_PERMISSION_SESSION_KEY,principals.getPrimaryPrincipal().toString(), RolePermissionContext.class);
            if (hValue == null) {// 如果中途角色权限信息被管理员更改，将当前用户踢出
                SubjectUtils.logout();
                CommonUtils.throwException(ResponseCode.ROLE_PERMISSION_CHANGED);
            }
//            ROLE_PERMISSION_CONTEXT_CACHE.put(key, hValue);
            return hValue;
//        }
    }

    private static Session getLocalCache(String key) {
        Session result;
        try {
            result = LOCAL_CACHE.getUnchecked(key);
        } catch (CacheLoader.InvalidCacheLoadException e) {
            return null;
        }
        return result;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
//        logger.debug("读取会话");
//        Session session;
        // fix issue 当redis中不存在此会话时，guava报错问题

        return getLocalCache(sessionId.toString());
//        Session hValue = CacheFactory.getHValue(SESSION_KEY, sessionId.toString(), Session.class);
//        return hValue;
    }

    public int getTimeout(){
        return Constants.SESSION_TIME_OUT;
    }
}
