package com.example.demo.todos.service;

import com.example.demo.config.repository.LikeRepository;
import com.example.demo.config.service.MemberLoginService;
import com.example.demo.members.domain.entity.Member;
import com.example.demo.todos.domain.entity.Likes;
import com.example.demo.todos.domain.entity.Todo;
import com.example.demo.todos.domain.request.TodoRequest;
import com.example.demo.todos.domain.request.UpdateRequest;
import com.example.demo.todos.domain.response.TodoResponse;
import com.example.demo.todos.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;
    private final MemberLoginService memberLoginService;
    private final LikeRepository likeRepository;

    public void insert(Long id,TodoRequest request){

        todoRepository.save(request.toEntity(new Member(id,null,null,null,null,null,null)));

    }

    public void update(Long id, UpdateRequest updateRequest){

        Todo byId = findById(id);

        byId.toUpdate(updateRequest.getTitle(), updateRequest.getContent(), updateRequest.isDone());

    }

    public void delete(Long id){

        Todo byId1 = findById(id);
        todoRepository.delete(byId1);
    }

    public Todo findById(Long id){

        return todoRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    public void like(Long tId, Long mId){
        Optional<Todo> byId = todoRepository.findById(tId);

        Todo todo = byId.orElseThrow(() -> new RuntimeException("해당 게시글이 없습니다."));
        Likes likes = new Likes(null, mId, todo);

        likeRepository.save(likes);

    }

    public void check(Long todoId,Long memberId){
        Optional<Todo> byId = todoRepository.findById(todoId);
    }

    public Page<TodoResponse> findAllBy(String title, String Content, PageRequest pageRequest){


        Page<Todo> allByTitleAndContent = todoRepository.findAllByTitleAndContent("%"+ title+"%", "%"+Content+"%", pageRequest);

        return  allByTitleAndContent.map(TodoResponse::new);
    }









}
