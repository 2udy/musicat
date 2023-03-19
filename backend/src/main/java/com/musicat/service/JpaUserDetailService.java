package com.musicat.service;


import com.musicat.data.entity.Member;
import com.musicat.data.repository.MemberRepository;
import com.musicat.util.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/*

Spring Security의 UserDetailService는 UserDetails 정보를 토대로 유저 정보를 불러올 때 사용된다.
Jpa를 이용해 DB에서 유저 정보를 조회할 것이므로 이에 맞춰 구현해준다.

카카오에서 제공한 id를 memberId로 받고 이를 토대로 검색함

 */


@Service
@RequiredArgsConstructor
public class JpaUserDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String memberId) throws UsernameNotFoundException {
        Member member = memberRepository.findByMemberId(memberId).orElseThrow(
                () -> new UsernameNotFoundException("Invalid authentication!")
        );

        return new CustomUserDetails(member);
    }
}
