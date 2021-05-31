package com.example.demo.framework.resolver;

import com.example.demo.framework.annotation.Operator;
import com.example.demo.framework.jwt.contract.JWTContract;
import com.example.demo.modules.controller.UserController;
import com.example.demo.modules.entity.User;
import com.example.demo.modules.processors.UserOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Slf4j
@Component
public class CurrUserResolver implements HandlerMethodArgumentResolver {
    @Autowired
    JWTContract jwtContract;
    @Autowired
    UserOperation userOperation;

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        Operator parameterAnnotation = methodParameter.getParameterAnnotation(Operator.class);
        return methodParameter.hasParameterAnnotation(Operator.class);

    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);

        String authorization = request.getHeader("Authorization");
        if(authorization == null) return null;


//        methodParameter.getParameterAnnotation();
        log.debug("验证operator");
        return userOperation.getUserByJwt(authorization);
    }
}
