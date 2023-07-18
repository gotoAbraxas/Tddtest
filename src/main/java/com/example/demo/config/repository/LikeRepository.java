package com.example.demo.config.repository;

import com.example.demo.config.domain.entity.Likes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LikeRepository extends JpaRepository<Likes, Long> {

    @Modifying
    @Query("INSERT INTO Likes(member, todo) " +
            "SELECT m, t FROM Member m, Todo t " +
            "WHERE m.id = :uId AND t.id = :tId")
    void like(@Param("tId") Long tId, @Param("uId") Long uId);
}
