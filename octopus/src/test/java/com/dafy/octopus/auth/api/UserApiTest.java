package com.dafy.octopus.auth.api;

import com.dafy.octopus.auth.TestBaseConfig;
import com.dafy.octopus.web.core.domain.Request;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户相关接口单元测试
 * Created by liaoxudong
 * Date:2018/5/3
 */

public class UserApiTest extends TestBaseConfig{
    private static final Logger logger = LoggerFactory.getLogger(UserApiTest.class);

    @Autowired
    private UserApi userApi;

    private String username = "13148899469";

    private String password = "cfde714f1c3ed597cd803e87f9a47b1b";

    /**
     * 操作前登录
     */
    /*@Before
    public void login(){
        AuthenticationToken authenticationToken = new UsernamePasswordToken(username,password);
        try {
            SecurityUtils.getSubject().login(authenticationToken);
        } catch (AuthenticationException e) {
//            e.printStackTrace();
            logger.error("登录失败!",e);
        }
        logger.debug("登录成功");
    }*/

    @Test
    public void list(){
        Map<String,Object> header = new HashMap<>();
        header.put("pagenum", "1");
        header.put("pagesize", "10");
        Request request = new Request();
        request.put("condition", "");
        request.put("orgId", "0");
//        userApi.list(header,request);
    }


}
