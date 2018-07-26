package com.dafy.octopus.rental.impl.user;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.dafy.common.util.StringUtils;
import com.dafy.octopus.rental.common.PhoneLenderConstants;
import com.dafy.octopus.rental.service.user.UserService;
import com.dafy.octopus.rental.common.PhoneLenderHttpUtil;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserServiceImpl implements UserService {

    @Value("${config.phone.lender.ase.key}")
    private String aseKey;
    @Value("${config.phone.lender.http.url}")
    private String httpUrl;

    /**
     * 查询用户信息列表
     * @param key
     * @param startDate
     * @return
     */
    @Override
    public JSONObject queryUserList(String key, Date startDate, Date endDate, int pageSize, int currentPage) throws Exception {
        JSONObject data = new JSONObject();
        data.put("pageSize", pageSize);
        data.put("pageNum", currentPage);
        data.put("nameAndTelAndCert", StringUtils.isEmpty(key) ? "" : key);
        data.put("startRegist", startDate == null ? "" : new DateTime(startDate).toString("yyyy-MM-dd HH:mm:ss"));
        data.put("endRegist", endDate == null ? "" : new DateTime(endDate).toString("yyyy-MM-dd HH:mm:ss"));

        JSONObject response = PhoneLenderHttpUtil.doPost(JSONObject.toJSONString(data, SerializerFeature.WriteMapNullValue),
                PhoneLenderConstants.GET_USER_LIST_URL);

        return response;
    }

    /**
     * 获取客户详细信息
     *
     * @param customerid
     * @return
     */
    @Override
    public JSONObject getUserDetailInfo(String customerid) throws Exception {
        JSONObject data = new JSONObject();
        data.put("customerId", customerid);

        JSONObject response = PhoneLenderHttpUtil.doPost(JSONObject.toJSONString(data, SerializerFeature.WriteMapNullValue),
                PhoneLenderConstants.GET_USER_DETAIL_INFO_URL);
        return response;
    }

    /**
     * 获取客户简要信息
     *
     * @param customerid
     * @return
     */
    @Override
    public JSONObject getUserBriefInfo(String customerid) throws Exception {
        JSONObject data = new JSONObject();
        data.put("customerId", customerid);

        JSONObject response = PhoneLenderHttpUtil.doPost(JSONObject.toJSONString(data, SerializerFeature.WriteMapNullValue),
                PhoneLenderConstants.GET_USER_BRIEF_INFO_URL);
        return response;
    }

    /**
     * 模糊查询客户信息列表
     *
     * @param key
     * @return
     */
    @Override
    public JSONObject queryUserInfo(String key) throws Exception {
        JSONObject data = new JSONObject();
        data.put("data", key);
        data.put("pageSize", 10);
        data.put("pageNum", 1);

        JSONObject response = PhoneLenderHttpUtil.doPost(JSONObject.toJSONString(data, SerializerFeature.WriteMapNullValue),
                PhoneLenderConstants.QUERY_USER_LIST_INFO_URL);

        return response;
    }
}
