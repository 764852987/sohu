package com.example.demo.exception;


import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    @ExceptionHandler(value = ParameterException.class)//拦截所有异常
    public String exceptionHandler(HttpServletRequest request, ParameterException e){
        e.printStackTrace();
        return "请求失败，请检查参数。";
    }
}
