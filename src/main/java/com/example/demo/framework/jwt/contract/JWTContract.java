package com.example.demo.framework.jwt.contract;

import org.springframework.stereotype.Component;

import java.util.Map;

public interface JWTContract {
    String encode(Map<String, Object> message);    //编码

    Map<String, ?> decode(String jwt);      //解码返回
}
