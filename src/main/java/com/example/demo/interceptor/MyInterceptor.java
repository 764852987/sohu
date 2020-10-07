package com.example.demo.interceptor;

import com.example.demo.annotation.Authority;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.aspectj.lang.annotation.Aspect;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

@Slf4j
@Aspect
@Component
public class MyInterceptor {
    @Pointcut("@annotation(com.example.demo.annotation.Authority) && execution(* com.example.demo.controller..*(..))")
    public void pointcut() {
    }

    @Before("pointcut()")
    public void doBefore(JoinPoint joinPoint){
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        // 如果方法的Admin注解不存在，直接执行下一步
        if (method == null || !method.isAnnotationPresent(Authority.class)) {
           // throw new CustomException(ErrorCodeEnum.FORBIDDEN);
        }
        Authority authority = method.getAnnotation(Authority.class);
        String a = authority.value();
        log.info("请求权限: "+a);
    }
}
