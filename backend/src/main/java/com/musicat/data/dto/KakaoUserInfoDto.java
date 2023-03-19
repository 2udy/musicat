package com.musicat.data.dto;


import lombok.*;

/*
카카오에서 보낸 유저 정보를 받는 객체

id
nickname
profile_image
thumbnail_image
email

5가지의 정보를 받는다.

 */


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class KakaoUserInfoDto {

    private String id;
    private String nickname;
    private String profileImage;
    private String thumbnailImage;
    private String email;

}
