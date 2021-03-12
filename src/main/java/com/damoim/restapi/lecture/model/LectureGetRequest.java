package com.damoim.restapi.lecture.model;

import com.damoim.restapi.lecture.entity.LectureSubject;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * GetLectureResponse Class
 *
 * @author leekyunghee
 * @since 2021. 03.04
 */

@Builder
@Getter
@Setter
public class LectureGetRequest {
    @ApiModelProperty(value = "강의 제목", example = "토비의 스프링 강의")
    private String title;

    @ApiModelProperty(value = "상세 설명", example = "이 강의는 토비의 스프링 5 실습과 예제 중심의 바이블 입니다.")
    private String description;

    @ApiModelProperty(value = "발표자", example = "토비")
    private String speaker;

    @ApiModelProperty(value = "주제", example = "SPRING")
    private LectureSubject subject;

    @ApiModelProperty(value = "수업 경로", example = "Zoom")
    private String route;

    @ApiModelProperty(value = "등록자", example = "오성록")
    private String register;

    @ApiModelProperty(value = "마감일 시작", example = "2020-03-12")
    private LocalDate deadLineFrom;

    @ApiModelProperty(value = "마감일 끝", example = "2020-03-16")
    private LocalDate deadLineTo;

    @ApiModelProperty(value = "생성일 시작", example = "2020-03-12")
    private LocalDate from;

    @ApiModelProperty(value = "생성일 끝", example = "2020-03-16")
    private LocalDate to;
}
