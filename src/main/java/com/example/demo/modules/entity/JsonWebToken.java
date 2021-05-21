package com.example.demo.modules.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@TableName("json_web_token")
@Data
public class JsonWebToken {
    private Integer id;
    private String token;
}
