package com.example.demo.todos.domain.request;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateRequest {

    private String title;
    private String content;
    private boolean isDone;
}
