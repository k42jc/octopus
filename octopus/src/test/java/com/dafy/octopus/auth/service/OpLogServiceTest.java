package com.dafy.octopus.auth.service;

import com.dafy.common.po.Response;
import com.dafy.octopus.auth.TestBaseConfig;
import com.dafy.octopus.web.core.domain.Request;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by liaoxudong
 * Date:2018/5/3
 */

public class OpLogServiceTest extends TestBaseConfig {

    @Autowired
    private IOpLogSevice opLogSevice;

    @Test
    public void test(){
        Request request = new Request();
        request.put("condition", "");
        request.put("startTime", "2018-03-10");
        request.put("endTime", "2018-04-30");
        Response list = opLogSevice.list(1, 10, request);
    }
}
