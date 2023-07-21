package com.example.demo.config.domain.dto;

import com.example.demo.todos.domain.entity.Todo;
import lombok.Getter;

@Getter
public class TodoDto {
    private Long id;
    private String title;
    private String content;
    private boolean isDone;
    private Integer likeCount;

    public TodoDto(Todo todo){
        this.id = todo.getId();
        this.title = todo.getTitle();
        this.content = todo.getContent();
        this.isDone = todo.isDone();
        this.likeCount = todo.getLikeCount();
    }
}
