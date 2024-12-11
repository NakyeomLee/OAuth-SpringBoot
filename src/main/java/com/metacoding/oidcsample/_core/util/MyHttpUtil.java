package com.metacoding.oidcsample._core.util;

import com.metacoding.oidcsample.user.UserResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class MyHttpUtil {

    public static UserResponse.KakaoDTO post(String code){
        String redirectUri = "http://localhost:8080/oauth";
        String clientId = "730a8ec7e91f04ab2647991ef34f1f81";

        RestTemplate restTemplate = new RestTemplate();
        String url = "https://kauth.kakao.com/oauth/token";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

        // 요청 바디 설정
        String requestBody = """
                grant_type=authorization_code&client_id=${clientId}&redirect_uri=${redirectUri}&code=${code}
                """.replace("${clientId}", clientId)
                .replace("${redirectUri}", redirectUri)
                .replace("${code}", code);

        // HttpEntity에 헤더와 바디 추가
        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

        // POST 요청
        ResponseEntity<UserResponse.KakaoDTO> response = restTemplate.exchange(url, HttpMethod.POST, request, UserResponse.KakaoDTO.class);

        // 응답 출력
        UserResponse.KakaoDTO kakaoDTO =  response.getBody();
        return kakaoDTO;
    }
}
