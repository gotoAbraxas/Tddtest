package com.example.demo.todos.domain.entity;


import com.example.demo.members.domain.entity.Member;
import com.example.demo.todos.domain.request.UpdateRequest;
import jakarta.persistence.*;
import lombok.*;

@Entity @Table(name = "todos")
@AllArgsConstructor @NoArgsConstructor
@Getter @Builder
@Setter
public class Todo {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    private boolean isDone;
    private Integer likeCount;
    @ManyToOne
    private Member member;

    public void toUpdate(String title,String content,boolean isDone){
        this.title = title;
        this.content = content;
        this.isDone = isDone;
    }
}
