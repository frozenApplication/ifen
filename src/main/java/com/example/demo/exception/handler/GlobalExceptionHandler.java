package com.example.demo.exception.handler;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.example.demo.exception.UserException;
import com.example.demo.modules.data.JsonResult;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 异常处理：
 * 验证不通过抛出的
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理参数验证不通过的
     *
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public JsonResult ParameterExceptionHandler(BindException e) {
        Map<String, Object> map = new HashMap<>();
        String err = "has not error msg";
        // 获取异常信息
        BindingResult exceptions = e.getBindingResult();
//         判断异常中是否有错误信息，如果存在就使用异常中的消息，否则使用默认消息
        if (exceptions.hasErrors()) {
            List<ObjectError> errors = exceptions.getAllErrors();
            if (!errors.isEmpty()) {
//                 这里列出了全部错误参数，按正常逻辑，只需要第一条错误即可
                FieldError fieldError = (FieldError) errors.get(0);
                err = fieldError.getDefaultMessage();
            }
        }
        return new JsonResult(1, null, err);
    }

    /**
     * 缺少请求头参数
     *
     * @param e
     * @return
     */
    @ExceptionHandler(MissingRequestHeaderException.class)
    public JsonResult MissingRequestHeaderExceptionHandler(MissingRequestHeaderException e) {
        return new JsonResult(1, null, e.getMessage());
    }

    /**
     * 缺少请求体的url参数
     *
     * @param e
     * @return
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public JsonResult MissingServletRequestParameterExceptionHandler(MissingServletRequestParameterException e) {
        return new JsonResult(1, null, "missing params");
    }

    @ExceptionHandler(DuplicateKeyException.class)//数据库处理时遇到重复键
    public JsonResult DataBaseMessageDuplicateExceptionHandler(DuplicateKeyException e) {

        return new JsonResult(1, null, "the mobile has been registered.");
    }

    @ExceptionHandler(UserException.class)
    public JsonResult JsonResultUserExceptionHandler(UserException e) {
        return new JsonResult(1, null, e.getMessage());
    }

    @ExceptionHandler(SignatureVerificationException.class)
    public JsonResult signatureExceptionHandler(SignatureVerificationException e) {
        return new JsonResult(1, null, e.getMessage());
    }

    @ExceptionHandler(JWTDecodeException.class)
    public JsonResult JwtDecodeExceptionHandler(JWTDecodeException e) {
        return new JsonResult(1, null, e.getMessage());
    }

}
