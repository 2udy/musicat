package com.musicat.util;


/*
JWT를 다루는 클래스

 */


import com.musicat.data.entity.Authority;
import com.musicat.data.entity.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


/*
JWT를 생성하고 검증하는 클래스

 */

@RequiredArgsConstructor
@Component
public class JwtProvider {

    @Value("${jwt.secret.key}")
    private String salt;

    private Key secretKey;

    // 만료시간 : 1Hour
    private final long exp = 1000L * 60 * 60;


    /*
    @PostConstruct
    해당 빈이 생성되고 초기화 된 후 호출됨.
     */

    @PostConstruct
    protected void init() {
        secretKey = Keys.hmacShaKeyFor(salt.getBytes(StandardCharsets.UTF_8));
    }

    // 토큰 생성
    public String createToken(Member member, List<Authority> roles) {

        // jwt에 담을 데이터
        HashMap<String, Object> claims = new HashMap<>();

        claims.put("memberSeq", member.getMemberSeq());
        claims.put("memberNickname", member.getMemberNickname());
        claims.put("memberProfileImage", member.getMemberProfileImage());
        claims.put("memberThumbnailImage", member.getMemberThumbnailImage());
        claims.put("memberEmail", member.getMemberEmail());
        claims.put("memberIsBan", member.isMemberIsBan());
        claims.put("roles", roles);

        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + exp))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

  




}
