package com.example.demo.modules.data;

import jdk.jfr.Name;
import lombok.Data;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.type.Alias;
import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;


@Data
public class JsonResult {
    private String code;
    @Name("data")
    private Object Content;
    private String message;
}
