package com.sboard.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Log4j2
@RequiredArgsConstructor
@Controller
public class MainController {


    @GetMapping(value = {"/", "/index"})
    public String index(Authentication authentication){

        log.info(authentication);

        if(authentication !=null && authentication.isAuthenticated()){
            // 로그인 했을 경우
            return "redirect:/article/list";

        }else {
            // 로그인 안했을 경우
            return "redirect:/user/login";
        }

    }
}
