package com.metacoding.oidcsample._core.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.metacoding.oidcsample.user.UserResponse;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.util.Base64URL;
import com.nimbusds.jwt.SignedJWT;

import java.math.BigInteger;
import java.util.Base64;

public class MyRSAUtil {

    public static UserResponse.IdTokenDTO verify(String idToken){
        String n = "qGWf6RVzV2pM8YqJ6by5exoixIlTvdXDfYj2v7E6xkoYmesAjp_1IYL7rzhpUYqIkWX0P4wOwAsg-Ud8PcMHggfwUNPOcqgSk1hAIHr63zSlG8xatQb17q9LrWny2HWkUVEU30PxxHsLcuzmfhbRx8kOrNfJEirIuqSyWF_OBHeEgBgYjydd_c8vPo7IiH-pijZn4ZouPsEg7wtdIX3-0ZcXXDbFkaDaqClfqmVCLNBhg3DKYDQOoyWXrpFKUXUFuk2FTCqWaQJ0GniO4p_ppkYIf4zhlwUYfXZEhm8cBo6H2EgukntDbTgnoha8kNunTPekxWTDhE5wGAt6YpT4Yw";
        String e = "AQAB";

        BigInteger bin = new BigInteger(1, Base64.getUrlDecoder().decode(n));
        BigInteger bie = new BigInteger(1, Base64.getUrlDecoder().decode(e));

        RSAKey rsaKey = new RSAKey.Builder(Base64URL.encode(bin), Base64URL.encode(bie)).build();
        try {
            // 1. 파싱
            SignedJWT signedJWT = SignedJWT.parse(idToken);

            // 2. 검증
            RSASSAVerifier verifier = new RSASSAVerifier(rsaKey.toRSAPublicKey());

            if(signedJWT.verify(verifier)){
                System.out.println("ID Token을 검증하였습니다");
                String payload = signedJWT.getPayload().toString();

                ObjectMapper om = new ObjectMapper();
                UserResponse.IdTokenDTO idTokenDTO = om.readValue(payload, UserResponse.IdTokenDTO.class);
                return idTokenDTO;
            }else{
                throw new RuntimeException("id토큰 검증 실패");
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }
}
