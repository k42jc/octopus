package com.dafy.octopus.rental.service;

import com.alibaba.fastjson.JSONObject;
import com.dafy.octopus.rental.TestBaseConfig;
import com.dafy.octopus.rental.service.order.OrderInfoService;
import com.dafy.octopus.web.core.domain.Request;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by liaoxudong
 * Date:2018/7/24
 */

public class OrderServiceTest extends TestBaseConfig {

    @Autowired
    private OrderInfoService orderInfoServiceImpl;

    @Test
    public void testDelay() throws Exception {
        Request request = new Request();
        request.put("orderNo", "111222222223333333");
        request.put("type", 1);
        request.put("days", 11);
        request.put("remark", "大帅比来取消订单了");
        JSONObject jsonObject = orderInfoServiceImpl.delayOrder(request);
        System.out.println(jsonObject);
    }
}
