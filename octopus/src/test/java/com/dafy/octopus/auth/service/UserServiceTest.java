package com.dafy.octopus.auth.service;

import com.dafy.common.po.Response;
import com.dafy.octopus.auth.TestBaseConfig;
import com.dafy.octopus.web.core.domain.Request;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 系统用户相关测试
 * Created by liaoxudong
 * Date:2018/5/3
 */

public class UserServiceTest extends TestBaseConfig {
    @Autowired
    private IUserService userService;

    @Test
    public void findUserInfos(){
        Request request = new Request();
        request.put("condition", "1314");
        request.put("orgId", "0");
        Response userInfos = userService.findUserInfos(1, 10, request);
        Assert.assertNotNull(userInfos.getData());
    }
}
