package com.dafy.octopus.auth.servlet;

import com.dafy.common.exception.ServerException;
import com.dafy.common.util.StringUtils;
import com.dafy.octopus.web.core.utils.RequestUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by liaoxudong
 * Date:2017/11/14
 */

@Controller
public class SwaggerApiDoc {

    @Value("${config.demo.url.index}")
    private String indexUrl;
    @Value("${config.demo.url.swagger-ui}")
    private String swaggerUrl;
    @Value("${config.demo.url.swagger-ui2}")
    private String swaggerUrl2;
    @Value("${config.demo.url.swagger-ui3}")
    private String swaggerUrl3;

    @RequestMapping("/")
    public ModelAndView index(){
        ModelAndView mv = new ModelAndView();
        mv.addObject("indexUrl", indexUrl);
        mv.addObject("swaggerUrl", swaggerUrl);
        mv.addObject("swaggerUrl2", swaggerUrl2);
        mv.addObject("swaggerUrl3", swaggerUrl3);
        mv.setViewName("index");
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            mv.addObject("currentUser", subject.getPrincipals().getPrimaryPrincipal());
        }
        return mv;
    }

    @RequestMapping("/login")
    public String login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            WebUtils.redirectToSavedRequest(request, response, "/");
            return "index";
        }
        return "login";
    }

    @RequestMapping("/doLogout")
    public String doLogout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        WebUtils.redirectToSavedRequest(request, response, "/");
        return "index";
    }

    @RequestMapping("/doLogin")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        @RequestParam("rememberMe") boolean rememberMe, HttpServletRequest request, HttpServletResponse response) throws IOException {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            throw new ServerException("warn","当前为登录状态请勿重复登录");
        }
        UsernamePasswordToken token = new UsernamePasswordToken(username,password,rememberMe, RequestUtils.getIpAddress(request));
//        try{
        subject.login(token);
        Session session = subject.getSession();
        Object redirectUrl = session.getAttribute("authc.redirectUrl");
        if (!StringUtils.isEmpty(redirectUrl)) {
            WebUtils.redirectToSavedRequest(request, response, redirectUrl.toString());
        }
        /*} catch (AuthenticationException e) {
            logger.error("登录失败：",e);
            CommonUtils.throwException(ResponseCode.LOGIN_ERROR);
        }*/
        return "index";
    }

//    @RequestMapping("/doc")
//    public String demoDoc(){
//        return "swagger-ui.html";
//    }
}
