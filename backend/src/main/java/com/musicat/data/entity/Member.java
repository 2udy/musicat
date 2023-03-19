package com.musicat.data.entity;

import javax.persistence.*;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


/*

회원 엔티티

 */

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "member_seq")
  private long memberSeq;

  @Column(name = "member_id")
  private String memberId;

  @Column(name = "member_nickname")
  private String memberNickname;

  @Column(name = "member_profile_image")
  private String memberProfileImage;

  @Column(name = "member_thumbnail_image")
  private String memberThumbnailImage;

  @Column(name = "member_email")
  private String memberEmail;

  @CreatedDate
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @Column(name = "member_created_at")
  private LocalDateTime memberCreatedAt;

  @Column(name = "member_money")
  private long memberMoney;

  @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @Builder.Default
  @Column(name = "member_roles")
  private List<Authority> memberRoles = new ArrayList<>();


  @Column(name = "member_warn_count")
  private int memberWarnCount;

  @Column(name = "member_unread_message")
  private int memberUnreadMessage;

  /*
  boolean 타입의 getter는 get이 아닌 is 접두어를 사용해 getter 메소드가 생성된다.
  예시) isMemberIsDarkmode

   */

  @Column(name = "member_is_darkmode")
  private boolean memberIsDarkmode;

  @Column(name = "member_is_ban")
  private boolean memberIsBan;

  @Column(name = "member_is_member")
  private boolean memberIsMember;



  /*
  @PrePersist
  JPA 엔티티 라이프사이클 이벤트 중 하나
  엔티티가 저장되기 전에 호출되는 메소드에 적용하는 어노테이션
  @PrePersis가 적둉된 메소드는 엔티티가 저장되기 전에 자동으로 호출
  전 처리 작업을 수행
   */
  @PrePersist
  public void prePersist() {
    if(memberEmail == null) {
      this.memberEmail = "";
    }
    this.memberCreatedAt = LocalDateTime.now();
    this.memberMoney = 0;
    this.memberWarnCount = 0;
    this.memberUnreadMessage = 0;
    this.memberIsDarkmode = false;
    this.memberIsBan = false;
    this.memberIsMember = false;
  }

  public void setRoles(List<Authority> role) {
    this.memberRoles = role;
    role.forEach(o -> o.setMember(this));
  }
}
