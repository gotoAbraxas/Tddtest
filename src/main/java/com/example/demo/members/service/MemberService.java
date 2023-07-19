package com.example.demo.members.service;

import com.example.demo.config.domain.entity.MemberLogin;
import com.example.demo.config.exception.DuplicateEmailException;
import com.example.demo.config.exception.LoginFailExceltion;
import com.example.demo.config.service.MemberLoginService;
import com.example.demo.members.domain.entity.Member;
import com.example.demo.members.domain.request.LoginRequest;
import com.example.demo.members.domain.request.SignupRequest;
import com.example.demo.members.domain.response.LoginResponse;
import com.example.demo.members.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final MemberLoginService memberLoginService;

    public void insert(SignupRequest request){

        Optional<Member> byEmail = memberRepository.findByEmail(request.email());

        if(byEmail.isPresent()) throw new DuplicateEmailException("있는 이메일");

       memberRepository.save(request.toEntity());
    }

    public LoginResponse login(LoginRequest request){
        Optional<Member> byEmailAndPassword =
                memberRepository
                        .findByEmailAndPassword(request.email(), request.password());
        Member member = byEmailAndPassword
                .orElseThrow(() -> new LoginFailExceltion("없는 유저"));
        memberLoginService.insert(member);
        return new LoginResponse(member.getId(), member.getName(), member.getAge());
    }
}
