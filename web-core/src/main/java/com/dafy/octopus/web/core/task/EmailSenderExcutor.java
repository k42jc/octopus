package com.dafy.octopus.web.core.task;

import com.dafy.common.exception.NotifyExcetpion;
import com.dafy.common.task.IAlarm;
import com.dafy.common.task.IExecutor;
import com.dafy.common.util.HttpUtils;
import com.dafy.common.util.StringUtils;
import com.dafy.octopus.web.core.context.ApplicationContextProvider;
import com.dafy.octopus.web.core.notify.email.Email;
import com.dafy.octopus.web.core.utils.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.AuthenticationFailedException;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.*;

/**
 * 邮件发送
 * Created by liaoxudong
 * Date:2017/11/22
 */

public class EmailSenderExcutor implements IExecutor<Email>,IAlarm<Email> {
    private static final Logger logger = LoggerFactory.getLogger(EmailSenderExcutor.class);
    private static JavaMailSender javaMailSender = ApplicationContextProvider.getBean(JavaMailSender.class);
    @Override
    public void alarm(Email email, boolean isSync) {

    }

    @Override
    public void execute(Email email) throws NotifyExcetpion {
        logger.debug("发件信息：{}",email);
        MimeMessage mimeMessage = null;
        MimeMessageHelper helper = null;
        try {
            switch (email.getContentType()) {
                case SIMPLE: // 简单文本邮件
                    logger.info("发送文本邮件");
                    SimpleMailMessage message = new SimpleMailMessage();
                    message.setFrom(email.getSender());
                    message.setTo(email.getReceiver().split(","));
                    if(!StringUtils.isEmpty(email.getDuplicate()))
                        message.setCc(email.getDuplicate().split(","));
                    message.setSubject(email.getSubject());
                    message.setText(email.getContent());
                    javaMailSender.send(message);
                    logger.debug("邮件发送成功");
                    break;
                case HTML:
                    logger.info("发送HTML邮件");
                    mimeMessage = javaMailSender.createMimeMessage();
                    helper = new MimeMessageHelper(mimeMessage, true);
                    helper.setFrom(email.getSender());
//                    helper.setTo(email.getReceiver());
                    helper.setTo(email.getReceiver().split(","));
                    helper.setSubject(email.getSubject());
                    if(!StringUtils.isEmpty(email.getDuplicate()))
                        helper.setCc(email.getDuplicate().split(","));

                    /*StringBuffer sb = new StringBuffer();
                    sb.append("<h1>大标题-h1</h1>")
                            .append("<p style='color:#F00'>红色字</p>")
                            .append("<p style='text-align:right'>右对齐</p>");*/
                    helper.setText(email.getContent(), true);
                    javaMailSender.send(mimeMessage);
                    logger.debug("邮件发送成功");
                    break;
                case ATTACHMENT:
                    logger.info("发送附件邮件");
                    mimeMessage = javaMailSender.createMimeMessage();
                    helper = new MimeMessageHelper(mimeMessage, true);
                    helper.setFrom(email.getSender());
//                    helper.setTo(email.getReceiver());
                    helper.setTo(email.getReceiver().split(","));
                    if(!StringUtils.isEmpty(email.getDuplicate()))
                        helper.setCc(email.getDuplicate().split(","));
                    helper.setSubject(email.getSubject());
                    helper.setText(email.getContent());
                    //注意项目路径问题，自动补用项目路径
//                    try {
                    if (!email.getFileUrls().isEmpty()) {
                            /*for (File attachment : email.getAttachment()) {
                                file = new FileSystemResource(attachment);
                                helper.addAttachment(file.getFilename(), file);
                            }*/
                        for (int i = 0; i < email.getFileUrls().size(); i++) {
                            String fileUrl = email.getFileUrls().get(i);
                            byte[] download = HttpUtils.download(fileUrl, null);
//                                File file = FileUtils.getFileFromBytes(download, fileUril.substring(fileUril.lastIndexOf(".")));
                            helper.addAttachment(fileUrl.substring(fileUrl.lastIndexOf("/") + 1), new ByteArrayResource(download));
                        }
                    }
                    //加入邮件
                    javaMailSender.send(mimeMessage);
                    logger.debug("邮件发送成功");
                    /*} catch (Exception e) {
                        logger.error("邮件发送失败，切换账户发送："+e.getMessage(),e);
                    }*/
                    break;
                case INLINE:
                    logger.info("发送静态文件邮件");
                    mimeMessage = javaMailSender.createMimeMessage();
                    helper = new MimeMessageHelper(mimeMessage, true);
                    helper.setFrom(email.getSender());
//                    helper.setTo(email.getReceiver());
                    helper.setTo(email.getReceiver().split(","));
                    if (!StringUtils.isEmpty(email.getDuplicate()))
                        helper.setCc(email.getDuplicate().split(","));
                    helper.setSubject(email.getSubject());
//                    helper.setText(email.getContent());
                    //第二个参数指定发送的是HTML格式,同时cid:是固定的写法
//                    helper.setText("<html><body><img src='cid:picture' /></body></html>", true);
                    helper.setText(String.format("<html><body><img src='%s' /></body></html>", email.getFileUrls().get(0)), true);
                    // 图片只支持第一个文件
//                    file = new FileSystemResource(email.getAttachment().get(0));
//                    try {
                        /*byte[] download = HttpUtils.download(email.getFileUrls().get(0), null);
                        helper.addInline("picture", new InputStreamResource(new ByteArrayInputStream(download)));*/
                        javaMailSender.send(mimeMessage);
                        logger.debug("邮件发送成功");
                    /*} catch (Exception e) {
                        logger.error("邮件发送失败：" + e.getMessage(), e);
                    }*/
                    break;
                case TEMPLATE:
                    logger.warn("暂未实现");
                    CommonUtils.throwException("warn", "暂未实现");
            }
        } catch (AuthenticationFailedException e) {// 如果是邮件发送超额导致的邮箱地址被服务商限制，则切换邮箱发送
            /*if(!"prod".equals(env.getActiveProfiles()[0])) { //FIXME 只针对测试环境大量邮件问题 20180327

                logger.error("邮件发送失败，当前账户受限，切换账户："+e.getMessage());
                javaMailSender = reacquireMailConfig(email);
                execute(email);
            }*/
            logger.error("邮件发送失败：",e);
        } catch (MessagingException e) {
            logger.error("邮件发送失败："+e.getMessage(), e);
//            CommonUtils.throwException(ExceptionCode.EMAIL_SEND_EXCEPTION);
        } catch (MailException e) {
            logger.error("邮件发送失败："+e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            logger.error("邮件发送失败："+e.getMessage(),e);
            throw e;
        }
    }

    private static final Environment env = ApplicationContextProvider.getBean(Environment.class);
    private static final List<JavaMailSender> MAIL_LIST = new ArrayList<>();

    /**
     * 读取配置的备用发件箱
     * @param email
     * @return
     */
    private JavaMailSender reacquireMailConfig(Email email) {
        if(MAIL_LIST.size() <= 1){
            synchronized (MAIL_LIST) {
                if (MAIL_LIST.size() == 0) {// 默认发送方只添加一次
                    MAIL_LIST.add(javaMailSender);
                }
                if(MAIL_LIST.size() == 1){// 备用配置也只加载一次
                    List<String> hosts = Arrays.asList(env.getProperty("config.notify.mail.host").split(","));
                    List<String> usernames = Arrays.asList(env.getProperty("config.notify.mail.username").split(","));
                    List<String> passwords = Arrays.asList(env.getProperty("config.notify.mail.password").split(","));
                    List<String> ports = Arrays.asList(env.getProperty("config.notify.mail.port").split(","));
                    for(int i=0;i<hosts.size();i++) {

                        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
                        javaMailSender.setHost(hosts.get(i));
                        javaMailSender.setUsername(usernames.get(i));
                        javaMailSender.setPassword(passwords.get(i));
                        Integer port = Integer.valueOf(ports.get(i));
                        javaMailSender.setPort(port);

                        // 放在循环内防止存在ssl发件配置的邮箱
                        Properties props = new Properties();
                        props.put("mail.smtp.connectiontimeout", 5000);
                        props.put("mail.smtp.timeout", 3000);
                        props.put("mail.smtp.writetimeout", 5000);
                        props.put("mail.smtp.auth", "true");
                        props.put("mail.smtp.starttls.enable", "true");
                        props.put("mail.smtp.starttls.required", "true");
                        if (port == 465) {// 使用ssl加密的配置
                            props.put("mail.smtp.ssl.enable", "true");
                            props.put("mail.smtp.socketFactory.port", 465);
                            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                            props.put("mail.smtp.socketFactory.fallback", "false");
                        }
                        javaMailSender.setJavaMailProperties(props);
                        MAIL_LIST.add(javaMailSender);
                    }
                }
            }
        }
        logger.debug("随机获取邮件发送人");
        JavaMailSender randomSender = MAIL_LIST.get(new Random().nextInt(MAIL_LIST.size()));
        email.setSender(((JavaMailSenderImpl)randomSender).getUsername());
        return randomSender;
    }

    @Override
    public void abortPolicy(Email body,Throwable e) {
        /*Task task = new Task();
        task.setType(TaskType.QUEUE.getCode());
        task.setClassName(body.getClass().getName());
        task.setJsonData(JsonUtils.toJson(body));
        task.setStackTrace(e.getMessage() + ":" +e.getStackTrace().toString());
        taskAbortMapper.insert(task);*/
        logger.warn("邮件发送失败，丢弃：{},{}",e.getMessage(),body);
    }
}
