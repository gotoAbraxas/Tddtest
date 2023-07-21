package com.example.demo.config.repository;

import com.example.demo.config.domain.entity.MemberLogin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberLoginRepository
        extends JpaRepository<MemberLogin, Long> {

    @Query("select m from MemberLogin m where m.member.id = :memberId ORDER BY m.id DESC LIMIT 1")
    Optional<MemberLogin> findByMemberId(@Param("memberId")Long memberId);


//    Optional<MemberLogin> findFirstByMemberIdAndEndAtAfterOrderByEndAtDesc();
}
