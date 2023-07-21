package com.example.demo.aspect;

import com.example.demo.config.Auth.AuthService;
import lombok.AllArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class TokenAspect {

    private AuthService authService;

//    @Before("@annotation(TokenRequired)")
//    public void checkToken(){
//        ServletRequestAttributes requestAttributes =
//                (ServletRequestAttributes) RequestContextHolder
//                        .currentRequestAttributes();
//        requestAttributes.getRequest().getHeader("Authorization");
//    }
}
