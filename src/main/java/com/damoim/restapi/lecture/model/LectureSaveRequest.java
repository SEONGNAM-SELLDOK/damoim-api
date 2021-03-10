package com.damoim.restapi.lecture.model;

import com.damoim.restapi.lecture.entity.LectureType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * SaveLectureResponse Class
 *
 * @author leekyunghee
 * @since 2021. 02.25
 */

@Builder
@Getter
@Setter
public class LectureSaveRequest {
    @ApiModelProperty(value = "강의 제목", required = true, example = "토비의 스프링 5")
    private String title;

    @ApiModelProperty(value = "상세 설명", required = true, example = "이 강의는 토비의 스프링 5 실습과 예제 중심의 바이블 입니다.")
    private String description;

    @ApiModelProperty(value = "발표자", required = true, example = "오성록")
    private String speaker;

    @ApiModelProperty(value = "주제", required = true, example = "SPRING")
    private LectureType lectureType;

    @ApiModelProperty(value = "수업 경로", example = "Zoom")
    private String route;

    @ApiModelProperty(value = "마감 기한",required = true,example = "2021-03-10")
    private LocalDate deadline;

    @ApiModelProperty(value = "등록자", required = true, example = "이경희")
    private String register;
}
