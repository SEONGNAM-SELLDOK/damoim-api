package com.damoim.restapi.lecture.model;

import com.damoim.restapi.lecture.entity.LectureSubject;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class LectureResponse {
    @ApiModelProperty(example = "1")
    private long id;
    @ApiModelProperty(value = "강의 이름", example = "토비의 스프링 5")
    private String title;
    @ApiModelProperty(value = "발표자 이름", example = "토비")
    private String speaker;
    @ApiModelProperty(value = "상세 설명", example = "이 강의는 토비의 스프링 5 실습과 예제 중심의 바이블 입니다.")
    private String description;
    @ApiModelProperty(value = "썸네일 이미지", example = "/images/lectures-01.png")
    private String image;
    @ApiModelProperty(value = "주제", required = true, example = "SPRING")
    private LectureSubject subject;
    @ApiModelProperty(value = "등록자", example = "이경희")
    private String register;
    @ApiModelProperty(value = "수업 경로", example = "Zoom")
    private String route;
    @ApiModelProperty(value = "등록일", example = "2021-02-25T18:30:33.121Z")
    private LocalDateTime registeredDate;
    @ApiModelProperty(value = "수정자", example = "이경희")
    private String modifier;
    @ApiModelProperty(value = "수정일", example = "2021-02-25T18:30:33.121")
    private LocalDateTime modifiedDate;
    @ApiModelProperty(value = "마감 기한", required = true, example = "2021-02-25")
    private LocalDate deadline;

}
