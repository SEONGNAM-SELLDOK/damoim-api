package com.damoim.restapi.lecture.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * SaveLectureResponse Class
 * @author leekyunghee
 * @since 2021. 02.25
 */

@Builder
@Getter
@Setter
public class SaveLectureRequest {
    @ApiModelProperty(value = "강의 아이디", required = true, example = "100001")
    private long lectureId;
    @ApiModelProperty(value = "강의 제목", required = true, example = "토비의 스프링 5")
    private String title;
    @ApiModelProperty(value = "상세 설명", required = true, example = "이 강의는 토비의 스프링 5 실습과 예제 중심의 바이블 입니다.")
    private String description;
    @ApiModelProperty(value = "썸네일 이미지", example = "/images/lectures-01.png")
    private String image;
    @ApiModelProperty(value = "등록자", required = true, example = "이경희")
    private String register;
}
