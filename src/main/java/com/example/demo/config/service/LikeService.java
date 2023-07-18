package com.example.demo.config.service;

import com.example.demo.config.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;

    public void like(Long tId,Long uId){

        likeRepository.like(tId,uId);


    }


}
