package com.example.demo.framework.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Iterator;

@Component
public class SimpleFilter implements Filter {
    @Override
    public void destroy() {
    }

    @Override

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterchain)
            throws IOException, ServletException {
        Iterator<String> stringIterator = request.getParameterNames().asIterator();
        String s;
        while (stringIterator.hasNext()) {
            s = stringIterator.next();
            System.out.println("params:"+s);
        }
        System.out.println("Remote Port:" + request.getRemotePort());
        System.out.println("Remote Host:" + request.getRemoteHost());
        System.out.println("Remote Address:" + request.getRemoteAddr());
        HttpServletRequest httpServletRequest = (HttpServletRequest)request;
        System.out.println("Remote URL:" + httpServletRequest.getRequestURI());

        filterchain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterconfig) throws ServletException {
        System.out.println("simple filter init.");
    }
}

