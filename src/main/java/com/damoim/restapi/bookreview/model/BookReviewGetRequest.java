package com.damoim.restapi.bookreview.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.LocalDate;

/**
 * @author SeongRok.Oh
 * @since 2021/03/16
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookReviewGetRequest {
    @ApiModelProperty(value = "제목", example = "객체지향의 사실과 오해를 읽고")
    private String title;
    @ApiModelProperty(value = "내용", example = "아주 좋았다.")
    private String description;
    @ApiModelProperty(value = "국제 표준 도서 번호", example = "978-89-98139-76-6")
    private String isbn;
    @ApiModelProperty(value = "출판사", example = "위키북스")
    private String publisher;
    @ApiModelProperty(value = "지은이", example = "조영호")
    private String writer;
    @ApiModelProperty(value = "주제", example = "객체지향")
    private String subject;
    @ApiModelProperty(value = "태그", example = "SOLID")
    private String tag;
    @ApiModelProperty(value = "등록자", example = "오성록")
    private String register;
    @ApiModelProperty(value = "모임 마감일 시작", example = "2021-03-16")
    private LocalDate deadlineFrom;
    @ApiModelProperty(value = "모임 마감일 시작", example = "2022-03-16")
    private LocalDate deadlineTo;
}
