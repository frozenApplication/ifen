package com.example.demo.modules.controller;

import com.example.demo.modules.data.JsonResult;
import com.example.demo.modules.params.Talent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class InterController {

    @Autowired
    HttpServletRequest httpServletRequest;

    @GetMapping("api/talent")
    public JsonResult pushPalent() {
//        Enumeration<String> parameterNames = httpServletRequest.getParameterNames();
//        Iterator<String> stringIterator = parameterNames.asIterator();
//        while (stringIterator.hasNext()){
//            System.out.println(stringIterator.next());
//        }

        return new JsonResult(200, new Talent(), "hello");
    }
}
