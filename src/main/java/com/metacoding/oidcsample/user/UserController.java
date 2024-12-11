package com.metacoding.oidcsample.user;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;
    private final HttpSession session;

    @GetMapping("/oauth")
    public String oauth(@RequestParam("code") String code){
        User sessionUser = userService.카카오로그인(code);
        session.setAttribute("sessionUser", sessionUser);
        return "redirect:/";
    }

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @PostMapping("/login")
    public String join(UserRequest.LoginDTO reqDTO){
        User sessionUser = userService.로그인(reqDTO);
        session.setAttribute("sessionUser", sessionUser);
        return "redirect:/";
    }

    @PostMapping("/join")
    public String join(UserRequest.JoinDTO reqDTO){
        userService.회원가입(reqDTO);
        return "redirect:/login-form";
    }

    @GetMapping("/join-form")
    public String joinForm(){
        return "user/join-form";
    }

    @GetMapping("/login-form")
    public String loginForm(){
        return "user/login-form";
    }

    @GetMapping("/logout")
    public String logout(){
        session.invalidate();
        return "redirect:/login-form";
    }
}
