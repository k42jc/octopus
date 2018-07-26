package com.dafy.octopus.rental.service;

import com.alibaba.fastjson.JSONObject;
import com.dafy.common.util.FileUtils;
import com.dafy.octopus.rental.TestBaseConfig;
import com.dafy.octopus.rental.service.user.UserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by liaoxudong
 * Date:2018/7/10
 */

public class CustomerInfoService extends TestBaseConfig{

    @Autowired
    private UserService userServiceImpl;
    @Test
    public void getCustomer(){
        try {
            JSONObject jsonObject = userServiceImpl.queryUserList("", null, null, 20000, 1);
            String data = jsonObject.getString("data");
            JSONObject parseObject = JSONObject.parseObject(data);
            FileUtils.writeByteToFile(parseObject.getString("list").getBytes(),"D:\\Users\\liaoxudong\\Desktop\\物主客户信息2.json",false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
