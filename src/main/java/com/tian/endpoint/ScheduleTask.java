//package com.tian.endpoint;
//
//import lombok.extern.slf4j.Slf4j;
//import org.apache.activemq.command.ActiveMQTextMessage;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jms.annotation.JmsListener;
//import org.springframework.jms.core.JmsTemplate;
//import org.springframework.jms.core.MessageCreator;
//import org.springframework.jms.support.converter.SimpleMessageConverter;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import javax.jms.Destination;
//import javax.jms.JMSException;
//import javax.jms.Message;
//import javax.jms.Session;
//import java.time.LocalDateTime;
//import java.time.chrono.Chronology;
//import java.time.format.DateTimeFormatter;
//import java.time.format.DateTimeFormatterBuilder;
//import java.time.format.FormatStyle;
//import java.util.Enumeration;
//import java.util.Locale;
//
///**
// * @author ﻿jinzhu.tian@onerway.com
// * @date 2020/4/8
// * 说明：
// */
////@Component
//@Slf4j
//public class ScheduleTask {
//
//    @Autowired
//    JmsTemplate jmsTemplate;
//
//    @Scheduled(cron = "*/10 * * * * ?")
//    public void sendMsg() {
//        jmsTemplate.send("tian", new MessageCreator() {
//            @Override
//            public Message createMessage(Session session) throws JMSException {
//                log.info("发送消息");
//                ActiveMQTextMessage message = new ActiveMQTextMessage();
//                DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//                message.setText(("现在时间：" + LocalDateTime.now().format(format)));
//                return message;
//            }
//        });
//    }
//
//    @JmsListener(destination = "tian")
//    public void receiveMsg(Message content) {
//
//            //String msg = ((ActiveMQTextMessage) jmsTemplate.receive("tian")).getText();
//        try {
//            log.info("接收消息：" + smc.fromMessage(content));
//        } catch (JMSException e) {
//            e.printStackTrace();
//        }
//    }
//
//    SimpleMessageConverter smc = new SimpleMessageConverter();
//
//
//    public static void main(String[] args) {
//        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        System.out.println(LocalDateTime.now().format(format));
//    }
//}
