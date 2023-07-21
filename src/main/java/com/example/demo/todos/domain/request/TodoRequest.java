package com.example.demo.todos.domain.request;

import com.example.demo.members.domain.entity.Member;
import com.example.demo.todos.domain.entity.Todo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @AllArgsConstructor
@NoArgsConstructor
public class TodoRequest {
    private String title;
    private String content;
    private boolean isDone;
    public Todo toEntity(Member member){

//        new Todo(null, title, content, false, 0,member);
        return Todo.builder()
                .content(content)
                .title(title)
                .member(member)
                .likeCount(0)
                .build();
    }
}
