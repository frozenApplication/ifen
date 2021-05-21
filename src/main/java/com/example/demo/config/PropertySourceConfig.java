package com.example.demo.config;

import com.example.demo.config.parameters.random.diy.RandomValuePropertySource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;

import javax.annotation.PostConstruct;

@Configuration
public class PropertySourceConfig {
    @Autowired
    private ConfigurableEnvironment env;

    @PostConstruct
    public void init() throws Exception {
        env.getPropertySources().addFirst(new RandomValuePropertySource());
    }
}
