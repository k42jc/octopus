package com.dafy.octopus.web.core.task;

import com.dafy.cache.redis.queue.TaskProducer;
import com.dafy.common.task.IExecutor;
import com.dafy.common.task.IMessageProducer;
import com.dafy.octopus.web.core.notify.email.Email;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 任务处理工厂
 * Created by liaoxudong
 * Date:2017/11/17
 */

public class TaskFatory {
    private static final Logger logger = LoggerFactory.getLogger(TaskFatory.class);


    private static final IExecutor<Email> emailSenderExcutor = new EmailSenderExcutor();
    private static final IMessageProducer<Email> emailSenderTask = new TaskProducer<>("EMAIL_QUEUE", emailSenderExcutor);

    /**
     * 异步发送邮件
     * @param email
     */
    public static void sendEmail(Email email) {
        emailSenderTask.push(email);
    }

}
