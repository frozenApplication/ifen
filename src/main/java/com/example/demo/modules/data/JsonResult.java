package com.example.demo.modules.data;


public class JsonResult {
    private Integer code;   // 0:成功 1:失败
    private Object data;//数据载体
    private String message;//成功发送 success; 失败发送错误原因

    public JsonResult(Integer code, Object data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
