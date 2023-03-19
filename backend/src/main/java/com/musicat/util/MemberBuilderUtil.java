package com.musicat.util;


import com.musicat.data.dto.KakaoUserInfoDto;
import com.musicat.data.entity.Member;
import org.springframework.stereotype.Component;

@Component
public class MemberBuilderUtil {

    /*
    카카오에서 받은 정보로 회원가입을 진행하기 위한 엔티티 객체를 생성하는 메소드
     */
    public Member buildMemberEntity(KakaoUserInfoDto kakaoUserInfoDto) {
        return Member.builder()
                .memberId(kakaoUserInfoDto.getId())
                .memberNickname(kakaoUserInfoDto.getNickname())
                .memberProfileImage(kakaoUserInfoDto.getProfileImage())
                .memberThumbnailImage(kakaoUserInfoDto.getThumbnailImage())
                .memberEmail(kakaoUserInfoDto.getEmail())
                .build();
    }

}
