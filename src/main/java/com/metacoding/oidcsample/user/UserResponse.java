package com.metacoding.oidcsample.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
/**
 * @JsonProperty("email_address")
 * spring.jackson.property-naming-strategy=SNAKE_CASE
 */
public class UserResponse {

    @Data
    public static class IdTokenDTO {
        private String sub;
        private String nickname;

        // 안씀
        private String aud;
        @JsonProperty("auth_time")
        private String authTime;
        private String iss;
        private String exp;
        private String iat;
    }

    @Data
    public static class KakaoDTO {
        @JsonProperty("access_token")
        private String accessToken;
        @JsonProperty("id_token")
        private String idToken;
    }
}
