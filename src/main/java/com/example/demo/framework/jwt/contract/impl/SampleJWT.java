package com.example.demo.framework.jwt.contract.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.impl.JWTParser;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTPartsParser;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.example.demo.framework.jwt.contract.JWTContract;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

@Component
public class SampleJWT implements JWTContract {
    @Value("${aaa.secret}")
    String secret;

    @Override
    public String encode(Map<String, Object> params) {
        Algorithm algorithm = Algorithm.HMAC256(secret);

//        装载信息，设置过期时间
        //        创建jwk，保存到数据库
        String token;
        try {

            token = JWT.create()
                    .withPayload(params)
                    .withIssuer("Auth0")      //这里设置为发起者
                    .withIssuedAt(new Date(System.currentTimeMillis()))
                    .withExpiresAt(new Date(System.currentTimeMillis() + 300 * 1000))    //设置过期时间 5分钟后
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
        JWTVerifier verifier = JWT.require(algorithm)
                .acceptLeeway(1) // 1 sec for nbf, iat and exp
                .build();// 为了超时校验而使用verifier ， 不用超时可以直接用algorithm.verify(DecodeJwt)
        DecodedJWT result = JWT.decode(jwt);
        verifier.verify(result);
//        algorithm.verify(result);
        JWTPartsParser jwtParser = new JWTParser();
        return result.getClaims();
    }


}
