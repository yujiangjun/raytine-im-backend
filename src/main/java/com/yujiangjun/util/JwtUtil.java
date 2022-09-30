package com.yujiangjun.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;

public class JwtUtil {


    public static String verify(String token,String encryptPass) throws com.auth0.jwt.exceptions.JWTVerificationException{
        String userId = JWT.decode(token).getAudience().get(0);
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(encryptPass)).build();
        jwtVerifier.verify(token);
        return userId;
    }
}
