package com.musicat.util;

/*

상수를 관리하는 class
 */


import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Component
public class ConstantUtil {

    // yml 파일로 값을 주입하면 완벽
    //  @Value("${user.page.size}")
    public final int USER_PAGE_SIZE = 10;

    // 공지사항 페이지
    public final int NOTICE_PAGE_SIZE = 10;

    // 알림 페이지
    public final int ALERT_PAGE_SIZE = 10;

    // 2022년 12월 10일 같은 형색
    public DateTimeFormatter simpleFormatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일");
    // 2022년 12월 10일 (수) 18:30
    public DateTimeFormatter detailFormatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 (E) HH:mm", Locale.KOREA);



}