package com.example.demo.config;


import com.example.demo.framework.interceptor.LoginRequiredInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class InterceptorConfig extends WebMvcConfigurationSupport {
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginRequiredInterceptor()).addPathPatterns("/api/v1/**")
                .excludePathPatterns("/api/v1/register","/api/v1/login");

        super.addInterceptors(registry);
    }
}
