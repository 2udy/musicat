package com.musicat.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.musicat.data.dto.KakaoTokenDto;
import com.musicat.data.dto.KakaoUserInfoDto;
import com.musicat.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {

    private String REST_API_KEY = "dd7128c40ac72350cbbeb8f001764e04";
    private String REDIRECT_URI = "http://localhost:9999/api/member/kakao/login";
    private String CLIENT_SECRET = "MLsO13rsfVkiOiLl6aZJAuf19ofNcBrC";

    private final MemberService memberService;


    /*
    카카오 로그인시 redirect uri를 통해 인가코드를 받는 코드
    https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=dd7128c40ac72350cbbeb8f001764e04&redirect_uri=http://localhost:9999/api/member/kakao/login
     */
    @GetMapping("/kakao/login")
    public ResponseEntity<?> kakaoLogin(@RequestParam("code") String code) throws JsonProcessingException {

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


        // access token으로 사용자 정보 받아오기
        restTemplate = new RestTemplate();
        headers = new HttpHeaders();
        headers.setBearerAuth(kakaoTokenDto.getAccessToken());

        HttpEntity<Object> requestUserInfo = new HttpEntity<>(headers);
        url = "https://kapi.kakao.com/v2/user/me";



        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, requestUserInfo, Map.class);


        // 응답 데이터를 KakaoUserInfo 클래스와 매핑하여 객체 만들기
        Map<String, Object> responseBody = response.getBody();

        Long id = ((Number) responseBody.get("id")).longValue();
        Map<String, Object> properties = (Map<String, Object>) responseBody.get("properties");
        String nickname = properties.get("nickname").toString();
        String profileImage = properties.get("profile_image").toString();
        String thumbnailImage = properties.get("thumbnail_image").toString();

        Map<String, Object> kakaoAccount = (Map<String, Object>) responseBody.get("kakao_account");
        String email = kakaoAccount.get("email").toString();

        // KakaoUserInfoDto 객체에 변수들 저장
        KakaoUserInfoDto kakaoUserInfoDto = KakaoUserInfoDto.builder()
                .id(id)
                .nickname(nickname)
                .profileImage(profileImage)
                .thumbnailImage(thumbnailImage)
                .email(email)
                .build();

        // 카카오에서 받은 정보를 토대로 로그인 수행
        memberService.kakaoLogin(kakaoUserInfoDto);


        // 응답 처리
        return new ResponseEntity<>(kakaoTokenDto, HttpStatus.OK);


    }


}
