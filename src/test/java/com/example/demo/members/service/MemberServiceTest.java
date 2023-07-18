package com.example.demo.members.service;

import com.example.demo.config.domain.entity.MemberLogin;
import com.example.demo.config.repository.MemberLoginRepository;
import com.example.demo.config.service.MemberLoginService;
import com.example.demo.members.domain.entity.Member;
import com.example.demo.members.domain.request.LoginRequest;
import com.example.demo.members.domain.request.SignupRequest;
import com.example.demo.members.domain.response.LoginResponse;
import com.example.demo.members.repository.MemberRepository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
//import static org.assertj.core.api.Assertions.assertThatThrownBy;


@SpringBootTest
@Transactional
class MemberServiceTest {
    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Autowired
    MemberLoginService memberLoginService;

    @Autowired
    MemberLoginRepository memberLoginRepository;

    @BeforeEach
    void init(){
        String email = "1111";
        String password = "1234";
        LoginRequest loginRequest = new LoginRequest(email, password);
        Member name = new Member(null, email, password, "name", 10, null, null,null);
        memberRepository.save(name);

    }

    @AfterEach
    void clear(){
        memberRepository.deleteAll();
        memberLoginRepository.deleteAll();
    }

    @Test
    void insert() {
        //given
        String email = "ssss";
        String password = "1234";
        String name = "uuuu";
        Integer age = 10;
        SignupRequest signupRequest =
                new SignupRequest(email, password, name, age);
        // when
        memberService.insert(signupRequest);
        //then
        List<Member> all = memberRepository.findAll();

        assertThat(all).hasSize(1);
        assertThat(all.get(0).getEmail()).isEqualTo(email);
    }

    @Test
    void 기본로그인() {

        //given
        String email = "1111";
        String password = "1234";
        LoginRequest loginRequest = new LoginRequest(email, password);
        Member name = new Member(null, email, password, "name", 10, null, null,null);
        memberRepository.save(name);
        //when
        LoginResponse loginResponse = memberService.login(loginRequest);
        //then
        assertThat(loginResponse.age()).isEqualTo(10);
        assertThat(loginResponse.name()).isEqualTo("name");
        assertThat(loginResponse.id()).isNotNull();
    }
    @Test

    void 기본로그인_멤버로그인_인서트_체크() {

        //given
        String email = "1111";
        String password = "1234";
        LoginRequest loginRequest = new LoginRequest(email, password);
        Member name = new Member(null, email, password, "name", 10, null, null,null);
        memberRepository.save(name);
        //when
        LoginResponse loginResponse = memberService.login(loginRequest);
        //then
        assertThat(loginResponse.age()).isEqualTo(10);
        assertThat(loginResponse.name()).isEqualTo("name");
        assertThat(loginResponse.id()).isNotNull();

        List<MemberLogin> all = memberLoginRepository.findAll();
        assertThat(all).hasSize(1);
        assertThat(all.get(0).getMember()).isEqualTo(name);
        assertThat(all.get(0).getCreateAt()).isBefore(LocalDateTime.now());
        assertThat(all.get(0).getEndAt()).isAfter(LocalDateTime.now());
    }
    @Test
    void 로그인시_없는_유저(){
        //given
        String email = "1111";
        String password = "1234";
        LoginRequest loginRequest = new LoginRequest(email, password);
        Member name = new Member(null, email +"333", password, "name", 10, null, null,null);
        memberRepository.save(name);
        //when
        RuntimeException runtimeException =  // 어떤 에러인지도 잡아줄 수 있다.
                Assertions.assertThrows(
                        RuntimeException.class,
                        () -> memberService.login(loginRequest));


        //assertThatThrownBy(()->memberService.login(loginRequest))
               // .hasMessage("없는 유저");

        //then
        assertThat(runtimeException).hasMessage("없는 유저");
    }
}