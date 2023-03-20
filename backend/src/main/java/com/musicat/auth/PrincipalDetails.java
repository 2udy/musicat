package com.musicat.auth;


import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;


/*

security가 /login 요청을 보고 로그인을 대신 진행해준다.
로그인 진행 완료시 시큐리티 세션이 만들어진다.
Authentication 객체에 유저 정보를 저장하여 security session에 저장

 */



@Data
public class PrincipalDetails {



}
