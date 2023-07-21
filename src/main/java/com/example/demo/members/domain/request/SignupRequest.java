package com.example.demo.members.domain.request;


import com.example.demo.members.domain.entity.Member;

public record SignupRequest(String email
        , String password
        , String name
        , Integer age) {
    public Member toEntity(){

        return new Member(null,email,password,name,age,null,null);

    }
}
