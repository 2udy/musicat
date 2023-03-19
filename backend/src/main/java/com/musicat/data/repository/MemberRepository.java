package com.musicat.data.repository;

import com.musicat.data.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    // 카카오에서 제공받은 id를 통해 member 정보를 가져옴
    Optional<Member> findByMemberId(long memberId);
    


}
