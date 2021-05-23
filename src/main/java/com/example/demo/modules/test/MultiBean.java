package com.example.demo.modules.test;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;




@Component
public class MultiBean {
    @Bean
    public MyBean bb1() {
        return new MyBean("bb1");
    }

    @Bean
    public MyBean bb2() {
        return new MyBean("bb2");
    }
}
