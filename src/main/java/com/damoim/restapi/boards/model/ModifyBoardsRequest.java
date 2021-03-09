package com.damoim.restapi.boards.model;

import com.damoim.restapi.boards.entity.Address;
import com.damoim.restapi.boards.entity.Board;
import com.damoim.restapi.boards.entity.DamoimTag;
import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author gisung go
 * @since 2021-02-22
 * */

@NoArgsConstructor
@Getter
@ToString
@AllArgsConstructor
@Builder
public class ModifyBoardsRequest {
    @NotBlank
    private String title; // 세미나 제목
    @NotBlank
    private String content; // 상세 내용
    @NotBlank
    private String image; // 대표 이미지
    @NotNull
    private String country;
    @NotNull
    private String city;
    @NotNull
    private String street;
    @NotNull
    private String subject; // 주제
    @NotNull
    private DamoimTag damoimTag; // 태그
    @NotNull
    private LocalDateTime endDate; // 마감일

    public Board updateTo(Board board) {
        board.setTitle(title);
        board.setContent(content);
        board.setImage(image);
        board.setAddress(new Address(country, city, street));
        board.setSubject(subject);
        board.setDamoimTag(damoimTag);
        board.setEndDate(endDate);
        return board;
    }
}
