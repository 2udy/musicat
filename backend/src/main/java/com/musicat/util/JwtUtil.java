package com.musicat.util;


import com.musicat.data.entity.Authority;
import com.musicat.data.entity.Member;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.List;

/*
JWT 생성, 검증 하는 클래스
 */

@Component
public class JwtUtil {



    @Value("${jwt.secret.key}")
    private String salt;

    private String secretKey;

    // 만료 시간 : 1시간
    private final long expiration = 1000L * 60 * 60;

    @PostConstruct
    protected void init() {
        byte[] keyBytes = salt.getBytes(StandardCharsets.UTF_8);
        secretKey = Base64.getEncoder().encodeToString(keyBytes);
    }




    // access token 생성
    public String createAccessToken(Member member) {

        /*
        setSubject는 토큰의 주제로 식별할 수 있는 대상이 와야한다.
        자료형은 String이어야 해서 memberSeq의 long을 String으로 변환했다.
         */
        Claims claims = Jwts.claims().setSubject(String.valueOf(member.getMemberSeq()));
        claims.put("memberNickname", member.getMemberNickname());
        claims.put("memberProfileImage", member.getMemberProfileImage());
        claims.put("memberRoles", member.getMemberRoles());

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    /*
    refresh token 생성
    확인용이므로 memberSeq의 정보만 담겨 있다.
     */
    public String createRefreshToken(Member member) {
        Claims claims = Jwts.claims().setSubject(String.valueOf(member.getMemberSeq()));

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    /*
    JWT 에서 Claims 객체를 추출하는 메소드
    secretKey를 통해 parsing 해서 객체를 추출
     */
    public Claims getClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // token 에서 memberSeq 추출 메소드
    public long getMemberSeq(String token) {
        String subject = getClaimsFromToken(token).getSubject();
        return Long.parseLong(subject);
    }

    // token 에서 memberRoles 추출 메소드
    public List<String> getMemberRolesFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.get("memberRoles", List.class);
    }


    /*
    토큰이 유효한지 검사하는 메소드
    비밀키를 사용해 서명을 검증, JWT 토큰을 파싱
    파싱하는 과정에서 서명이 유효하지 않거나 토큰이 손상된 경우 예외가 발생
    파싱 과정에 예외가 발생하면 토큰이 유효하지 않다고 간주해 false를 반환
     */
    public boolean validateToken(String token) {
        try {

            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException | MalformedJwtException | SignatureException | UnsupportedJwtException | IllegalArgumentException e) {
            return false;
        }
    }





}
