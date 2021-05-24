package com.example.demo.modules.params;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class UserRegisterStructure {
    @Pattern(regexp = "^1[0-9]{10}$",message = "请输入正确的手机号")
    @NotEmpty(message = "mobile may not be empty")
    private String mobile;
    @NotEmpty(message = "password may not be empty")
    private String password;
}
