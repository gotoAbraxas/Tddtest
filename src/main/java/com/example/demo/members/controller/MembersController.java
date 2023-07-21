package com.example.demo.members.controller;

import com.example.demo.aspect.TokenRequired;
import com.example.demo.members.domain.request.LoginRequest;
import com.example.demo.members.domain.request.SignupRequest;
import com.example.demo.members.domain.response.LoginResponse;
import com.example.demo.members.domain.response.MemberResponse;
import com.example.demo.members.service.MemberService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jdk.swing.interop.SwingInterOpUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class MembersController {

    private final MemberService memberService;
    @PostMapping("/login")
    @ApiResponses({
            @ApiResponse(description = "성공",responseCode = "200")
            ,@ApiResponse(description = "실패",responseCode = "400")})
    @Operation(description = "로그인",summary = "login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest){
        return memberService.login(loginRequest);
    }

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public void signup(@RequestBody SignupRequest signupRequest){
        memberService.insert(signupRequest);
    }

    @GetMapping
    @TokenRequired
    public Page<MemberResponse> getAll(
            @RequestParam(required = false,defaultValue = "0",name = "page")
                       Integer page,
            @RequestParam(required = false,defaultValue = "3",name = "size")
                        Integer size
                        ){
        return memberService.findAll(PageRequest.of(page,size));
    }

    @GetMapping("test")
    @TokenRequired
    public Map<String,Object> test(@RequestHeader("Authorization")String token
                                   ){
        System.out.println("fffffffffff");
        return memberService.getTokenToData(token.replace("Bearer ",""));
    }
}
