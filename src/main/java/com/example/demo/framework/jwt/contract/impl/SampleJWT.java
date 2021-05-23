package com.example.demo.framework.jwt.contract.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.impl.JWTParser;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTPartsParser;
import com.auth0.jwt.interfaces.Payload;
import com.example.demo.framework.jwt.contract.JWTContract;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class SampleJWT implements JWTContract {
    @Value("${aaa.secret}")
    String secret;

    @Override
    public String encode(Map<String, Object> params) {
        Algorithm algorithm = Algorithm.HMAC256(secret);

//        装载信息，设置过期时间，
        //        创建jwk，保存到数据库
        String token;
        try {

            token = JWT.create()
                    .withPayload(params)
                    .withIssuer("Auth0")      //这里设置为发起者
                    .withIssuedAt(new Date(System.currentTimeMillis()))
                    .withExpiresAt(new Date(System.currentTimeMillis() + 30 * 1000))    //设置过期时间 30秒后
                    .sign(algorithm);

        } catch (JWTCreationException exception) {
            //Invalid Signing configuration / Couldn't convert Claims.
            token = null;
        }
        return token;
    }

    @Override
    public Map<String, Claim> decode(String jwt) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        DecodedJWT result = JWT.decode(jwt);
        algorithm.verify(result);
        JWTPartsParser jwtParser = new JWTParser();
        return result.getClaims();
    }


}
