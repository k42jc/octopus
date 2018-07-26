package com.dafy.octopus.web.core.notify;

import java.io.Serializable;

/**
 * 通知消息公用报文
 * Created by liaoxudong
 * Date:2018/6/25
 */

public class Msg implements Serializable{

    private static final long serialVersionUID = 964817229063864156L;

    // 接收人 多个使用逗号分隔
    private String receiver;
    // 主题
    private String subject;
    // 内容
    private String content;

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
