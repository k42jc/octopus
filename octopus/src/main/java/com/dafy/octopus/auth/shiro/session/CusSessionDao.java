package com.dafy.octopus.auth.shiro.session;

import com.dafy.octopus.web.core.domain.Constants;
import com.dafy.octopus.web.core.shiro.session.RedisSessionDao;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 使用redis管理会话 便于分布式扩展
 *
 * 增加本地缓存，解决同一次请求多次请求相同session问题，减少/避免redis中央缓存大量操作
 *
 *
 * Created by liaoxudong
 * Date:2018/4/16
 */

@Component
public class CusSessionDao extends RedisSessionDao{
    @Value("${server.session.timeout}")
    private int timeout;

    @Override
    public int getTimeout() {
//        int timeout = ConfigUtils.get("server.session.timeout", Integer.class);
//        return timeout;// session过期时间 30分钟
        return Constants.SESSION_TIME_OUT;
    }

}
