package com.damoim.restapi.boards.dao;

import java.time.LocalDateTime;

import com.damoim.restapi.boards.entity.BoardType;
import lombok.Data;

/**
 * @author gisung go
 * @since 2021-02-22
 * */

@Data
public class BoardSearchCondition {
    private String title;
    private LocalDateTime endDate;
    private String boardsCountry;
    private String boardsCity;
    private String boardStreet;
    private String totalMember;
    private String currentMember;
    private String subject;
    private String damoimTag;
    private BoardType boardType;
}
