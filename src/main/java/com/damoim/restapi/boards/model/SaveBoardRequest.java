package com.damoim.restapi.boards.model;

import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author gisung go
 * @since 2021-02-22
 * */
@Getter
@Setter
@Builder
public class SaveBoardRequest {
    @NotBlank
    @ApiModelProperty(value = "모임 제목", example = "Seminar, Study 제목 입니다.")
    private String title;
    @NotBlank
    @ApiModelProperty(value = "모임 내용", example = "Spring 모임 입니다.")
    private String content;
    @NotBlank
    @ApiModelProperty(value = "모임 썸네일", example = "img/0000.jpg")
    private String image;
    @NotNull
    @ApiModelProperty(value = "국가", example = "KR")
    private String country;
    @NotNull
    @ApiModelProperty(value = "도시", example = "SEOUL")
    private String city;
    @NotNull
    @ApiModelProperty(value = "상세 주소", example = "강남구 영동대로 513")
    private String street;
    @NotNull
    @ApiModelProperty(value = "전체 인원", example = "10")
    private Integer totalMember;
    @NotNull
    @ApiModelProperty(value = "현재 인원", example = "4")
    private Integer currentMember;
    @NotNull
    @ApiModelProperty(value = "모임 주제", example = "SPRING")
    private String subject;
    @NotNull
    @ApiModelProperty(value = "모임 태그", example = "JAVA")
    private String damoimTag;
    @NotNull
    @ApiModelProperty(value = "모임 마감일", example = "2021-03-15T20:17:22.137Z")
    private LocalDateTime endDate;
}
