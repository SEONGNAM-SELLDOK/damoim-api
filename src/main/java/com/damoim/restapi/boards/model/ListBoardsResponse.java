package com.damoim.restapi.boards.model;

import com.querydsl.core.annotations.QueryProjection;
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
    private String title;
    private LocalDateTime endDate;
    private String boardsCountry;
    private String boardsCity;
    private String boardStreet;
    private Integer totalMember;
    private Integer currentMember;
    private String subject;
    private String damoimTag;
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
