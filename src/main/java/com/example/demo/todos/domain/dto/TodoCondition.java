package com.example.demo.todos.domain.dto;

import lombok.*;
import org.springframework.data.domain.PageRequest;
@AllArgsConstructor
@Getter @Setter
@NoArgsConstructor
@Builder
public class TodoCondition {


    private  String title;
    private String content;
    private Boolean isDone;
    private Integer likeGoe;
    private Integer likeLoe;
}
