package com.dafy.octopus.web.core.notify.email;

import com.dafy.octopus.web.core.notify.Msg;

import java.io.Serializable;
import java.util.List;

/**
 * 邮件类型通知报文
 * Created by liaoxudong
 * Date:2018/6/25
 */

public class Email extends Msg implements Serializable{

    private static final long serialVersionUID = -143944323015991598L;

    // 发件人
    private String sender;

    //抄送人 多个使用逗号分隔
    private String duplicate;

    // 附件或静态文件内容列表 文件url
    private List<String> fileUrls;

    // 邮件文档类型 普通文字
    private EmailType contentType = EmailType.SIMPLE;

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getDuplicate() {
        return duplicate;
    }

    public void setDuplicate(String duplicate) {
        this.duplicate = duplicate;
    }

    public List<String> getFileUrls() {
        return fileUrls;
    }

    public void setFileUrls(List<String> fileUrls) {
        this.fileUrls = fileUrls;
    }

    public EmailType getContentType() {
        return contentType;
    }

    public void setContentType(EmailType contentType) {
        this.contentType = contentType;
    }
}
