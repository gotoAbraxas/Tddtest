package com.example.demo.todos.controller;

import com.example.demo.aspect.TokenRequired;
import com.example.demo.config.Auth.AuthService;
import com.example.demo.todos.domain.entity.Todo;
import com.example.demo.todos.domain.request.TodoRequest;
import com.example.demo.todos.domain.request.UpdateRequest;
import com.example.demo.todos.domain.response.TodoResponse;
import com.example.demo.todos.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/todos")
public class TodoController {
    private final TodoService todoService;
    private final AuthService authService;



    @PostMapping
    @TokenRequired
    @ResponseStatus(HttpStatus.CREATED)
    public void insert(@RequestBody TodoRequest todoRequest,
                       @RequestHeader("Authorization")String token){

        if(token == null){
            System.out.println("------------------------------");
        }
        Map<String, Object> claims = authService.getClaims(token.replace("Bearer ",""));
        Long memberId = ((Integer) claims.get("memberId")).longValue();

        todoService.insert(memberId,todoRequest);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public void update(@PathVariable(name = "id") Long id, @RequestBody UpdateRequest updateRequest){
        todoService.update(id,updateRequest);
    }

    @GetMapping
    public Page<TodoResponse> findAllBy(
            @RequestParam(name = "title",required = false,defaultValue = "")String title,
            @RequestParam(name = "content",required = false,defaultValue = "")String content,
            @RequestParam(required = false,defaultValue = "0",name = "page")
            Integer page,
            @RequestParam(required = false,defaultValue = "20",name = "size")
            Integer size){

        PageRequest pageRequest = PageRequest.of(page, size);
        return todoService.findAllBy(title,content,pageRequest);

    }


    @PutMapping("{todoId}/check")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @TokenRequired
    public void todoCheck(@PathVariable(name = "todoId") Long todoId,
                          @RequestHeader("Authorization")String token){

        Map<String, Object> claims = authService.getClaims(token.replace("Bearer ",""));
        Long memberId = ((Integer) claims.get("memberId")).longValue();

        todoService.check(todoId,memberId);


    }
}
