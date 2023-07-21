package com.example.demo.config.repository;

import com.example.demo.members.domain.entity.Member;
import com.example.demo.todos.domain.entity.Likes;

import com.example.demo.todos.domain.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LikeRepository extends JpaRepository<Likes, Long> {

//    @Modifying(clearAutomatically = true)
//    @Query("INSERT INTO Likes(Member, Todo) " +
//            "VALUES(:member,:todo)")
//    void like(@Param("member") Member member, @Param("todo") Todo todo);
}
