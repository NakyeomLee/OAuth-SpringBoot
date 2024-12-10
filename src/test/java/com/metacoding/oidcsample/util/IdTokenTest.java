package com.metacoding.oidcsample.util;

import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.util.Base64URL;
import com.nimbusds.jwt.SignedJWT;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.text.ParseException;
import java.util.Base64;

public class IdTokenTest {

    @Test
    public void tokenVerify_test(){
        String idToken = "eyJraWQiOiI5ZjI1MmRhZGQ1ZjIzM2Y5M2QyZmE1MjhkMTJmZWEiLCJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiI3MzBhOGVjN2U5MWYwNGFiMjY0Nzk5MWVmMzRmMWY4MSIsInN1YiI6IjM4Mjc3MzEwNTgiLCJhdXRoX3RpbWUiOjE3MzM3OTE2ODEsImlzcyI6Imh0dHBzOi8va2F1dGgua2FrYW8uY29tIiwibmlja25hbWUiOiLstZzso7ztmLgiLCJleHAiOjE3MzM4MTMyODEsImlhdCI6MTczMzc5MTY4MX0.mMT4oqaT8TCnWMaKMVthpS5JdLj0itI0wn8rZm1AketyGxOYYU2fDtcVHcUvprGFgTZ3y9QCKZd6xUMtWF7eM7LgYqAhY9uY8hR_ms11tNGbWf-67j9NSJ9TpwEbTHxtLF3xjIAQOpaACRmwpqvUXn5GF_ct2ko_35LOtOOHxjGKMHs219Obb85sbEO-Rwlqi3qXEc79myfYOB1FdaOrxyG_NJtxeKHJPwzcfWo2HGdkZaf31k0XpZSYQug1yiM1L3NzVpe7_rWhg_ku_k0mp7IyS7AoLDOyceUl6kmck4XP7gVEZtKdaViVZqKA_Y6xbO7aMfNYDdnG_FaanhzBLg";
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
                System.out.println("Payload : "+signedJWT.getPayload());
            }else{
                System.out.println("검증에 실패하였습니다.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
