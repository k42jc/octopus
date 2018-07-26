package com.dafy.octopus.web.core.shiro.listener;

import com.dafy.common.util.DateTimeUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 监控会话
 * Created by liaoxudong
 * Date:2018/4/16
 */

public class SystemSessionListener implements SessionListener{
    private static final Logger logger = LoggerFactory.getLogger(SystemSessionListener.class);

    @Override
    public void onStart(Session session) {
        logger.info("会话创建：{},超时时间：{}",session.getId(),session.getTimeout());
    }

    @Override
    public void onStop(Session session) {
        logger.info("会话停止：{},创建时间：{},最后操作时间：{}",session.getId(), DateTimeUtils.formatDatetime1(session.getStartTimestamp()),session.getLastAccessTime());
    }

    @Override
    public void onExpiration(Session session) {
        logger.info("会话超时：{},创建时间：{},最后操作时间：{}",session.getId(), DateTimeUtils.formatDatetime1(session.getStartTimestamp()),session.getLastAccessTime());
    }
}
