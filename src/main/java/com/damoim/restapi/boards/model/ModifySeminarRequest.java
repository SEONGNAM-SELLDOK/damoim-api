package com.damoim.restapi.boards.model;

import com.damoim.restapi.boards.entity.DamoimTag;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@ToString
@AllArgsConstructor
@Builder
public class ModifySeminarRequest {
    @NotBlank
    private String title; // 세미나 제목
    @NotBlank
    private String content; // 상세 내용
    @NotBlank
    private String image; // 대표 이미지
    @NotNull
    private String country;
    @NotNull
    private String city;
    @NotNull
    private String street;
    @NotNull
    private String subject; // 주제
    @NotNull
    private DamoimTag damoimTag; // 태그
    @NotNull
    private LocalDateTime endDate; // 마감일
}
