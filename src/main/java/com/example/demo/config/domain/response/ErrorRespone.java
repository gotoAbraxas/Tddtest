package com.example.demo.config.domain.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@AllArgsConstructor
public class ErrorRespone {
    private  String message;
    private Throwable cause;
}
