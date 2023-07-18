package com.example.demo.todos.service;

import com.example.demo.todos.domain.entity.Todo;
import com.example.demo.todos.domain.request.TodoRequest;
import com.example.demo.todos.domain.request.UpdateRequest;
import com.example.demo.todos.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;
    public void insert(TodoRequest request){
        todoRepository.save(request.toEntity());
    }

    public void update(Long id, UpdateRequest updateRequest){

        Optional<Todo> byId = todoRepository.findById(id);
        Todo todo = byId.orElseThrow(()->new RuntimeException("글이 없습니다."));
        todo.toUpdate(updateRequest.getTitle(), updateRequest.getContent(), updateRequest.isDone());

    }

    public void delete(Long id){
        Optional<Todo> byId = todoRepository.findById(id);


        todoRepository.delete();
    }


}
