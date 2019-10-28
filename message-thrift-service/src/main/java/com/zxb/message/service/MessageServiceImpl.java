package com.zxb.message.service;

import com.zxb.message.thrift.MessageService;
import com.zxb.message.config.EmailComponent;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


/**
 * 信息服务 Java 实现 class
 *
 * @author Mr.zxb
 * @date 2019-10-27 09:55
 */
@Service
@Slf4j
public class MessageServiceImpl implements MessageService.Iface {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private EmailComponent emailComponent;

    @Override
    public boolean sendMobileMessage(String mobile, String message) throws TException {
        log.info("Message-Service send mobile: {} message: {}", mobile, message);
        return true;
    }

    @Override
    public boolean sendEmailMessage(String email, String message) throws TException {
        log.info("Message-Service send email: {} message: {}", email, message);
        return emailComponent.sendMail(message, "钟学斌的邮件", email, javaMailSender, false);
    }
}
