package com.example.demo.todos.service;

import com.example.demo.todos.domain.entity.Likes;
import com.example.demo.config.domain.entity.MemberLogin;
import com.example.demo.config.repository.MemberLoginRepository;
import com.example.demo.config.service.MemberLoginService;
import com.example.demo.members.domain.entity.Member;
import com.example.demo.members.domain.request.LoginRequest;
import com.example.demo.members.repository.MemberRepository;
import com.example.demo.todos.domain.entity.Todo;
import com.example.demo.todos.domain.request.TodoRequest;
import com.example.demo.todos.domain.request.UpdateRequest;
import com.example.demo.todos.repository.TodoRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@SpringBootTest
@Transactional
class TodoServiceTest {
    @Autowired
    TodoRepository todoRepository;
    @Autowired
    TodoService todoService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    MemberLoginRepository memberLoginRepository;
    @Autowired
    MemberLoginService memberLoginService;
    String email = "1111";
    String password = "1234";
    Member member;
    Todo todo;

    @BeforeEach
    void init(){
        LoginRequest loginRequest = new LoginRequest(email, password);
        Member name = new Member(null, email, password, "name", 10, null, null);
        this.member = memberRepository.save(name);
        MemberLogin entity = new MemberLogin(this.member, LocalDateTime.now());
        memberLoginRepository.save(entity);

        TodoRequest request = new TodoRequest("test", "test2",false);
        todo = request.toEntity(member);
        todoRepository.save(todo);
    }

    @AfterEach
    void clear(){
        todoRepository.deleteAll();
        memberRepository.deleteAll();

    }

    @Test
    void 아이디찾기(){
        //given
        Long id = todo.getId();
        //when
        Todo byId = todoService.findById(id);
        //then
        Assertions.assertThat(byId.getContent()).isEqualTo(todo.getContent());

    }
    @Test
    void insert() {

    }

    @Test
    void 글삽입() {
        //given
        String content = "test22";
        String title = "test33";
        boolean isDone = true;
        UpdateRequest updateRequest = new UpdateRequest(title, content, isDone);

        //when
        todoService.update(todo.getId(),updateRequest);


        //then
        Assertions.assertThat(todo.getContent()).isEqualTo(content);
        Assertions.assertThat(todo.getTitle()).isEqualTo(title);
        Assertions.assertThat(todo.isDone()).isEqualTo(isDone);


    }



    @Test
    void 글삭제() {
        //given
        Long id = todo.getId();
        //when
        todoService.delete(id);
        //then
        Todo todo1 = todoRepository.findById(id).orElse(null);
        Assertions.assertThat(todo1).isNull();

    }

    @Test
    void 좋아요누르기(){
        //given


        //when
        Long id = todo.getId();
        todoService.like(id,member.getId());

        //then
//        Likes likes = todoService.findAll().get(0);
//        Assertions.assertThat(likes.getMember().getId()).isEqualTo(member.getId());
//        Assertions.assertThat(likes.getTodo().getId()).isEqualTo(id);
    }


}