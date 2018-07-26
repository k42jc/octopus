package com.dafy.octopus.auth.config;

import com.dafy.octopus.web.core.shiro.listener.SystemSessionListener;
import com.dafy.octopus.auth.shiro.credit.RetryLimitHashedCredentialsMatcher;
import com.dafy.octopus.auth.shiro.filter.AuthCenterAuthenticationFilter;
import com.dafy.octopus.auth.shiro.realms.UsernameAuthRealm;
import com.dafy.octopus.auth.shiro.session.CusSessionDao;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.RememberMeManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.SessionDAO;
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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.servlet.Filter;
import java.util.*;

/**
 * @author: hxy
 * @description: shiro配置类
 * @date: 2017/10/24 10:10
 */
@Configuration
public class ShiroConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(ShiroConfiguration.class);

    /**
     * Shiro的Web过滤器Factory 命名:shiroFilter
     */
    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //Shiro的核心安全接口,这个属性是必须的
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        Map<String, Filter> filterMap = new LinkedHashMap<>();
//        filterMap.put("authc", new ServerFormAuthenticationFilter());
//        filterMap.put("sysUser", new SysUserFilter());
        filterMap.put("authc", new AuthCenterAuthenticationFilter());
        shiroFilterFactoryBean.setFilters(filterMap);
        shiroFilterFactoryBean.setLoginUrl("/login");// 用于展示api文档
//        shiroFilterFactoryBean.setSuccessUrl("/auth-center");// 用于展示api文档
        /*定义shiro过滤链  Map结构
         * Map中key(xml中是指value值)的第一个'/'代表的路径是相对于HttpServletRequest.getContextPath()的值来的
         * anon：它对应的过滤器里面是空的,什么都没做,这里.do和.jsp后面的*表示参数,比方说login.jsp?main这种
         * authc：该过滤器下的页面必须验证后才能访问,它是Shiro内置的一个拦截器org.apache.shiro.web.filter.authc.FormAuthenticationFilter
         */
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
         /* 过滤链定义，从上向下顺序执行，一般将 / ** 放在最为下边:这是一个坑呢，一不小心代码就不好使了;
          authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问 */
        filterChainDefinitionMap.put("/", "anon");
        filterChainDefinitionMap.put("/login", "anon");// 用于展示api文档
        filterChainDefinitionMap.put("/doLogin", "anon");// 用于展示api文档
//        filterChainDefinitionMap.put("/remoteAuthService", "anon");
        filterChainDefinitionMap.put("/user/login", "anon");
//        filterChainDefinitionMap.put("/user/logout", "logout");
//        filterChainDefinitionMap.put("/authenticated", "authc");
//        filterChainDefinitionMap.put("/error", "anon");
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
        securityManager.setRealm(usernameAuthRealm());
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
        sessionManager.setSessionDAO(sessionDAO());
        // 利用cookie来记录客户端行为
        sessionManager.setSessionIdCookieEnabled(true);
//        sessionManager.setDeleteInvalidSessions(false);
        sessionManager.setSessionIdCookie(sessionIdCookie());
        return sessionManager;
    }

    public SessionDAO sessionDAO(){
        return new CusSessionDao();
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
    public UsernameAuthRealm usernameAuthRealm(){
        return new UsernameAuthRealm(retryLimitHashedCredentialsMatcher());
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
     * 凭证匹配器
     * （由于我们的密码校验交给Shiro的SimpleAuthenticationInfo进行处理了
     * 所以我们需要修改下doGetAuthenticationInfo中的代码;
     * ）
     * 可以扩展凭证匹配器，实现 输入密码错误次数后锁定等功能，下一次
     */
    @Bean(name = "credentialsMatcher")
    public HashedCredentialsMatcher retryLimitHashedCredentialsMatcher() {
        RetryLimitHashedCredentialsMatcher hashedCredentialsMatcher = new RetryLimitHashedCredentialsMatcher();
        //散列算法:这里使用MD5算法;
        hashedCredentialsMatcher.setHashAlgorithmName("md5");
        //散列的次数，比如散列两次，相当于 md5(md5(""));
        hashedCredentialsMatcher.setHashIterations(2);
        //storedCredentialsHexEncoded默认是true，此时用的是密码加密用的是Hex编码；false时用Base64编码
        hashedCredentialsMatcher.setStoredCredentialsHexEncoded(true);
        hashedCredentialsMatcher.setRetryTimes(5);
        return hashedCredentialsMatcher;
    }

    /**
     * Shiro生命周期处理器
     */
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
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
