package com.dafy.octopus.auth.service;


import com.dafy.common.po.Response;
import com.dafy.octopus.web.core.dto.User;
import com.dafy.octopus.web.core.domain.Request;

/**
 * 登录相关
 * Created by liaoxudong
 * Date:2018/1/30
 */

public interface IUserService {


    /**
     * 登录
     * @param request username,password
     * @return
     */
    Response login(Request request);


    /**
     * 获取用户信息
     * @param userName
     * @return
     */
    User getUser(String userName);

    /**
     * 获取已登录用户的全部授权基础信息
     * @return
     */
    Response getInfo();

    /**
     * 登出
     * @return
     */
    Response logout();

    /*
     * 查询所有用户
     * @param currentPage
     * @param pageCount
     * @return
    Response listUser(int currentPage, int pageCount);*/

    Response findUserInfos(int currentPage, int pageSize, Request request);

    /**
     * 获取所有角色信息
     * @return
     */
    Response getAllRoles();

    /**
     * 新增用户
     * @param request
     * @return
     */
    Response add(Request request);

    /**
     * 更新用户信息
     * @param request
     * @return
     */
    Response update(Request request);

    /**
     * 删除用户
     * @param request
     * @return
     */
    Response delete(User request);

    /**
     * 单独修改密码接口
     * @param request
     * @return
     */
    Response updatePwd(Request request);

    /**
     * 单独的启用禁用用户
     * @param userId 用户id
     * @param status 状态值 1启用 0禁用
     * @return
     */
    Response enable(long userId, int status);
}
