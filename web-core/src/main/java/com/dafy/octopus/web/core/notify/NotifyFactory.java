package com.dafy.octopus.web.core.notify;

import com.dafy.octopus.web.core.notify.email.Email;
import com.dafy.octopus.web.core.notify.email.EmailNotify;

/**
 * 事件通知
 * 由调用者指定调用策略
 * Created by liaoxudong
 * Date:2018/6/25
 */

public class NotifyFactory {

    private static NotifyStrategy strategy = new EmailNotify();

    public static void sendEmail(Email email) {
        strategy.notify(email);
    }

    private NotifyFactory() {
    }

}
