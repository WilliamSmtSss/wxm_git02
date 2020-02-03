package com.splan.xbet.back.backforbusiness.config;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfiguration {

    @Value("${mail.mailName}")
    public  String mailName="";
    @Value("${mail.mailPassword}")
    public  String mailPassword="";
    @Value("${mail.mailHost}")
    public  String mailHost="";
    @Value("${mail.online}")
    public  String online="";

    @Bean
    public JavaMailSenderImpl JavaMailSender(){
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(mailHost);
        mailSender.setUsername(mailName);
        mailSender.setPassword(mailPassword);
        if("1".equals(online)) {
            mailSender.setPort(465);
            Properties properties = new Properties();
            properties.setProperty("mail.smtp.auth", "true");//开启认证
            properties.setProperty("mail.debug", "true");//启用调试
            properties.setProperty("mail.smtp.timeout", "25000");//设置链接超时
            properties.setProperty("mail.smtp.port", Integer.toString(465));//设置端口
            properties.setProperty("mail.smtp.socketFactory.port", Integer.toString(465));//设置ssl端口
            properties.setProperty("mail.smtp.socketFactory.fallback", "false");
            properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            mailSender.setJavaMailProperties(properties);
        }
        return  mailSender;
    }

}
