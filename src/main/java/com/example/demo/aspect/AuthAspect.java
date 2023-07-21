package com.example.demo.aspect;

import com.example.demo.config.Auth.AuthService;
import com.example.demo.config.exception.EmptyTokenException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class AuthAspect {

    @Autowired
    private AuthService authService;

    @Around("@annotation(TokenRequired)")
    public Object checkToken(ProceedingJoinPoint point) throws Throwable {

        ServletRequestAttributes request =
                (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        String token = request.getRequest().getHeader("Authorization");
        System.out.println(token);
        // 파라미터는 uri 가 너무 더러워지니깐, header 에 담아서 보낼 수 있다.
        // 그때는 Authorization 라는 이름으로 보내야한다.
        if(token == null) throw  new EmptyTokenException("토큰 내놔");
        String replace = token.replace("Bearer ", "");
        authService.getClaims(replace);

        Object proceed = point.proceed();

        return proceed;
    }

}
