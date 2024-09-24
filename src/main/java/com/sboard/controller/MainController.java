package com.sboard.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@RequiredArgsConstructor
@Controller
public class MainController {


    @GetMapping(value = {"/", "/index"})
    public String index(Authentication authentication){

        if(authentication !=null && authentication.isAuthenticated()){
            // 로그인 했을 경우
            return "redirect:/article/list";

        }else {
            // 로그인 안했을 경우
            return "redirect:/user/login";
        }

    }
}
