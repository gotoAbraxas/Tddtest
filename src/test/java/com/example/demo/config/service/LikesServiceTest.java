package com.example.demo.config.service;

import com.example.demo.config.domain.entity.Likes;
import com.example.demo.config.domain.entity.MemberLogin;
import com.example.demo.config.repository.LikeRepository;
import com.example.demo.config.repository.MemberLoginRepository;
import com.example.demo.members.domain.entity.Member;
import com.example.demo.members.domain.request.LoginRequest;
import com.example.demo.members.repository.MemberRepository;
import com.example.demo.members.service.MemberService;
import com.example.demo.todos.domain.entity.Todo;
import com.example.demo.todos.domain.request.TodoRequest;
import com.example.demo.todos.repository.TodoRepository;
import com.example.demo.todos.service.TodoService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
class LikesServiceTest {

    @Autowired
    TodoRepository todoRepository;
    @Autowired
    TodoService todoService;
    @Autowired
    MemberRepository memberRepository;

    @Autowired
    MemberService memberService;

    @Autowired
    LikeRepository likeRepository;
    @Autowired
    LikeService likeService;
    @Autowired
    MemberLoginRepository memberLoginRepository;

    String email = "1111";
    String password = "1234";
    Member member;
    Todo todo;

    @BeforeEach
    void init(){
        LoginRequest loginRequest = new LoginRequest(email, password);
        Member name = new Member(null, email, password, "name", 10, null, null,null);

        this.member = memberRepository.save(name);

        MemberLogin entity = new MemberLogin(this.member, LocalDateTime.now());
        memberLoginRepository.save(entity);

        TodoRequest request = new TodoRequest("test", "test2", 1l);
        todo = request.toEntity();
        todoRepository.save(todo);
    }

    @AfterEach
    void clear(){
        likeRepository.deleteAll();
        todoRepository.deleteAll();
        memberRepository.deleteAll();

    }

    @Test
    void like() {
    }

    @Test
    void 좋아요누르기(){
        //given
        Long id = todo.getId();
        Long uid = member.getId();

        //when
        likeService.like(id,uid);

        //then
        Likes likes = likeRepository.findAll().get(0);
        Assertions.assertThat(likes.getMember().getId()).isEqualTo(uid);
        Assertions.assertThat(likes.getTodo().getId()).isEqualTo(id);

    }
}