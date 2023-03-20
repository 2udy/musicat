package com.musicat.service;


import com.musicat.data.dto.KakaoLoginResponseDto;
import com.musicat.data.dto.KakaoTokenDto;
import com.musicat.data.dto.KakaoUserInfoDto;
import com.musicat.data.entity.Member;
import com.musicat.data.repository.MemberRepository;
import com.musicat.util.JwtUtil;
import com.musicat.util.MemberBuilderUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Optional;

/*

회원과 관련된 비즈니스 로직을 수행하는 클래스

 */

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private String REST_API_KEY = "dd7128c40ac72350cbbeb8f001764e04";
    private String REDIRECT_URI = "http://localhost:9999/api/member/kakao/login";
    private String CLIENT_SECRET = "MLsO13rsfVkiOiLl6aZJAuf19ofNcBrC";


    private final MemberRepository memberRepository;
    private final MemberBuilderUtil memberBuilderUtil;
    private final JwtUtil jwtUtil;




    /*
    인가코드를 파라미터 입력받아 access token 생성
    access token으로 kakao 사용자 정보 받음
    사용자 정보로 회원가입 진행
     */
    public KakaoLoginResponseDto login(String code) {

        // 인가코드로 액세스 토큰 받아옴
        KakaoTokenDto kakaoToken = getKakaoToken(code);

        // 액세스 토큰으로 카카오 유저 정보 받아옴
        KakaoUserInfoDto kakaoUserInfo = getKakaoUserInfo(kakaoToken);

        // 로그인 작업 처리 후 카카오 로그인 정보 반환
        return kakaoLogin(kakaoUserInfo);
    }


    /*
    인가코드로 access token 받아오기
     */
    public KakaoTokenDto getKakaoToken(String code) {

        // 인가코드를 사용해서 access token 획득하기
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // 요청 파라미터 설정
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", REST_API_KEY);
        params.add("redirect_uri", REDIRECT_URI);
        params.add("code", code);
        params.add("client_secret", CLIENT_SECRET);

        // HttpEntity 객체 생성
        HttpEntity<MultiValueMap<String, String>> requestToken = new HttpEntity<>(params, headers);

        // 요청 보내기
        String url = "https://kauth.kakao.com/oauth/token";

        // access token 획득
        KakaoTokenDto kakaoTokenDto = restTemplate.postForObject(url, requestToken, KakaoTokenDto.class);

        return kakaoTokenDto;
    }

    /*
    access token으로 사용자 정보 불러오기
     */
    public KakaoUserInfoDto getKakaoUserInfo(KakaoTokenDto kakaoTokenDto) {

        // access token으로 사용자 정보 받아오기
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(kakaoTokenDto.getAccessToken());

        HttpEntity<Object> requestUserInfo = new HttpEntity<>(headers);
        String url = "https://kapi.kakao.com/v2/user/me";

        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, requestUserInfo, Map.class);


        // 응답 데이터를 KakaoUserInfo 클래스와 매핑하여 객체 만들기
        Map<String, Object> responseBody = response.getBody();

        String id = responseBody.get("id").toString();
        Map<String, Object> properties = (Map<String, Object>) responseBody.get("properties");
        String nickname = properties.get("nickname").toString();
        String profileImage = properties.get("profile_image").toString();
        String thumbnailImage = properties.get("thumbnail_image").toString();

        Map<String, Object> kakaoAccount = (Map<String, Object>) responseBody.get("kakao_account");
        String email = kakaoAccount.get("email").toString();

        // KakaoUserInfoDto 객체에 변수들 저장한 후 return
        return KakaoUserInfoDto.builder()
                .id(id)
                .nickname(nickname)
                .profileImage(profileImage)
                .thumbnailImage(thumbnailImage)
                .email(email)
                .build();

    }


    /*
    카카오에서 받은 유저 정보를 토대로 로그인 수행

    DB 정보와 비교해
    있는 경우) 그대로 로그인 진행
    없는 경우) DB에 저장한 후 로그인 진행
     */
    public KakaoLoginResponseDto kakaoLogin(KakaoUserInfoDto kakaoUserInfoDto) {


        // 카카오에서 가져온 유저정보 중 ID를 통해 검색
        Optional<Member> findMember = memberRepository.findByMemberId(kakaoUserInfoDto.getId());
        Member member = null;
        // ID를 통해 검색했을 때 정보가 있으면 회원 정보 반환
        if (findMember.isPresent()) {
            System.out.println("MemberService - 아이디 있음 바로 회원 정보 반환 ");
            member = findMember.get();
        }

        // 회원 정보가 없다면 회원 가입 후 로그인 진행
        else if(!findMember.isPresent()) {
            System.out.println("MemberService - 아이디 없음 회원 가입 진행 후 회원 정보 반환 ");
            Member memberSave = memberBuilderUtil.buildMemberEntity(kakaoUserInfoDto);
            member = memberRepository.save(memberSave);
        }

        return KakaoLoginResponseDto.builder()
                .id(member.getMemberId())
                .accessToken(jwtUtil.createAccessToken(member))
                .refreshToken(jwtUtil.createRefreshToken(member))
                .build();
    }



}
