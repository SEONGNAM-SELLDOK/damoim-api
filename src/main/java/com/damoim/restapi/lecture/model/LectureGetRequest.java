package com.damoim.restapi.lecture.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * GetLectureResponse Class
 * @author leekyunghee
 * @since 2021. 03.04
 */

@Builder
@Getter
@Setter
public class LectureGetRequest {
    @ApiModelProperty(value = "강의 제목", required = true, example = "토비의 스프링 강의")
    private String title;

    @ApiModelProperty(value = "상세 설명", required = true, example = "이 강의는 토비의 스프링 5 실습과 예제 중심의 바이블 입니다.")
    private String description;

    @ApiModelProperty(value = "등록자", example = "오성록")
    private String register;
}
