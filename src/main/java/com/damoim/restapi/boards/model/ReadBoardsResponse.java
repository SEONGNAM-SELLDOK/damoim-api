package com.damoim.restapi.boards.model;

import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

import java.time.LocalDateTime;

/**
 * @author Gisung Go
 * @since 2021-02-26
 * */
@Getter
@Setter
@Builder
@NoArgsConstructor
public class ReadBoardsResponse {
    private String title;
    private String content;
    private String image;
    private String country;
    private String city;
    private String street;
    private String totalMember;
    private String currentMember;
    private String subject;
    private LocalDateTime endDate;

    @QueryProjection
    public ReadBoardsResponse(String title, String content, String image, String country, String city, String street, String totalMember, String currentMember, String subject, LocalDateTime endDate) {
        this.title = title;
        this.content = content;
        this.image = image;
        this.country = country;
        this.city = city;
        this.street = street;
        this.totalMember = totalMember;
        this.currentMember = currentMember;
        this.subject = subject;
        this.endDate = endDate;
    }
}
