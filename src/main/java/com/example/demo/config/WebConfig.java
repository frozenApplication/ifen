package com.example.demo.config;

import com.example.demo.framework.interceptor.LoginRequiredInterceptor;
import com.example.demo.framework.resolver.CurrUserResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

/**
 *  WebMvcConfigurationSupport类仅可继承一个，多余的无法进行自动转载
 */
@Configuration
public class WebConfig extends WebMvcConfigurationSupport {

    @Autowired
    CurrUserResolver currUserResolver;
    @Override
    protected void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(currUserResolver);
        super.addArgumentResolvers(argumentResolvers);
    }

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        System.out.println("interceptors init.");
        registry.addInterceptor(new LoginRequiredInterceptor()).addPathPatterns("/api/v1/**")
                .excludePathPatterns("/api/v1/register","/api/v1/login");

        super.addInterceptors(registry);
    }
}
