package com.sboard.controller;

import com.sboard.config.AppInfo;
import com.sboard.dto.TermsDTO;
import com.sboard.dto.UserDTO;
import com.sboard.service.EmailService;
import com.sboard.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

@Log4j2
@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;
    private final EmailService emailService;

    private final AppInfo appInfo;

    @Value("${spring.application.version}")
    private String appVersion;


    @GetMapping("/user/login")
    public String login(Model model) {
        model.addAttribute(appInfo);
        return "/user/login";
    }

    @GetMapping("/user/terms")
    public String terms(Model model){
        TermsDTO terms = userService.selectTerms();
        model.addAttribute("terms", terms);
        model.addAttribute(appInfo);
        return "/user/terms";
    }

    @GetMapping("/user/register")
    public String register(Model model){
        model.addAttribute(appInfo);
        return "/user/register";
    }

    @GetMapping( "/user/checkUser")
    public ResponseEntity<Boolean> checkUser(@RequestParam("uid")String uid){
        boolean exists = userService.checkUserIdExists(uid);
        return ResponseEntity.ok(exists);
    }

    @GetMapping( "/user/checkUserNick")
    public ResponseEntity<Map<String,Object>> checkUserNick(@RequestParam("nick")String nick)throws UnsupportedEncodingException {
        Map<String, Object> response = new HashMap<>();
        String decodedNick = URLDecoder.decode(nick, "UTF-8");
        boolean exists = userService.checkUserNickExists(decodedNick);
        log.info("exists : "+exists);
        response.put("result", exists ? 1 : 0);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping( "/user/checkUserHp")
    public ResponseEntity<Map<String,Object>> checkUserHp(@RequestParam("hp")String hp){
        Map<String, Object> response = new HashMap<>();
        boolean exists = userService.checkUserHpExists(hp);
        log.info("exists : "+exists);
        response.put("result", exists ? 1 : 0);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping( "/user/checkUserEmail")
    public ResponseEntity<Map<String,Object>> checkUserEmail(HttpSession session,
            @RequestParam("email")String email, Model model){
        Map<String, Object> response = new HashMap<>();
        boolean exists = userService.checkUserEmailExists(email);
        response.put("result", exists ? 1 : 0);

        //이메일 인증번호 발송하기
        int code =  emailService.sendEmailCode(session, email);
        model.addAttribute("code", code);

        return ResponseEntity.ok(response);
    }
    @ResponseBody
    @PostMapping("/checkUserEmail")
    public ResponseEntity<?> checkEmail(HttpSession session, @RequestBody Map<String, String> jsonData) {
        String receiveCode = jsonData.get("code");
        String sessionCode = (String) session.getAttribute("code");
        if(sessionCode.equals(receiveCode)){
            // Json 생성
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("result", true);

            return ResponseEntity.ok().body(resultMap);
        }else{
            // Json 생성
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("result", false);

            return ResponseEntity.ok().body(resultMap);
        }
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
