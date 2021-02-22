package com.damoim.restapi.recruit.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author SeongRok.Oh
 * @since 2021/02/21
 */
@Getter
@Setter
public class RecruitSaveResponseDto {

    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(value = "등록자", required = true, example = "오성록")
    private String register;

    @ApiModelProperty(value = "회사", required = true, example = "네이버")
    private String company;

    @ApiModelProperty(value = "제목", required = true, example = "새로운 서비스를 함께 할 팀원을 모집합니다.")
    private String title;

    @ApiModelProperty(value = "내용", example = "B2B 서비스 고도화/안정화를 위하여 백엔드 개발자를 채용하고 있습니다!")
    private String description;

    @ApiModelProperty(value = "좋아요", example = "[\"오성록\",\"유재석\",\"박명수\"]")
    private List<String> likes;

    @ApiModelProperty(value = "북마크", example = "[\"정준하\",\"정형돈\"]")
    private List<String> bookMarks;

    @ApiModelProperty(value = "마감일", required = true, example = "2021-02-28T20:17:22.137Z")
    private LocalDate deadline;

    @ApiModelProperty(value = "등록일", example = "2021-02-21T20:17:22.137Z")
    private LocalDateTime createdDate;

    @ApiModelProperty(value = "마지막 수정일", example = "2021-02-21T20:17:22.137Z")
    private LocalDateTime modifiedDate;

}
