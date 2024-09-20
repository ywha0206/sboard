package com.sboard.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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



    public String sendEmailCode(String email){

        //인증코드 생성
        int code = ThreadLocalRandom.current().nextInt(100000,1000000);

        //이메일 기본 정보
        String title = "jboard 인증번호입니다.";
        String content = "<h3> 인증 코드는 <h1>"+ code +"</h1> 입니다.</h3>";
        String sender = "ppyyhh3285@gmail.com";
        String appPass = "fbax fmxo pxup eook"; //google 앱 비밀번호



        return null;
    }
}
