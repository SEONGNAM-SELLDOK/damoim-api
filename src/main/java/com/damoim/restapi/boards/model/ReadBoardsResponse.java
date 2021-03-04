package com.damoim.restapi.boards.model;

import com.damoim.restapi.boards.entity.Address;
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
    private long id;
    private String title;
    private String content;
    private String image;
    private Address address;
    private String totalMember;
    private String currentMember;
    private String subject;
    private LocalDateTime endDate;

    @QueryProjection
    public ReadBoardsResponse(long id, String title, String content, String image, Address address, String totalMember, String currentMember, String subject, LocalDateTime endDate) {
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
