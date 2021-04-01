package com.damoim.restapi.boards.model;

import com.damoim.restapi.boards.entity.Address;
import com.querydsl.core.annotations.QueryProjection;
import java.time.LocalDateTime;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Gisung Go
 * @since 2021-02-26
 * */
@Getter
@Setter
@Builder
@NoArgsConstructor
public class ReadBoardsResponse {
    @ApiModelProperty(example = "1")
    private long id;
    @ApiModelProperty(value = "모임 제목", example = "Seminar, Study 제목 입니다.")
    private String title;
    @ApiModelProperty(value = "모임 내용", example = "Spring 모임 입니다.")
    private String content;
    @ApiModelProperty(value = "모임 썸네일", example = "img/0000.jpg")
    private String image;
    @ApiModelProperty(value = "모임 장소", example = "서울 강남구 영동대로 513")
    private Address address;
    @ApiModelProperty(value = "모임 전체 인원", example = "10")
    private Integer totalMember;
    @ApiModelProperty(value = "모임 현재 인원", example = "4")
    private Integer currentMember;
    @ApiModelProperty(value = "모임 주제", example = "SPRING")
    private String subject;
    @ApiModelProperty(value = "모임 마감일", example = "2021-03-15T20:17:22.137Z")
    private LocalDateTime endDate;

    @QueryProjection
    public ReadBoardsResponse(long id, String title, String content, String image, Address address, Integer totalMember, Integer currentMember, String subject, LocalDateTime endDate) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.image = image;
        this.address = address;
        this.totalMember = totalMember;
        this.currentMember = currentMember;
        this.subject = subject;
        this.endDate = endDate;
    }
}
