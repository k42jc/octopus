package com.dafy.octopus.rental.service.user;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.Date;

public interface UserService {
    /**
     * 查询用户信息列表
     * @param key
     * @param createDate
     * @return
     */
    JSONObject queryUserList(String key, Date createDate, Date endDate, int pageSize, int currentPage) throws Exception;

    /**
     * 获取客户详细信息
     * @param customerid
     * @return
     */
    JSONObject getUserDetailInfo(String customerid) throws Exception;

    /**
     * 获取客户简要信息
     * @param customerid
     * @return
     */
    JSONObject getUserBriefInfo(String customerid) throws Exception;

    /**
     * 模糊查询客户信息列表
     * @param key
     * @return
     */
    JSONObject queryUserInfo(String key) throws Exception;
}
