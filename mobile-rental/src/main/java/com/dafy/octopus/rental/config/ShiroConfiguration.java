package com.dafy.octopus.rental.config;

import com.dafy.octopus.web.core.shiro.filter.BaseAuthenticationFilter;
import com.dafy.octopus.web.core.shiro.listener.SystemSessionListener;
import com.dafy.octopus.web.core.shiro.realm.PluginAuthorizingRealm;
import com.dafy.octopus.web.core.shiro.session.RedisSessionDao;
import org.apache.shiro.mgt.RememberMeManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.servlet.Filter;
import java.util.*;

/**
 * shiro配置
 *
 * 注意199行的static控制加载顺序，避免在配置文件加载之前先加载本配置
 * issue fix: https://blog.csdn.net/wuxuyang_7788/article/details/70141812
 *
 *
 *
 */
@Configuration
public class ShiroConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(ShiroConfiguration.class);

    @Value("${config.octopus.appKey}")
    private String appKey;
    @Value("${config.octopus.remoteAuth.loginUrl}")
    private String loginUrl;
    @Value("${config.octopus.remoteAuth.successUrl}")
    private String successUrl;
    @Value("${config.octopus.remoteAuth.unauthorizedUrl}")
    private String unauthorizedUrl;
    /**
     * Shiro的Web过滤器Factory 命名:shiroFilter
     */
    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //Shiro的核心安全接口,这个属性是必须的
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        shiroFilterFactoryBean.setLoginUrl(loginUrl);
//        shiroFilterFactoryBean.setSuccessUrl(successUrl);
        shiroFilterFactoryBean.setUnauthorizedUrl(unauthorizedUrl);
        Map<String, Filter> filterMap = new LinkedHashMap<>();
//        filterMap.put("authc", new ServerFormAuthenticationFilter());
//        filterMap.put("sysUser", new SysUserFilter());
        filterMap.put("authc", new BaseAuthenticationFilter());
        shiroFilterFactoryBean.setFilters(filterMap);
        /*定义shiro过滤链  Map结构
         * Map中key(xml中是指value值)的第一个'/'代表的路径是相对于HttpServletRequest.getContextPath()的值来的
         * anon：它对应的过滤器里面是空的,什么都没做,这里.do和.jsp后面的*表示参数,比方说login.jsp?main这种
         * authc：该过滤器下的页面必须验证后才能访问,它是Shiro内置的一个拦截器org.apache.shiro.web.filter.authc.FormAuthenticationFilter
         */
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
         /* 过滤链定义，从上向下顺序执行，一般将 / ** 放在最为下边:这是一个坑呢，一不小心代码就不好使了;
          authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问 */
        filterChainDefinitionMap.put("/", "anon");
        filterChainDefinitionMap.put("/hello", "anon");
        filterChainDefinitionMap.put("/**", "authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    /**
     * 不指定名字的话，自动创建一个方法名第一个字母小写的bean
     */
    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
//        securityManager.setRealms(userRealm());
        securityManager.setRealm(authorizingRealm());
//        securityManager.setCacheManager(new MemoryConstrainedCacheManager());
        securityManager.setSessionManager(sessionManager());
        securityManager.setRememberMeManager(rememberMeManager());
        return securityManager;
    }

    /**
     * 用于保存会话ID的cookie，实现分布式会话管理
     * @return
     */
    @Bean
    public Cookie sessionIdCookie(){
        SimpleCookie cookie = new SimpleCookie();
        cookie.setName("sessionId");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(-1);
        cookie.setDomain("");
        cookie.setPath("/");
        return cookie;
    }

    public SessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionListeners(sessionListeners());
        sessionManager.setSessionDAO(redisSessionDao());
        // 利用cookie来记录客户端行为
        sessionManager.setSessionIdCookieEnabled(true);
//        sessionManager.setDeleteInvalidSessions(false);
        sessionManager.setSessionIdCookie(sessionIdCookie());
        return sessionManager;
    }

    /*@Bean
    public RemoteSessionDAO remoteSessionDAO(){
        RemoteSessionDAO sessionDAO = new RemoteSessionDAO();
        sessionDAO.setAppKey(appKey);
//        sessionDAO.setRemoteAuthService(remoteAuthService);
        return sessionDAO;
    }*/

    @Bean
    public RedisSessionDao redisSessionDao (){
        RedisSessionDao sessionDao = new RedisSessionDao();
        return sessionDao;
    }

    public List<SessionListener> sessionListeners(){
        List<SessionListener> listeners = new ArrayList<>();
        SystemSessionListener listener = new SystemSessionListener();
        listeners.add(listener);
        return listeners;
    }

    /**
     * Shiro Realm 继承自AuthorizingRealm的自定义Realm,即指定Shiro验证用户登录的类为自定义的
     */
   /* public List<Realm> userRealm() {
        List<Realm> list = new ArrayList<>(4);
        list.add(usernameAuthRealm());
//        list.add(phoneAuthRealm());
//        list.add(emailAuthRealm());
        return list;
    }*/

    /**
     * 记住我
     * @return
     */
    @Bean
    public RememberMeManager rememberMeManager(){
        CookieRememberMeManager rememberMeManager = new CookieRememberMeManager();
        rememberMeManager.setCookie(simpleCookie());
        rememberMeManager.setCipherKey(Base64.getDecoder().decode("4AvVhmFLUs0KTA3Kprsdag=="));
        return rememberMeManager;
    }

    @Bean
    public SimpleCookie simpleCookie(){
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        simpleCookie.setHttpOnly(true);
        simpleCookie.setMaxAge(2592000);//30天
        simpleCookie.setDomain("");
        simpleCookie.setPath("/");
        return simpleCookie;
    }

    @Bean
    public PluginAuthorizingRealm authorizingRealm(){
        PluginAuthorizingRealm authorizingRealm = new PluginAuthorizingRealm();
        authorizingRealm.setAppKey(appKey);
//        authorizingRealm.setRemoteAuthService(remoteAuthService);
        return authorizingRealm;
    }

//    @Bean
//    public PhoneAuthRealm phoneAuthRealm(){
//        return new PhoneAuthRealm(retryLimitHashedCredentialsMatcher());
//    }

/*    @Bean
    public EmailAuthRealm emailAuthRealm(){
        return new EmailAuthRealm(retryLimitHashedCredentialsMatcher());
    }*/


    /**
     * Shiro生命周期处理器
     * 将初始化方法改为静态，解决当前bean在配置文件初始化之前加载的问题
     */
    @Bean
    public static LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * 开启Shiro的注解(如@RequiresRoles,@RequiresPermissions),需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证
     * 配置以下两个bean(DefaultAdvisorAutoProxyCreator(可选)和AuthorizationAttributeSourceAdvisor)即可实现此功能
     */
    @Bean
    @DependsOn({"lifecycleBeanPostProcessor"})
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager());
        return authorizationAttributeSourceAdvisor;
    }
}
