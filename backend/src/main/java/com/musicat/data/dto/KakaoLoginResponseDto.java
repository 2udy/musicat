package com.musicat.data.dto;


import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class KakaoLoginResponseDto {
    private String id;
    private String accessToken;
    private String refreshToken;

}
