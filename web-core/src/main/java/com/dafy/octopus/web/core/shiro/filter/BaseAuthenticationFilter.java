package com.dafy.octopus.web.core.shiro.filter;

import com.dafy.cache.factory.CacheFactory;
import com.dafy.octopus.web.core.domain.ResponseCode;
import com.dafy.octopus.web.core.dto.User;
import com.dafy.octopus.web.core.utils.CommonUtils;
import com.dafy.octopus.web.core.utils.RequestUtils;
import com.dafy.octopus.web.core.utils.ResponseUtils;
import com.dafy.octopus.web.core.utils.SubjectUtils;
import com.dafy.octopus.web.core.domain.Constants;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.AuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;


/**
 * 当前认证过滤器
 * 针对authc类型的请求
 *
 * 因为是前后端分离，只需要返回json数据的特定状态码，跳转操作又前端完成
 *
 */
public class BaseAuthenticationFilter extends AuthenticationFilter {
    private static final Logger logger = LoggerFactory.getLogger(BaseAuthenticationFilter.class);

    @Override
    public boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        HttpServletRequest req = (HttpServletRequest) request;
        String ip = RequestUtils.getIpAddress(req);
        String uri = req.getRequestURI();
        String api = uri.substring(req.getRequestURI().lastIndexOf("/") + 1);
        logger.info("IP:{}, API:{}   params:{} ",ip , api,   RequestUtils.getParams(req));

        return super.onPreHandle(request, response, mappedValue);
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        boolean accessAllowed = super.isAccessAllowed(request, response, mappedValue);
        if (accessAllowed) {// 已登录状态，检查用户是否中途被禁用
//            IUserService userService = ApplicationContextProvider.getBean(IUserService.class);
            User user = SubjectUtils.getCurrentUser();
            User refreshUser = CacheFactory.getObject(Constants.REFRESH_USER_INFO_SESSION_KEY + user.getId(), User.class);
//            User existUser = userService.getUser(user.getUserName());
            if (refreshUser != null && !refreshUser.isEnable()) {// 如果发现中途被管理员禁用了，也禁止操作
                SubjectUtils.logout();//先登出
                return false;
            }
        }
        return accessAllowed;
    }

    /*@Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        ResponseUtils.responseJson(response,CommonUtils.buildResp(ResponseCode.LOGIN_EXPIRE));// 直接跳转更改为返回json格式数据
        return false;
    }*/

    /**
     * 未登录返回json数据至前端
     *
     * @param servletRequest
     * @param servletResponse
     * @throws Exception
     */
    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        // 处理api文档显示请求
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String uri = request.getRequestURI();
        if (!uri.endsWith("/swagger-ui.html")) {
            ResponseUtils.responseJson(servletResponse, CommonUtils.buildResp(ResponseCode.LOGIN_EXPIRE));
        }else{// swagger api页的登录跳转 如果未登录，跳转到登录页，登录完成再跳转回来
            saveRequest(request, getCurrentUrl(WebUtils.toHttp(request)));
            redirectToLogin(request, servletResponse);
        }
        return false;
    }

    /*@Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        String backUrl = request.getParameter("backUrl");
//        saveRequest(request, backUrl, getDefaultBackUrl(WebUtils.toHttp(request)));
//        redirectToLogin(request, response);
        Response<Map> resp = CommonUtils.buildResp(ResponseCode.REDIRECT_AUTH_CENTER);
        Map<String, String> map = new HashMap<>();
        map.put("location", this.getLoginUrl());
        resp.setData(map);
        ResponseUtils.responseJson(response,resp);// 直接跳转更改为返回json格式数据
        return false;
    }*/
    protected void saveRequest(ServletRequest request, String fallbackUrl) {
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
//        HttpServletRequest httpRequest = WebUtils.toHttp(request);
        session.setAttribute("authc.redirectUrl", fallbackUrl);
        /*SavedClientRequest savedRequest = new SavedClientRequest(httpRequest, backUrl);
        session.setAttribute(WebUtils.SAVED_REQUEST_KEY, savedRequest);*/
    }
    private String getCurrentUrl(HttpServletRequest request) {
        String scheme = request.getScheme();
        String domain = request.getServerName();
        int port = request.getServerPort();
        String contextPath = request.getContextPath();
        StringBuilder backUrl = new StringBuilder(scheme);
        backUrl.append("://");
        backUrl.append(domain);
        if("http".equalsIgnoreCase(scheme) && port != 80) {
            backUrl.append(":").append(String.valueOf(port));
        } else if("https".equalsIgnoreCase(scheme) && port != 443) {
            backUrl.append(":").append(String.valueOf(port));
        }
//        backUrl.append(contextPath);
        backUrl.append(request.getRequestURI());
        return backUrl.toString();
    }

}
