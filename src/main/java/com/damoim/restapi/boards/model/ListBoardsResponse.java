package com.damoim.restapi.boards.model;

import com.querydsl.core.annotations.QueryProjection;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author gisung go
 * @since 2021-02-22
 * */

@Data
@Builder
@NoArgsConstructor
public class ListBoardsResponse {
    @ApiModelProperty(value = "모임 제목", example = "Seminar, Study 제목 입니다.")
    private String title;
    @ApiModelProperty(value = "모임 마감일", example = "2021-03-15T20:17:22.137Z")
    private LocalDateTime endDate;
    @ApiModelProperty(value = "국가", example = "KR")
    private String boardsCountry;
    @ApiModelProperty(value = "도시", example = "SEOUL")
    private String boardsCity;
    @ApiModelProperty(value = "상세 주소", example = "강남구 영동대로 513")
    private String boardStreet;
    @ApiModelProperty(value = "전체 인원", example = "10")
    private Integer totalMember;
    @ApiModelProperty(value = "현재 인원", example = "4")
    private Integer currentMember;
    @ApiModelProperty(value = "모임 주제", example = "SPRING")
    private String subject;
    @ApiModelProperty(value = "모임 태그", example = "JAVA")
    private String damoimTag;
    @ApiModelProperty(value = "모임 썸네일", example = "img/0000.jpg")
    private String image;

    @QueryProjection
    public ListBoardsResponse(String title, LocalDateTime endDate, String boardsCountry, String boardsCity, String boardStreet, Integer totalMember, Integer currentMember, String subject, String damoimTag, String image) {
        this.title = title;
        this.endDate = endDate;
        this.boardsCountry = boardsCountry;
        this.boardsCity = boardsCity;
        this.boardStreet = boardStreet;
        this.totalMember = totalMember;
        this.currentMember = currentMember;
        this.subject = subject;
        this.damoimTag = damoimTag;
        this.image = image;
    }
}
