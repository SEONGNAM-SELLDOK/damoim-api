package com.damoim.restapi.seminar.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

/**
 * @author gjsung.Go
 * @since 2012.02.20
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Seminar extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seminar_id")
    private Long id; // 세미나 아이디

    @NotBlank
    private String title; // 세미나 제목

    @NotBlank
    private String content; // 상세 내용

    @NotBlank
    private String image; // 대표 이미지

    @Embedded
    private Address address; // 장소
    private String totalMember; // 총원
    private String currentMember; //현재원
    private String subject; // 주제

    // 모임 태그 의도가 해시태그 인가요??
    @Enumerated(EnumType.STRING)
    private DamoimTag damoimTag; // 태그

    private LocalDateTime endDate; // 마감일
}
