package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "对不起有问题.")
public class UserException extends RuntimeException {
    public UserException(String msg) {
        super(msg);
    }
}
