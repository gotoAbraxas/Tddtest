package com.example.demo.aspect;

import lombok.AllArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;

@Aspect
@Component
public class LoginAspect {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Before("execution(* com.example.demo.members.controller.MembersController.*(..))")
    public void loggingBefore(){
      log.info(this.getClass().getName() + " " + LocalDateTime.now());
    }

    @After("execution(* com.example.demo.members.controller.MembersController.*(..))")
    public void loggingAfter(){
        log.info(this.getClass().getName() + " " + LocalDateTime.now());
    }

    @Around("execution(* com.example.demo.members.controller.MembersController.*(..))")
    public Object loggingAround(ProceedingJoinPoint point) throws Throwable {
        long startTime = new Date().getTime();
        log.info("start : " + point.getSignature().getName() + ", " + point.getSignature().getDeclaringTypeName());
        Object proceed = point.proceed(); // 비포와 에프터를 나누는 기준
        log.info("after :" + point.getSignature().getName() + ", " + point.getSignature().getDeclaringTypeName());
        log.info("ms :" + (new Date().getTime() - startTime));
        return proceed;
    }

    @Around("execution(* com.example.demo.members.*.*.*(..))")
    public Object loggingAround1(ProceedingJoinPoint point) throws Throwable {
        long startTime = new Date().getTime();
        log.info("start : " + point.getSignature().getName() + ", " + point.getSignature().getDeclaringTypeName());
        Object proceed = point.proceed(); // 비포와 에프터를 나누는 기준
        log.info("after :" + point.getSignature().getName() + ", " + point.getSignature().getDeclaringTypeName());
        log.info("ms :" + (new Date().getTime() - startTime));
        return proceed;
    }

    @Around("@annotation(org.springframework.stereotype.Service)")
    public Object loggingAround2(ProceedingJoinPoint point) throws Throwable {
        long startTime = new Date().getTime();
        log.info("start : " + point.getSignature().getName() + ", " + point.getSignature().getDeclaringTypeName());
        Object proceed = point.proceed(); // 비포와 에프터를 나누는 기준
        log.info("after :" + point.getSignature().getName() + ", " + point.getSignature().getDeclaringTypeName());
        log.info("ms :" + (new Date().getTime() - startTime));
        return proceed;
    }

}
