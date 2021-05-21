package com.example.demo.framework.jwt.contract.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demo.framework.jwt.contract.JWTContract;

import java.time.LocalDate;
import java.util.Date;

public class SampleJWT implements JWTContract {
    @Override
    public String encode(String issue) {
//        装载信息，设置过期时间，
        //        创建jwk，保存到数据库
        String token;
        try {
            Algorithm algorithm = Algorithm.HMAC256("secret");
            token = JWT.create()
                    .withIssuer(issue)      //这里设置为用户ID
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
    public String decode(String jwt) {
        DecodedJWT result = JWT.decode(jwt);


        return null;
    }
}
