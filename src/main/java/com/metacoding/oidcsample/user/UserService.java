package com.metacoding.oidcsample.user;

import com.metacoding.oidcsample._core.util.MyHttpUtil;
import com.metacoding.oidcsample._core.util.MyRSAUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public void 회원가입(UserRequest.JoinDTO reqDTO){
        userRepository.save(reqDTO.toEntity());
    }

    public User 로그인(UserRequest.LoginDTO reqDTO){
        User sessionUser = userRepository.findByUsername(reqDTO.getUsername())
                .orElseThrow(() -> new RuntimeException("유저네임을 찾을 수 없습니다"));

        if(!sessionUser.getPassword().equals(reqDTO.getPassword())){
            throw new RuntimeException("패스워드가 일치하지 않습니다");
        }
        return sessionUser;
    }

    public User 카카오로그인(String code) {
        // 1. 카카오 로그인 요청
        UserResponse.KakaoDTO kakaoDTO = MyHttpUtil.post(code);

        // 2. id token 검증
        UserResponse.IdTokenDTO idTokenDTO = MyRSAUtil.verify(kakaoDTO.getIdToken());

        // 3. 회원가입 유무 확인
        String username = "kakao_"+idTokenDTO.getSub();
        Optional<User> userOP = userRepository.findByUsername(username);

        // 4. 안되어있다면 강제 회원가입
        if(userOP.isEmpty()){
            User user = User.builder()
                    .username(username)
                    .password(UUID.randomUUID().toString())
                    .provider(ProviderEnum.KAKAO)
                    .build();
            User userPS = userRepository.save(user);
            return userPS;
        }

        // 5. User 객체 리턴
        return userOP.get();
    }
}
