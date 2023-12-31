package com.example.demo.config.service;

import com.example.demo.config.domain.entity.MemberLogin;
import com.example.demo.config.repository.MemberLoginRepository;
import com.example.demo.members.domain.entity.Member;
import com.example.demo.members.domain.request.LoginRequest;
import com.example.demo.members.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberLoginServiceTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    MemberLoginRepository   memberLoginRepository;
    @Autowired
    MemberLoginService memberLoginService;
    String email = "1111";
    String password = "1234";
    Member member;

    @BeforeEach
    void init(){
        LoginRequest loginRequest = new LoginRequest(email, password);
        Member name = new Member(null, email, password, "name", 10, null, null);
        this.member = memberRepository.save(name);
        MemberLogin entity = new MemberLogin(this.member, LocalDateTime.now());
        memberLoginRepository.save(entity);
    }

    @AfterEach
    void clear(){
        memberLoginRepository.deleteAll();
        memberRepository.deleteAll();
    }

    @Test
    void insert() {
    }

    @Test
    void findByMember(){
        //given
        Long memberId = member.getId();

        //when
        Member byMember = memberLoginService.findByMember(memberId);

        //then
        Assertions.assertThat(byMember.getEmail()).isEqualTo(email);
        Assertions.assertThat(byMember.getPassword()).isEqualTo(password);
    }

    @Test
    void 가장최근것찾기() {
        //given


        Member name = memberRepository.findAll().get(0);
        Long memberId = name.getId();

        MemberLogin entity = new MemberLogin(name, LocalDateTime.now());
        MemberLogin save = memberLoginRepository.save(entity);

        //when
        Member byMember = memberLoginService.findByMember(memberId);

        //then
        Assertions.assertThat(save.getMember()).isEqualTo(byMember);
    }

    @Test
    void 로그인상태가아닌경우(){
        RuntimeException runtimeException = assertThrows(
                RuntimeException.class,()->memberLoginService.findByMember(100l));
        Assertions.assertThat(runtimeException).hasMessage("로그인 상태가 아닙니다.");
    }
}