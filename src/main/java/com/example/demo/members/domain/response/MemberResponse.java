package com.example.demo.members.domain.response;

import com.example.demo.config.domain.dto.MemberDto;
import com.example.demo.config.domain.dto.TodoDto;
import com.example.demo.members.domain.entity.Member;
import com.example.demo.todos.domain.entity.Todo;
import jakarta.persistence.Column;
import lombok.Getter;

import java.util.List;

@Getter
public class MemberResponse extends MemberDto {
    private List<TodoDto> todos;


    public MemberResponse(Member member) {
        super(member);
        todos = member.getTodos()
                .stream().map(TodoDto::new).toList();
    }
}

