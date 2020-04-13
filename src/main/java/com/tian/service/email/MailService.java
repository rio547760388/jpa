package com.tian.service.email;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.naming.factory.MailSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.*;
import javax.mail.internet.MimeMessage;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Properties;
import java.util.stream.StreamSupport;

/**
 * @author ﻿jinzhu.tian@onerway.com
 * @date 2020/4/10
 * 说明：
 */
@Component
@Slf4j
public class MailService {

    @Value("${spring.mail.username:null}")
    private String account;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private TemplateEngine templateEngine;

    @Async
    public void senSth(String sth) {
        try {
            Context context = new Context();
            context.setVariable("sth", sth);
            String html = templateEngine.process("mail/mailSample", context);
            doSend("Sample", html, "547760388@qq.com", true, null, null);
        } catch (MessagingException e) {
            log.error(e.getMessage(), e);
        }
    }

    private void doSend(String subject, String text, String to, boolean isHtml, String fileName, InputStream attachment) throws MessagingException {
        MimeMessage mailMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mailMessage);

        helper.setFrom(account);
        helper.setTo(to.split(","));
        helper.setSubject(subject);
        helper.setText(text, isHtml);
        if (StringUtils.isNotEmpty(fileName) && attachment != null) {
            helper.addAttachment(fileName, new InputStreamResource(attachment));
        }
        javaMailSender.send(mailMessage);
        log.info("邮件发送成功{}", to);

    }

    @Async
    public void receiveEmail() {
        try {
            Properties properties = new Properties();
            properties.setProperty("mail.store.protocol", "smtp");
            properties.setProperty("mail.smtp.host", "smtp.qq.com");
            properties.setProperty("mail.smtp.port", "465");

            Session session = Session.getDefaultInstance(properties);
            session.requestPasswordAuthentication(InetAddress.getByName("smtp.qq.com"), 465, "smtp", "", "547760388@qq.com");
            Store store = session.getStore("smtp");
            store.connect("smtp.qq.com", 465, "", "");
            Arrays.stream(session.getStore().getSharedNamespaces())
                    .forEach(e -> {
                        System.out.println(e.getFullName());
                    });
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
