package com.damoim.restapi.boards.dao;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author gisung go
 * @since 2021-02-22
 * */

@Data
public class BoardsSearchCondition {
    private String title;
    private LocalDateTime endDate;
    private String boardsCountry;
    private String boardsCity;
    private String boardStreet;
    private String totalMember;
    private String currentMember;
    private String subject;
    private String damoimTag;
}
