package com.musicat.data.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/*
카카오에서 보내주는 access token 과 refresh token의 정보를 받는 클래스
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KakaoTokenDto {


    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("token_type")
    private String tokenType;

    @JsonProperty("refresh_token")
    private String refreshToken;

    @JsonProperty("expires_in")
    private String expiresIn;

    @JsonProperty("scope")
    private String scope;

    @JsonProperty("refresh_token_expires_in")
    private String refreshTokenExpiresIn;

}
