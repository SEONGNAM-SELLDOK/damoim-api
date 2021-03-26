package com.damoim.restapi.lecture.model;

import com.damoim.restapi.lecture.entity.LectureSubject;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
@NoArgsConstructor
@AllArgsConstructor
public class LectureSaveRequest {
    @ApiModelProperty(value = "강의 제목", required = true, example = "토비의 스프링 5")
    @NotBlank
    private String title;

    @ApiModelProperty(value = "상세 설명", required = true, example = "이 강의는 토비의 스프링 5 실습과 예제 중심의 바이블 입니다.")
    private String description;

    @ApiModelProperty(value = "발표자", required = true, example = "오성록")
    @NotBlank
    private String speaker;

    @ApiModelProperty(value = "주제", required = true, example = "SPRING")
    @NotNull
    private LectureSubject subject;

    @ApiModelProperty(value = "수업 경로", example = "Zoom")
    private String route;

    @ApiModelProperty(value = "마감 기한", required = true, example = "2021-03-10")
    @Future
    @NotNull
    private LocalDate deadline;
}