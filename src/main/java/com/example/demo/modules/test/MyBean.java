package com.example.demo.modules.test;

import lombok.Data;

@Data
public class MyBean {
    String s;

    public MyBean(String key) {
        this.s = key;
    }
}