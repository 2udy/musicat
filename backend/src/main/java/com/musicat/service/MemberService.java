package com.musicat.service;


import com.musicat.data.dto.KakaoUserInfoDto;
import com.musicat.data.entity.Member;
import com.musicat.data.repository.MemberRepository;
import com.musicat.util.MemberBuilderUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/*

회원과 관련된 비즈니스 로직을 수행하는 클래스

 */

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {


    private final MemberRepository memberRepository;
    private final MemberBuilderUtil memberBuilderUtil;


    /*
    카카오에서 받은 유저 정보를 토대로 로그인 수행

    DB 정보와 비교해
    있는 경우) 그대로 로그인 진행
    없는 경우) DB에 저장한 후 로그인 진행
     */
    public void kakaoLogin(KakaoUserInfoDto kakaoUserInfoDto) {

        // 카카오에서 가져온 유저정보 중 ID를 통해 검색
        Optional<Member> findMember = memberRepository.findByMemberId(kakaoUserInfoDto.getId());

        // ID를 통해 검색했을 때 정보가 없으면 회원가입을 진행
        if (!findMember.isPresent()) {
            System.out.println("아이디 없음 회원 가입 진행");
            Member member = memberBuilderUtil.buildMemberEntity(kakaoUserInfoDto);
            memberRepository.save(member);
        }

        // 로그인을 성공하면 token을 제공
        System.out.println("로그인 성공");




    }



}
