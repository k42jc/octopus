package com.dafy.octopus.auth.shiro.filter;

import com.dafy.octopus.web.core.shiro.filter.BaseAuthenticationFilter;


/**
 * 认证过滤器
 *
 */
public class AuthCenterAuthenticationFilter extends BaseAuthenticationFilter {


    /*@Override
    protected void issueSuccessRedirect(ServletRequest request, ServletResponse response) throws Exception {
//        super.issueSuccessRedirect(request, response);
        Response<Map> resp = CommonUtils.buildResp(ResponseCode.REDIRECT_SUCCESS_URL);
        Map<String, String> map = new HashMap<>();
        map.put("location", this.getSuccessUrl());
        resp.setData(map);
        ResponseUtils.responseJson(response,resp);
    }*/



}
