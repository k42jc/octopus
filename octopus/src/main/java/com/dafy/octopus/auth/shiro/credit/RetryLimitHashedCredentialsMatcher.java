package com.dafy.octopus.auth.shiro.credit;

import com.dafy.cache.factory.CacheFactory;
import com.dafy.common.util.JsonUtils;
import com.dafy.octopus.web.core.domain.ResponseCode;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 密码匹配器
 * 增加限制密码错误次数
 * Created by liaoxudong
 * Date:2018/4/13
 */

public class RetryLimitHashedCredentialsMatcher extends HashedCredentialsMatcher{
    private static final Logger logger = LoggerFactory.getLogger(RetryLimitHashedCredentialsMatcher.class);

    private final static String RETRY_PASSWORD_KEY = "LOGIN:RETRY:";
    private static final int UNLOCK_TIME = 20;//账户解锁时间 20分钟

    private int retryTimes = 5;

    public void setRetryTimes(int retryTimes) {
        this.retryTimes = retryTimes;
    }

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        String loginName = (String)token.getPrincipal();// 获取用户名
        long times = CacheFactory.incr(RETRY_PASSWORD_KEY + loginName);
        boolean correct = super.doCredentialsMatch(token, info);
        if (!correct) {
            logger.warn("token:{}, info:{}", JsonUtils.toJson(token), JsonUtils.toJson(info));

            if (times < retryTimes) {// 每次都提示一下
                throw new AuthenticationException(String.format(ResponseCode.PWD_RETRY_TIMES.toString(),retryTimes-times));
            }else{
                logger.warn("密码错误次数超过限制，此账号:{}禁用{}分钟",loginName,UNLOCK_TIME);
                CacheFactory.expireString(RETRY_PASSWORD_KEY + loginName,UNLOCK_TIME*60);
                throw new AuthenticationException(String.format(ResponseCode.PWD_ERROR_OUTOF_LIMIT.toString(),UNLOCK_TIME));
            }
        }else{// 正确就删除掉
            CacheFactory.deleteString(RETRY_PASSWORD_KEY + loginName);
        }
        return correct;
    }
}
