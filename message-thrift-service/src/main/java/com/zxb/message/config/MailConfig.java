package com.zxb.message.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

/**
 * class
 *
 * @author Mr.zxb
 * @date 2019-10-27 11:09
 */
@Configuration
public class MailConfig {

    @Value("${mail.smtp.host}")
    private String host;
    @Value("${mail.smtp.username}")
    private String username;
    @Value("${mail.smtp.password}")
    private String password;
    @Value("${mail.smtp.defaultEncoding}")
    private String encoding;
    @Value("${mail.smtp.auth}")
    private String auth;
    @Value("${mail.smtp.timeout}")
    private String timeout;

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setUsername(username);
        mailSender.setPassword(password);
        mailSender.setDefaultEncoding(encoding);
        Properties properties = new Properties();
        properties.setProperty("mail.smtp.auth", auth);
        properties.setProperty("mail.smtp.timeout", timeout);
        mailSender.setJavaMailProperties(properties);
        return mailSender;
    }

}
