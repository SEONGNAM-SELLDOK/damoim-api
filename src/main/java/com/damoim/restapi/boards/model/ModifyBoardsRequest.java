package com.damoim.restapi.boards.model;

import com.damoim.restapi.boards.entity.Address;
import com.damoim.restapi.boards.entity.Board;
import com.damoim.restapi.boards.entity.DamoimTag;
import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty(value = "모임 주제", example = "SPRING")
    private String subject;
    @NotNull
    @ApiModelProperty(value = "모임 태그", example = "JAVA")
    private DamoimTag damoimTag;
    @NotNull
    @ApiModelProperty(value = "모임 썸네일", example = "img/0000.jpg")
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
