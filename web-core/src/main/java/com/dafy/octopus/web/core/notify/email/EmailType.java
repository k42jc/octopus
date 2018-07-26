package com.dafy.octopus.web.core.notify.email;

/**
 * Created by liaoxudong
 * Date:2017/11/22
 */

public enum EmailType {
    // 简单文本邮件、HTML邮件、模板邮件(仅支持freemarker模板)、带附件的邮件、静态资源邮件(如图片,PDF等)
    SIMPLE,HTML,TEMPLATE,ATTACHMENT,INLINE
}
