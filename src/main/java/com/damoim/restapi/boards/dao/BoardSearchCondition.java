package com.damoim.restapi.boards.dao;

import com.damoim.restapi.boards.entity.BoardType;
import java.time.LocalDate;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @author gisung go
 * @since 2021-02-22
 */

@Data
public class BoardSearchCondition {

    private String title;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate from;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate to;
    private String boardsCountry;
    private String boardsCity;
    private String boardStreet;
    private String totalMember;
    private String currentMember;
    private String subject;
    private String damoimTag;
    private BoardType boardType;
}
