package com.metacoding.oidcsample.auth;

import com.metacoding.oidcsample.user.User;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class AuthController {

    private final HttpSession session;

    // 인증이 필요한 페이지 /board/**
    @GetMapping("/auth")
    public String list(){

        User sessionUser = (User) session.getAttribute("sessionUser");
        if(sessionUser == null){
            throw new RuntimeException("로그인을 하고 진입하세요");
        }

        return "auth/list";
    }
}
