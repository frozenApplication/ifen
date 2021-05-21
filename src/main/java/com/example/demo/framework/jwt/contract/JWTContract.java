package com.example.demo.framework.jwt.contract;

public interface JWTContract {
    String encode(String issue);    //编码

    String decode(String jwt);      //解码
}
