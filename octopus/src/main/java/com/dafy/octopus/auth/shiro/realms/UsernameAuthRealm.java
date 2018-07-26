package com.dafy.octopus.auth.shiro.realms;

import com.dafy.octopus.web.core.dto.User;
import com.dafy.octopus.auth.service.IUserService;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * 用户名认证
 * Created by liaoxudong
 * Date:2018/4/11
 */

public class UsernameAuthRealm extends AuthCenterAuthRealm {

    public UsernameAuthRealm(CredentialsMatcher credentialsMatcher) {
        super(credentialsMatcher);
    }

    @Override
    @Autowired
    protected void setUserService(IUserService userService) {
        super.setUserService(userService);
    }

    @Override
    protected List<String> userNames(User user) {
        String[] userNames = {user.getUserName(),user.getPhoneNo(),user.getEmail()};
        return Arrays.asList(userNames);
    }

    public static String convertUsername(Collection<String> userNames) {
        if (userNames == null || userNames.isEmpty()) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        userNames.forEach(userName -> {
            builder.append(userName).append(":");
        });
        return builder.toString();
    }
}
