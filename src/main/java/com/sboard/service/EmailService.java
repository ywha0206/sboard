package com.sboard.service;

import jakarta.mail.Message;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;


    @Value("${spring.mail.username}")
    private String sender;

    public int sendEmailCode(HttpSession session, String receiver){

        MimeMessage message = mailSender.createMimeMessage();

        //인증코드 생성
        int code = ThreadLocalRandom.current().nextInt(100000,1000000);
        session.setAttribute("code", String.valueOf(code));

        //이메일 기본 정보
        String title = "jboard 인증번호입니다.";
        String content = "<h3> 인증 코드는 <h1>"+ code +"</h1> 입니다.</h3>";

        try {
            message.setFrom(new InternetAddress(sender, "보내는 사람", "UTF-8"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(receiver));
            message.setSubject(title);
            message.setContent(content, "text/html;charset=UTF-8");

            // 메일 발송
            mailSender.send(message);

        }catch(Exception e){
            log.error("sendEmailConde : " + e.getMessage());
        }
        return code;
    }
}
