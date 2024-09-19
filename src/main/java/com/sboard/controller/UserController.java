package com.sboard.controller;

import com.sboard.dto.TermsDTO;
import com.sboard.dto.UserDTO;
import com.sboard.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.connector.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;



    @GetMapping("/user/login")
    public String login(){
        return "/user/login";
    }

    @GetMapping("/user/terms")
    public String terms(Model model){
        TermsDTO terms = userService.selectTerms();
        model.addAttribute("terms", terms);

        return "/user/terms";
    }

    @GetMapping("/user/register")
    public String register(){
        return "/user/register";
    }

    @GetMapping( "/user/checkUser")
    public ResponseEntity<Map<String,Object>> checkUser(@RequestParam("uid")String uid){
        Map<String, Object> response = new HashMap<>();
        boolean exists = userService.checkUserIdExists(uid);
        response.put("result", exists ? 1 : 0);
        return ResponseEntity.ok(response);
    }


    @PostMapping("/user/register")
    public String register(UserDTO userDTO){
        userService.insertUser(userDTO);
        return "redirect:/user/login";
    }

    @GetMapping("/user/success")//로그인한 사용자 정보는 Authentication에 저장(세션이랑 같은 개념) 에 저장됨
    public String success(){
        return "/user/success";
    }
}
