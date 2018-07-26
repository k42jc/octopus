package com.dafy.octopus.web.core.shiro.session;

import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SimpleSession;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

/**
 * Shiro session包装类
 * 解决dubbo rpc sessionId无法传输问题
 * Created by liaoxudong
 * Date:2018/4/18
 */

public class SessionWrapper implements Session,Serializable{


    private Session session;
    private String sessionId;

    public static Session wrapper(Session session) {
        SessionWrapper wrapper = new SessionWrapper();
        wrapper.setSession(session);
        wrapper.setSessionId(session.getId().toString());
        return wrapper;
    }

    public static Session unWrapper(Session session) {
        if (session instanceof SessionWrapper) {
            Session result = ((SessionWrapper) session).getSession();
            ((SimpleSession)result).setId(((SessionWrapper) session).getSessionId());
            return result;
        }
        return session;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public Serializable getId() {
        return session.getId();
    }

    @Override
    public Date getStartTimestamp() {
        return session.getStartTimestamp();
    }

    @Override
    public Date getLastAccessTime() {
        return session.getLastAccessTime();
    }

    @Override
    public long getTimeout() throws InvalidSessionException {
        return session.getTimeout();
    }

    @Override
    public void setTimeout(long l) throws InvalidSessionException {

    }

    @Override
    public String getHost() {
        return session.getHost();
    }

    @Override
    public void touch() throws InvalidSessionException {
        session.touch();
    }

    @Override
    public void stop() throws InvalidSessionException {
        session.stop();
    }

    @Override
    public Collection<Object> getAttributeKeys() throws InvalidSessionException {
        return session.getAttributeKeys();
    }

    @Override
    public Object getAttribute(Object o) throws InvalidSessionException {
        return session.getAttribute(o);
    }

    @Override
    public void setAttribute(Object o, Object o1) throws InvalidSessionException {
        session.setAttribute(o,o1);
    }

    @Override
    public Object removeAttribute(Object o) throws InvalidSessionException {
        return session.removeAttribute(o);
    }
}
