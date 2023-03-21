package com.musicat.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.musicat.data.dto.KakaoLoginResponseDto;
import com.musicat.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class
MemberController {

    private final MemberService memberService;

    /*
    카카오 로그인시 redirect uri를 통해 인가코드를 받는 코드
    https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=dd7128c40ac72350cbbeb8f001764e04&redirect_uri=http://localhost:9999/api/member/kakao/login
     */
    @GetMapping("/kakao/login")
    public ResponseEntity<?> kakaoLogin(@RequestParam("code") String code) throws JsonProcessingException {
        KakaoLoginResponseDto kakaoLoginResponseDto = memberService.login(code);

        // access token과 refresh token을 header 에 담아 보낸다.
        HttpHeaders responseHeaders = new HttpHeaders();


        return new ResponseEntity<>(HttpStatus.OK);
    }




}
