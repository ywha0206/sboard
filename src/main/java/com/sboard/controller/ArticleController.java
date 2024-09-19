package com.sboard.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class ArticleController {

    @GetMapping("/article/list")
    public String list(){
        return "/article/list";
    }

    @GetMapping("/article/modify")
    public String modify(){
        return "/article/modify";
    }

    @GetMapping("/article/view")
    public String view(){
        return "/article/view";
    }

    @GetMapping("/article/write")
    public String write(){
        return "/article/write";
    }
}
